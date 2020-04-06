package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.rules.RuleEvaluationListener;
import com.snakesandladders.game.rules.RuleEvaluator;
import com.snakesandladders.game.state.Turn;

import static java.lang.Boolean.TRUE;

public class Player {

    private int number;
    private String name;
    private RollBehavior dice;
    private Logger messageLogger;
    private Integer position;
    private Boolean isWinner;

    public Player(int number, String name, RollBehavior dice, Logger messageLogger) {
        this.number = number;
        this.name = name;
        this.dice = dice;
        this.messageLogger = messageLogger;
        this.position = 0;
        this.isWinner = Boolean.FALSE;
    }

    public Integer getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void takeTurn(RuleEvaluator ruleEvaluator, RuleEvaluationListener ruleEvaluationListener) {
        Integer newHopCount = dice.roll();
        messageLogger.log("Player " + number + " got dice roll of " + newHopCount);
        ruleEvaluator.evaluateRules(this, Turn.advanceBy(position, newHopCount), ruleEvaluationListener);
    }

    public void updatePosition(Turn turn) {
        this.position = turn.nextPosition();
    }

    public void declareWinner() {
        this.isWinner = TRUE;
    }

    public boolean isWinner() {
        return isWinner;
    }


}
