package com.snakesandladders.game.props;

import java.util.Random;

public class RandomDice implements RollBehavior {
    @Override
    public Integer roll() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
    }
}
