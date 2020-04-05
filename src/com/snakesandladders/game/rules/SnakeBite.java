package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.state.Turn;

import java.util.Map;

public class SnakeBite implements GameRule {

    private Map<Integer, Integer> snakePositions;

    public SnakeBite(Map<Integer, Integer> snakePositions) {
        this.snakePositions = snakePositions;
    }

    @Override
    public void evaluate(Player player, Turn turn, RuleEvaluationListener listener) {
        if (snakePositions.get(turn.nextPosition()) != null) {
            listener.updatedTurnFor(player, Turn.advanceTo(snakePositions.get(turn.nextPosition())));
        }
    }
}
