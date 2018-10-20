package com.haaivda.g2048.QLearning;

import com.haaivda.g2048.engine.Move;

import java.util.*;

public class QValueRow {
    public final static Move[] ALL_ACTIONS = Move.getAllMoves();
    private final static Map<Move, Integer> ACTION_INDICES = initializeActionIndices();
    private final List<Double> QValues;

    public QValueRow(List<Integer> state) {
        this.QValues = initializeRewards();
    }

    private static List<Double> initializeRewards() {
        List<Double> rewards = new ArrayList<>();
        for(Move m : ALL_ACTIONS) {
            rewards.add(0.0);
        }
        return rewards;
    }

    private static Map<Move, Integer> initializeActionIndices() {
        Map<Move, Integer> actionIndices = new HashMap<>();
        for(int i = 0; i < ALL_ACTIONS.length; i++) {
            actionIndices.put(ALL_ACTIONS[i], i);
        }
        return actionIndices;
    }

    public void setQValue(Move m, double newReward) {
        this.QValues.set(ACTION_INDICES.get(m), newReward);
    }

    public double getQValue(Move m) {
        return this.QValues.get(ACTION_INDICES.get(m));
    }

    public double getMaxQValue() {
        return Collections.max(this.QValues);
    }

    public Move getBestMove() {
        // TODO: what if there are multiple "max" QValues?
        int bestMoveIndex = this.QValues.indexOf(Collections.max(this.QValues));
        return ALL_ACTIONS[bestMoveIndex];
    }
}
