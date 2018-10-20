package com.haaivda.g2048.engine;

import java.util.*;

public class Board {
    private final int numRows;
    private final int numCols;
    private final Tile[][] tiles;
    private final int score;
    private final boolean gameOver;
    private final Random random;

    private static final double PROBABILITY_TWO_TILE = 0.9;

    private Board(Builder builder) {
        this.numRows = builder.numRows;
        this.numCols = builder.numCols;
        this.random = builder.random;

        // Initialize board with tiles (null if tile is empty)
        tiles = new Tile[this.numCols][this.numRows];
        for(int y = 0; y < this.numRows; y++) {
            for(int x = 0; x < this.numCols; x++) {
                final Coordinate c = new Coordinate(x, y);
                this.tiles[y][x] = builder.boardConfig.get(c);
            }
        }
        this.gameOver = Board.checkGameOver(this);
        this.score = builder.score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return numRows == board.numRows &&
                numCols == board.numCols &&
                score == board.score &&
                gameOver == board.gameOver &&
                Arrays.deepEquals(tiles, board.tiles); // Multidimensional array, so deepEquals needed!!
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(numRows, numCols, score, gameOver);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }

    public Tile getTile(int x, int y) {
        if (x >= this.numCols || y >= this.numRows) {
            throw new RuntimeException("Out of bounds: given combination of x and y are not on the board!");
        }
        return this.tiles[y][x];
    }

    static Board createInitialBoard(int randomSeed) {
        Builder builder = new Builder(4, 4, 0, randomSeed);
        // Set two random coordinates for initial tiles
        Coordinate c1 = new Coordinate(builder.random.nextInt(builder.numCols),
                                        builder.random.nextInt(builder.numRows));
        Coordinate c2;
        do {
            c2 = new Coordinate(builder.random.nextInt(builder.numCols),
                                builder.random.nextInt(builder.numRows));
        } while(c2.equals(c1));

        // Add random tiles
        builder.setInitialTile(c1);
        builder.setInitialTile(c2);
        return builder.build();
    }

    private static boolean checkGameOver(Board board) {
        for(int y = 0; y < board.numRows; y++) {
            for(int x = 0; x < board.numCols; x++) {
                final Tile tile = board.getTile(x, y);
                // If there is still a free space: no game over!
                if(tile == null) {
                    return false;
                }
                // Check adjacent tiles
                if(x + 1 < board.numCols) {
                    final Tile adjacentTile = board.getTile(x + 1, y);
                    if(adjacentTile != null && adjacentTile.getValue() == tile.getValue()) {
                        return false;
                    }
                }
                if(y + 1 < board.numRows) {
                    final Tile adjacentTile = board.getTile(x, y + 1);
                    if(adjacentTile != null && adjacentTile.getValue() == tile.getValue()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    Board makeMove(Move m) {
        Builder builder = new Builder(this.numRows, this.numCols, this.score, this.random.nextInt());
        Tile[][] newTiles = new Tile[this.numCols][this.numRows];
        List<Coordinate> freePositions =  new ArrayList<>();

        // Calculate moves in y-direction
        if(m == Move.UP || m == Move.DOWN) {
            for(int x = 0; x < this.numCols; x++) {
                int currentY = (m == Move.UP ? 0 : this.numRows - 1);
                for(int y = 0; y < this.numRows; y++) {
                    final Tile currentTile = this.tiles[m == Move.UP ? y : this.numRows - y - 1][x];
                    if(currentTile != null) {
                        final Tile newTile = newTiles[currentY][x];
                        if(newTile != null) {
                            if (newTile.getValue() == currentTile.getValue()) {
                                newTiles[currentY][x] = currentTile.createMovedAndDoubledTile(new Coordinate(x, currentY));
                                currentY -= m.getDirectionY();
                                continue;
                            }
                            currentY -= m.getDirectionY();
                        }
                        newTiles[currentY][x] = currentTile.createMovedTile(new Coordinate(x, currentY));
                    }
                }
            }
        }
        // Calculate moves in x-direction
        else if(m == Move.LEFT || m == Move.RIGHT) {
            for(int y = 0; y < this.numRows; y++) {
                int currentX = (m == Move.LEFT ? 0 : this.numCols - 1);
                for(int x = 0; x < this.numCols; x++) {
                    final Tile currentTile = this.tiles[y][m == Move.LEFT ? x : this.numCols - x - 1];
                    if(currentTile != null) {
                        final Tile newTile = newTiles[y][currentX];
                        if(newTile != null) {
                            if (newTile.getValue() == currentTile.getValue()) {
                                newTiles[y][currentX] = currentTile.createMovedAndDoubledTile(new Coordinate(currentX, y));
                                currentX -= m.getDirectionX();
                                continue;
                            }
                            currentX -= m.getDirectionX();
                        }
                        newTiles[y][currentX] = currentTile.createMovedTile(new Coordinate(currentX, y));
                    }
                }
            }
        } else {
            throw new RuntimeException("Should not get here; impossible move!");
        }

        // Add all tiles to builder
        for(int y = 0; y < this.numRows; y++) {
            for (int x = 0; x < this.numCols; x++) {
                final Tile newTile = newTiles[y][x];
                if(newTile == null) {
                    freePositions.add(new Coordinate(x, y));
                }
                builder.addTile(newTiles[y][x]);
            }
        }

        // Check move validity
        if(freePositions.isEmpty()) {
            // invalid move
            return this;
        }
        Board boardWithoutRandomTile = builder.build();
        if(boardWithoutRandomTile.equals(this)) {
            return this;
        }

        // Add random tile and return
        builder.setInitialTile(freePositions.get(this.random.nextInt(freePositions.size())));
        return builder.build();
    }

    @Override
    public String toString() {
        // Add board tile values
        StringBuilder s = new StringBuilder();
        for(int y = 0; y < this.numRows; y++) {
            for(int x = 0; x < this.numCols; x++) {
                if(this.tiles[y][x] != null) {
                    s.append(this.tiles[y][x].toString());
                } else {
                    s.append("-");
                }
                s.append("\t");
            }
            s.append("\n");
        }
        // Add score
        s.append("Score: ").append(this.score);
        return s.toString();
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public int getScore() {
        return this.score;
    }

    public boolean gameOver() {
        return this.gameOver;
    }

    private static class Builder {
        final Map<Coordinate, Tile> boardConfig;
        int score;
        final int numRows;
        final int numCols;
        final Random random;

        Builder(int numRows, int numCols, int initialScore, int randomSeed) {
            this.boardConfig = new HashMap<>();
            this.score = initialScore;
            this.numRows = numRows;
            this.numCols = numCols;
            this.random = new Random();
            if(randomSeed > 0) {
                this.random.setSeed(randomSeed);
            }
        }

        void setInitialTile(final Coordinate c) {
            this.boardConfig.put(c, Tile.createInitialTile(c, this.random.nextDouble() < PROBABILITY_TWO_TILE ? 2 : 4));
        }

        void addTile(Tile t) {
            if(t != null) {
                this.boardConfig.put(t.getPosition(), t);

                /* Update score */
                this.score += t.getScore();
            }
        }

        Board build() {
            return new Board(this);
        }
    }
}
