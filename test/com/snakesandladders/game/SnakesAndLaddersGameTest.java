package com.snakesandladders.game;

import com.snakesandladders.game.elements.GameBoard;
import com.snakesandladders.game.elements.RollBehavior;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.BoardGameEvents;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnakesAndLaddersGameTest {

    private SnakesAndLaddersGame snakesAndLaddersGame;
    private Map<Integer, Integer> snakesBoardPositions = new HashMap<Integer, Integer>() {
        {
            put(18, 2);
            put(25, 8);
            put(38, 11);
            put(41, 19);
            put(59, 21);
            put(72, 12);
            put(78, 7);
            put(86, 31);
            put(92, 26);
            put(97, 5);
        }
    };

    private Map<Integer, Integer> ladderBoardPositions = new HashMap<Integer, Integer>() {
        {
            put(9, 32);
            put(12, 53);
            put(17, 90);
            put(21, 50);
            put(27, 66);
            put(29, 42);
            put(44, 73);
            put(63, 88);
        }
    };


    @Test
    void shouldFinishOneRoundOfGamePlaySuccessfully() {
        //given
        ProgrammableDice dice = new ProgrammableDice(1, 6, 5, 4, 6, 1, 2, 4, 6, 1, 2, 6, 3, 3, 2, 4, 3, 2, 6, 4, 6, 5, 3, 4, 4, 6, 4, 2, 5, 6, 6, 2, 6, 5, 4, 1, 3, 6, 6, 1, 3, 5, 1, 2, 6, 4, 3, 1, 2, 1, 4, 2, 5);
        InMemoryLogger msgLogger = new InMemoryLogger();
        BoardGameController controller = new BoardGameController();
        snakesAndLaddersGame = new SnakesAndLaddersGame(dice, msgLogger, new GameBoard(snakesBoardPositions, ladderBoardPositions, msgLogger), controller);

        try {
            //when
            snakesAndLaddersGame.beginGamePlay();
        } catch (RuntimeException rte) {
            assertEquals("Game finished!", rte.getMessage());
        }

        //then
        assertEquals("Player 2 got dice roll of 6", msgLogger.getMessageAtPosition(4));
        assertEquals("Player 1 got dice roll of 6", msgLogger.getMessageAtPosition(15));
        assertEquals("Player 4 got dice roll of 6", msgLogger.getMessageAtPosition(40));
        assertEquals("Player 3 got dice roll of 6", msgLogger.getMessageAtPosition(63));
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

    class BoardGameController implements BoardGameEvents {

        @Override
        public void finished() {
            throw new RuntimeException("Game finished!");
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