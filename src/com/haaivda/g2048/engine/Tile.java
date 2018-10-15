package com.haaivda.g2048.engine;

public class Tile {
    private final Coordinate position;
    private final int value;
    private final boolean valueIsScore;

    private Tile(Coordinate position, int value, boolean valueIsScore) {
        this.position = position;
        this.value = value;
        this.valueIsScore = valueIsScore;
    }

    static Tile createInitialTile(Coordinate position, int value) {
        return new Tile(position, value, false);
    }

    Tile moveTile(Coordinate destination) {
        return new Tile(destination, this.value, false);
    }

    Tile moveAndDoubleTile(Coordinate destination) {
        return new Tile(destination, 2*this.value, true);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    int getValue() {
        return this.value;
    }

    int getScore() {
        return this.valueIsScore ? value : 0;
    }

    Coordinate getPosition() {
        return this.position;
    }
}
