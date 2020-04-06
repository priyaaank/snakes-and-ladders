package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;

public interface RuleEvaluationListener {

    void skipTurnFor(Player player);

    void yetToStart(Player player);

    void playerWon(Player player);

    void updatedTurnFor(Player player);
}
