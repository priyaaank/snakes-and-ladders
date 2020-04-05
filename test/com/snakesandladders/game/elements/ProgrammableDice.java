package com.snakesandladders.game.elements;

import java.util.Arrays;
import java.util.List;

public class ProgrammableDice implements RollBehavior {

    private int rollIndex;
    private List<Integer> rollValues;

    public ProgrammableDice(Integer... rollNumbers) {
        this.rollValues = Arrays.asList(rollNumbers);
        this.rollIndex = 0;
    }

    @Override
    public Integer roll() {
        assert rollIndex < rollValues.size();
        return rollValues.get(rollIndex++);
    }
}
