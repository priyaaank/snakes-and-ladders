package com.snakesandladders.game.elements;

public class Player {

    private int number;
    private String name;
    private Integer position;

    public Player(int number, String name) {
        this.number = number;
        this.name = name;
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
}
