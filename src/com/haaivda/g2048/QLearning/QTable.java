package com.haaivda.g2048.QLearning;

import com.haaivda.g2048.engine.Board;
import com.haaivda.g2048.engine.Game;
import com.haaivda.g2048.engine.Move;

import java.util.*;

public class QTable {
    // TODO: disallow AI to do moves that don't change the state
    // TODO: add score bonus when higher tile appears??
    // TODO: add score reduction when more places are taken? (i.e., closer to game over)
    Game game;
    final Move[] allMoves = Move.getAllMoves();
    Map<List<Integer>, QValueRow> qTable;
    final Random random;

    public QTable() {
        this.game = new Game();
        this.qTable = new HashMap<>();
        this.random = new Random();
    }

    public void playExploitationGame() {
        System.out.println("Start exploitation game... I hope you trained!");
        this.game.newGame();
        System.out.println(this.game.getBoard());

        int movesUntilGameover = 0;
        while(!this.game.getBoard().gameOver()) {
            if(this.qTable.containsKey(this.game.getBoard().getBoardState())) {
                this.game.makeMove(this.qTable.get(this.game.getBoard().getBoardState()).getBestMove());
            } else {
                this.game.makeMove(QValueRow.ALL_ACTIONS[this.random.nextInt(QValueRow.ALL_ACTIONS.length)]);
            }
            System.out.println(this.game.getBoard());
            movesUntilGameover++;
        }
        System.out.println("Game over in " + movesUntilGameover + " moves.");
    }

    public void qLearning(int nGames) {
        for(int i = 0; i < nGames; i++) {
            System.out.println("Starting learning game: " + i + "/" + nGames);

            int movesUntilGameover = 0;
            while(!this.game.getBoard().gameOver()) {
                double learningRate = ((double)(nGames-i)) / nGames;
                this.qLearningStep(learningRate,0.9 );
                movesUntilGameover++;
            }
            System.out.println("Game over in " + movesUntilGameover + " moves. Final score: " + this.game.getBoard().getScore());
            this.game.newGame();
        }
    }

    private void qLearningStep(double learningRate, double discountRate) {
        List<Integer> currentState = this.game.getBoard().getBoardState();
        if(!this.qTable.containsKey(currentState)) {
            this.qTable.put(currentState, new QValueRow(currentState));
        }

        // Choose action (either through exploitation or exploration, based on learning rate
        Move nextAction;
        double r = this.random.nextDouble();
        if(r > learningRate) {
            // Exploitation: pick best move
            nextAction = this.qTable.get(currentState).getBestMove();
        } else {
            // Exploration: pick random move
            nextAction = QValueRow.ALL_ACTIONS[this.random.nextInt(QValueRow.ALL_ACTIONS.length)];
        }

        // Perform action
        Board oldBoard = this.game.getBoard();
        this.game.makeMove(nextAction);
        Board newBoard = this.game.getBoard();
        List<Integer> newState = newBoard.getBoardState();

        // Check whether new state is already in QTable, and add if not
        if(!this.qTable.containsKey(newState)) {
            this.qTable.put(newState, new QValueRow(newState));
        }

        // Update Q-value using Bellman equation
        double currentQValue = this.qTable.get(currentState).getQValue(nextAction);
        double maximumExpectedFutureQValue = this.qTable.get(newState).getMaxQValue();
        int reward = newBoard.getScore() - oldBoard.getScore();
        double deltaQ = reward + discountRate*maximumExpectedFutureQValue - currentQValue;
        this.qTable.get(currentState).setQValue(nextAction, currentQValue + deltaQ);
    }
}
