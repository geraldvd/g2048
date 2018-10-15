package com.haaivda.g2048.engine;

public class Coordinate {
    private final int x;
    private final int y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof Coordinate)) {
            return false;
        }
        final Coordinate c = (Coordinate)obj;
        return this.x == c.x && this.y == c.y;
    }

    @Override
    public int hashCode() {
        return 31 * this.x + this.y;
    }
}
