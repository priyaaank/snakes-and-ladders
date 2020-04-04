package com.snakesandladders.game;

import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.props.RollBehavior;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnakesAndLaddersGameTest {

    private SnakesAndLaddersGame snakesAndLaddersGame;

    @Test
    void shouldFinishOneRoundOfGamePlaySuccessfully() {
        //given
        ProgrammableDice dice = new ProgrammableDice(1, 6, 5, 4, 6, 1, 2, 4, 6, 1, 2, 6, 3, 3, 2, 4, 3, 2, 6, 4, 6, 5, 3, 4, 4, 6, 4, 2, 5, 6, 6, 2, 6, 5, 4, 1, 3, 6, 6, 1, 3, 5, 1, 2, 6, 4, 3, 1, 2, 1, 4, 2, 5);
        InMemoryLogger msgLogger = new InMemoryLogger();
        snakesAndLaddersGame = new SnakesAndLaddersGame(dice, msgLogger);

        //when
        snakesAndLaddersGame.beginGamePlay();

        //then -- ??
        assertEquals("Player one wins! Game finished.", msgLogger.getMessageAtPosition(172));
    }

    class InMemoryLogger implements Logger {

        private List<String> messageHistory;

        public InMemoryLogger() {
            this.messageHistory = new ArrayList<>();
        }

        @Override
        public void log(String message) {
            System.out.println("Storing message " + message);
            this.messageHistory.add(message);
        }

        public String getMessageAtPosition(Integer index) {
            return messageHistory.get(index);
        }
    }

    class ProgrammableDice implements RollBehavior {

        private int rollIndex;
        private List<Integer> rollValues;

        ProgrammableDice(Integer... rollNumbers) {
            this.rollValues = Arrays.asList(rollNumbers);
            this.rollIndex = 0;
        }

        @Override
        public Integer roll() {
            assert rollIndex < rollValues.size();
            return rollValues.get(rollIndex++);
        }
    }
}