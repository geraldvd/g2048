package com.haaivda.g2048.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    private final int numRows;
    private final int numCols;
    private final Tile[][] tiles;
    private final int score;

    private Board(Builder builder) {
        // Set board dimensions
        this.numRows = builder.numRows;
        this.numCols = builder.numCols;

        // Initialize board with null values
        tiles = new Tile[this.numCols][this.numRows];

        // Set tiles that are not null
        for(int y = 0; y < this.numRows; y++) {
            for(int x = 0; x < this.numCols; x++) {
                final Coordinate c = new Coordinate(x, y);
                this.tiles[y][x] = builder.boardConfig.get(c);
            }
        }

        // Compute board value
        this.score = builder.score;

        // DEBUG: print board, whenever it is created (e.g., initialization, moves, etc.)
        System.out.println(this);
    }

    public Tile getTile(int x, int y) {
        if (x >= this.numCols || y >= this.numRows) {
            throw new RuntimeException("Out of bounds: given combination of x and y are not on the board!");
        }
        return this.tiles[y][x];
    }

    public static Board createInitialBoard() {
        Builder builder = new Builder(4, 4, 0);
        // Set two random coordinates for initial tiles
        Coordinate c1 = new Coordinate(ThreadLocalRandom.current().nextInt(0, builder.numCols),
                                        ThreadLocalRandom.current().nextInt(0, builder.numRows));
        Coordinate c2;
        do {
            c2 = new Coordinate(ThreadLocalRandom.current().nextInt(0, builder.numCols),
                                ThreadLocalRandom.current().nextInt(0, builder.numRows));
        } while(c2.equals(c1));

        // Add random tiles
        builder.setInitialTile(c1);
        builder.setInitialTile(c2);

        return builder.build();
    }

    public Board makeMove(Move m) {
        Builder builder = new Builder(this.numRows, this.numCols, this.score);
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
                                newTiles[currentY][x] = currentTile.moveAndDoubleTile(new Coordinate(x, currentY));
                                currentY -= m.getDirectionY();
                                continue;
                            }
                            currentY -= m.getDirectionY();
                        }
                        newTiles[currentY][x] = currentTile.moveTile(new Coordinate(x, currentY));
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
                                newTiles[y][currentX] = currentTile.moveAndDoubleTile(new Coordinate(currentX, y));
                                currentX -= m.getDirectionX();
                                continue;
                            }
                            currentX -= m.getDirectionX();
                        }
                        newTiles[y][currentX] = currentTile.moveTile(new Coordinate(currentX, y));
                    }
                }
            }
        }

        else {
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

        // Add new random tile
        builder.setInitialTile(freePositions.get(ThreadLocalRandom.current().nextInt(0, freePositions.size())));

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

    public static class Builder {
        Map<Coordinate, Tile> boardConfig;
        int score;
        final int numRows;
        final int numCols;

        Builder(int numRows, int numCols, int initialScore) {
            this.boardConfig = new HashMap<>();
            this.score = initialScore;
            this.numRows = numRows;
            this.numCols = numCols;
        }

        void setInitialTile(final Coordinate c) {
            this.boardConfig.put(c, Tile.createInitialTile(c, ThreadLocalRandom.current().nextDouble() < 0.9 ? 2 : 4));
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
