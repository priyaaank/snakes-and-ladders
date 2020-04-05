package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.state.Turn;

public interface GameRule {

    void evaluate(Player player, Turn turn, RuleEvaluationListener listener);

}
