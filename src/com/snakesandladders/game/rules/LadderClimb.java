package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.Turn;

import java.util.Map;

public class LadderClimb implements GameRule {

    private Map<Integer, Integer> ladderPositions;
    private Logger logger;

    public LadderClimb(Map<Integer, Integer> ladderPositions, Logger logger) {
        this.ladderPositions = ladderPositions;
        this.logger = logger;
    }

    @Override
    public void evaluate(Player player, Turn turn, RuleEvaluationListener listener) {
        if (ladderPositions.get(turn.nextPosition()) != null) {
            logger.log("Player got chanced upon a ladder at position " + turn.nextPosition() + "!");
            listener.updatedTurnFor(player, Turn.advanceTo(ladderPositions.get(turn.nextPosition())));
        }
    }
}
