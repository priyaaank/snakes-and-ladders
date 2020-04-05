package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.state.Turn;

public interface RuleEvaluationListener {

    void skipTurnFor(Player player);

    void yetToStart(Player player);

    void playerWon(Player player);

    void updatedTurnFor(Player player, Turn turn);
}
