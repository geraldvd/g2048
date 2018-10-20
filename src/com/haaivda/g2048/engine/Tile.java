package com.haaivda.g2048.engine;

import java.util.Objects;

public class Tile {
    private final Coordinate position;
    private final int value;
    private final boolean valueIsScore;

    private Tile(Coordinate position, int value, boolean valueIsScore) {
        this.position = position;
        this.value = value;
        this.valueIsScore = valueIsScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return value == tile.value &&
                Objects.equals(position, tile.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, value, valueIsScore);
    }

    static Tile createInitialTile(Coordinate position, int value) {
        return new Tile(position, value, false);
    }

    Tile createMovedTile(Coordinate destination) {
        return new Tile(destination, this.value, false);
    }

    Tile createMovedAndDoubledTile(Coordinate destination) {
        return new Tile(destination, 2*this.value, true);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    public int getValue() {
        return this.value;
    }

    int getScore() {
        return this.valueIsScore ? value : 0;
    }

    Coordinate getPosition() {
        return this.position;
    }
}
