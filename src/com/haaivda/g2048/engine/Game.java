package com.haaivda.g2048.engine;

import java.util.Stack;

public class Game {
    private Board board;
    private final Stack<Board> boardHistory;

    public Game() {
        this.boardHistory = new Stack<>();
        this.newGame();
    }

    public Board getBoard() {
        return this.board;
    }

    public void undo() {
        if(!this.boardHistory.empty()) {
            this.board = this.boardHistory.pop();
        }
    }

    public void makeMove(Move m) {
        this.boardHistory.push(this.board);
        this.board = this.board.makeMove(m);
    }

    public void newGame() {
        this.board = Board.createInitialBoard(0);
        this.boardHistory.clear();
    }
}
