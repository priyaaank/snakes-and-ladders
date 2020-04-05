package com.snakesandladders.game.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomDiceTest {

    private RandomDice randomDice;

    @BeforeEach
    void setUp() {
        randomDice = new RandomDice();
    }

    @Test
    void shouldRollOnlyATotalOfSixDifferentNumbers() {
        //given
        Map<Integer, Integer> diceThrows = new HashMap<>();

        //when
        diceThrows = throwDiceForCount(50);

        //then
        assertEquals(6, diceThrows.size(), "Expected six different numbers. But were " + diceThrows.size());
    }

    @Test
    void shouldExpectEachOfTheSixNumbersToBeBetweenZeroAndSeven() {
        //given
        Map<Integer, Integer> diceThrows = new HashMap<Integer, Integer>();

        //when
        diceThrows = throwDiceForCount(50);

        //then
        for (Integer number : diceThrows.keySet()) {
            assertTrue(number < 7 && number > 0, "Dice roll was expected to be a number between 0 and 7. Was " + number);
        }
    }

    private Map<Integer, Integer> throwDiceForCount(Integer count) {
        Map<Integer, Integer> numberRollCount = new HashMap<>();

        Integer rolledNumber;
        for (int rollCount = 0; rollCount < count; rollCount++) {
            rolledNumber = randomDice.roll();
            numberRollCount.put(rolledNumber, numberRollCount.getOrDefault(rolledNumber, 0) + 1);
        }

        return numberRollCount;
    }

}