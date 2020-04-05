package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.Turn;

public class Player {

    private int number;
    private String name;
    private RollBehavior dice;
    private Logger messageLogger;
    private Integer position;

    public Player(int number, String name, RollBehavior dice, Logger messageLogger) {
        this.number = number;
        this.name = name;
        this.dice = dice;
        this.messageLogger = messageLogger;
        this.position = 0;
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

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Turn takeTurn() {
        Integer newHopCount = dice.roll();
        messageLogger.log("Player " + number + " got dice roll of " + newHopCount);
        return Turn.advanceBy(position, newHopCount);
    }

    public void updatePosition(Turn turn) {
        this.position = turn.nextPosition();
    }
}
