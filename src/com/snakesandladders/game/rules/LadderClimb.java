package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.state.Turn;

import java.util.Map;

public class LadderClimb implements GameRule {

    private Map<Integer, Integer> ladderPositions;

    public LadderClimb(Map<Integer, Integer> ladderPositions) {
        this.ladderPositions = ladderPositions;
    }

    @Override
    public void evaluate(Player player, Turn turn, RuleEvaluationListener listener) {
        if (ladderPositions.get(turn.nextPosition()) != null) {
            listener.updatedTurnFor(player, Turn.advanceTo(ladderPositions.get(turn.nextPosition())));
        }
    }
}
