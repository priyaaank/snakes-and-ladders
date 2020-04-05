package com.snakesandladders.game.elements;

import com.snakesandladders.game.state.Turn;

public class Player {

    private int number;
    private String name;
    private RollBehavior dice;
    private Integer position;

    public Player(int number, String name, RollBehavior dice) {
        this.number = number;
        this.name = name;
        this.dice = dice;
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
        return Turn.advanceBy(position, dice.roll());
    }
}
