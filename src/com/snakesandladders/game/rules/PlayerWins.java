package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.state.Turn;

public class PlayerWins implements GameRule {

    @Override
    public void evaluate(Player player, Turn turn, RuleEvaluationListener listener) {
        if (turn.hasReachedHundred()) {
            listener.playerWon(player);
        }
    }

}
