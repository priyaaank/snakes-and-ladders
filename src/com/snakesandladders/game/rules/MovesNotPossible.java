package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.state.Turn;

public class MovesNotPossible implements GameRule {

    @Override
    public void evaluate(Player player, Turn turn, RuleEvaluationListener listener) {
        if (turn.nextPosition() > 100 || (player.getPosition() == 0 && !turn.hasRolledASix())) {
            listener.skipTurnFor(player);
        }
    }
}
