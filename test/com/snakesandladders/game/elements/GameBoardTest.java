package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameBoardTest {

    private GameBoard gameBoard;
    private Player playerOne = new Player(1, "one");
    private Player playerTwo = new Player(2, "two");

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

    @BeforeEach
    void setUp() {
        gameBoard = new GameBoard(snakesBoardPositions, ladderBoardPositions,
                new PlayerGroup(playerOne, playerTwo), new ConsoleLogger());
    }

    @Test
    void shouldUpdateTurnWithSnakeBiteWhenLandsOnASnake() {
        Turn updatedTurn = gameBoard.evaluateTurn(Turn.advanceTo(38));

        assertEquals(11, updatedTurn.nextPosition());
    }

    @Test
    void shouldUpdateTurnWithLadderEndWhenLandsOnALadder() {
        Turn updatedTurn = gameBoard.evaluateTurn(Turn.advanceTo(12));

        assertEquals(53, updatedTurn.nextPosition());
    }

    @Test
    void shouldProvideDetailsOfCurrentPlayer() {
        assertEquals(playerOne, gameBoard.currentPlayer());
    }

    @Test
    void shouldSwitchToNextPlayer() {
        gameBoard.moveToNextPlayer();

        assertEquals(playerTwo, gameBoard.currentPlayer());
    }

}