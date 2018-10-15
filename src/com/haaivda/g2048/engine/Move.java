package com.haaivda.g2048.engine;

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
    };

    public abstract int getDirectionX();
    public abstract int getDirectionY();
}
