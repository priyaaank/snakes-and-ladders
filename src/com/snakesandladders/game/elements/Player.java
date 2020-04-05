package com.snakesandladders.game.elements;

public class Player {

    private int number;
    private String name;

    public Player(int number, String name) {
        this.number = number;
        this.name = name;
    }

    public Integer getNumber() {
        return this.number;
    }

    public String getName() {
        return this.name;
    }
}
