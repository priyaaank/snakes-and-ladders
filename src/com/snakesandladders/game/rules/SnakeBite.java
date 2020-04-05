package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.Turn;

import java.util.Map;

public class SnakeBite implements GameRule {

    private Map<Integer, Integer> snakePositions;
    private Logger logger;

    public SnakeBite(Map<Integer, Integer> snakePositions, Logger logger) {
        this.snakePositions = snakePositions;
        this.logger = logger;
    }

    @Override
    public void evaluate(Player player, Turn turn, RuleEvaluationListener listener) {
        if (snakePositions.get(turn.nextPosition()) != null) {
            logger.log("Player got bit by snake a position " + turn.nextPosition());
            listener.updatedTurnFor(player, Turn.advanceTo(snakePositions.get(turn.nextPosition())));
        }
    }
}
