package com.snakesandladders.game;

import com.snakesandladders.game.elements.GameBoard;
import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.PlayerGroup;
import com.snakesandladders.game.elements.ProgrammableDice;
import com.snakesandladders.game.io.InMemoryLogger;
import com.snakesandladders.game.rules.RuleEvaluator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnakesAndLaddersGameTest {

    private SnakesAndLaddersGame snakesAndLaddersGame;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
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
        InMemoryLogger msgLogger = new InMemoryLogger();
        ProgrammableDice dice = new ProgrammableDice(1, 6, 5, 4, 6, 1, 2, 4, 6, 1, 2, 6, 3, 3, 2, 4, 3, 2, 6, 4, 6, 5, 3, 4, 4, 6, 4, 2, 5, 6, 6, 2, 6, 5, 4, 1, 3, 6, 6, 1, 3, 5, 1, 2, 6, 4, 3, 1, 2, 1, 4, 2, 5);
        playerOne = new Player(1, "one", dice, msgLogger);
        playerTwo = new Player(2, "two", dice, msgLogger);
        playerThree = new Player(3, "three", dice, msgLogger);
        playerFour = new Player(4, "four", dice, msgLogger);
        PlayerGroup playerGroup = new PlayerGroup(playerOne, playerTwo, playerThree, playerFour);
        snakesAndLaddersGame = new SnakesAndLaddersGame(new GameBoard(snakesBoardPositions, ladderBoardPositions, playerGroup, msgLogger, new RuleEvaluator(snakesBoardPositions, ladderBoardPositions, msgLogger)));

        snakesAndLaddersGame.beginGamePlay();

        //then
        assertEquals("Player 2 got dice roll of 6", msgLogger.getMessageAtPosition(4));
        assertEquals("Player 1 got dice roll of 6", msgLogger.getMessageAtPosition(15));
        assertEquals("Player 4 got dice roll of 6", msgLogger.getMessageAtPosition(40));
        assertEquals("Player 3 got dice roll of 6", msgLogger.getMessageAtPosition(63));
        assertEquals("Player one wins! Game finished.", msgLogger.getMessageAtPosition(172));
    }

}