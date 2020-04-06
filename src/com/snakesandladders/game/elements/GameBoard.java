package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.rules.RuleEvaluationListener;
import com.snakesandladders.game.rules.RuleEvaluator;

public class GameBoard {

    private PlayerGroup playerGroup;
    private final Logger messageLogger;
    private RuleEvaluator ruleEvaluator;

    public GameBoard(PlayerGroup playerGroup, Logger messageLogger, RuleEvaluator ruleEvaluator) {
        this.playerGroup = playerGroup;
        this.messageLogger = messageLogger;
        this.ruleEvaluator = ruleEvaluator;
    }

    public Player currentPlayer() {
        return this.playerGroup.currentPlayer();
    }

    public void moveToNextPlayer() {
        messageLogger.log("Next position for player " + currentPlayer().getNumber() + " is " + currentPlayer().getPosition());
        this.playerGroup.moveToNextPlayer();
        messageLogger.log("Player " + currentPlayer().getName() + " will play next turn");
    }

    public void takeTurn(RuleEvaluationListener ruleEvaluationListener) {
        currentPlayer().takeTurn(ruleEvaluator, ruleEvaluationListener);
    }

}
