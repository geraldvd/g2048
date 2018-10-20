package com.haaivda.g2048.engine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public enum Move {
    UP {
        @Override
        public int getDirectionX() {
            return 0;
        }

        @Override
        public int getDirectionY() {
            return -1;
        }

        @Override
        public int getMoveIndex() {
            return 0;
        }

        @Override
        public String toString() {
            return "UP";
        }
    },
    DOWN {
        @Override
        public int getDirectionX() {
            return 0;
        }

        @Override
        public int getDirectionY() {
            return 1;
        }

        @Override
        public int getMoveIndex() {
            return 1;
        }

        @Override
        public String toString() {
            return "DOWN";
        }
    },
    LEFT {
        @Override
        public int getDirectionX() {
            return -1;
        }

        @Override
        public int getDirectionY() {
            return 0;
        }

        @Override
        public int getMoveIndex() {
            return 2;
        }

        @Override
        public String toString() {
            return "LEFT";
        }
    },
    RIGHT {
        @Override
        public int getDirectionX() {
            return 1;
        }

        @Override
        public int getDirectionY() {
            return 0;
        }

        @Override
        public int getMoveIndex() {
            return 3;
        }

        @Override
        public String toString() {
            return "RIGHT";
        }

    };

    public abstract int getDirectionX();
    public abstract int getDirectionY();
    public abstract int getMoveIndex();

    public static Move[] getAllMoves() {
        return Move.class.getEnumConstants();
    }
}
