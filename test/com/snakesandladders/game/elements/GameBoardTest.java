package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.InMemoryLogger;
import com.snakesandladders.game.rules.RuleEvaluator;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GameBoardTest {

    private GameBoard gameBoard;
    private InMemoryLogger logger = new InMemoryLogger();
    private Player playerOne = new Player(1, "one", new RandomDice(), logger);
    private Player playerTwo = new Player(2, "two", new RandomDice(), logger);

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
                new PlayerGroup(playerOne, playerTwo), logger, new RuleEvaluator(snakesBoardPositions, ladderBoardPositions, logger));
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

    @Test
    void shouldLogASkipTurnMessageForAPlayer() {
        playerOne.updatePosition(Turn.advanceTo(3));

        gameBoard.skipTurnFor(playerOne);

        assertEquals("Player one needs to score exactly 97 on dice roll to win. Passing chance.", logger.getMessageAtPosition(0));
    }

    @Test
    void shouldUpdatePlayerPositionWithTurn() {
        playerOne.updatePosition(Turn.advanceTo(3));

        gameBoard.updatedTurnFor(playerOne, Turn.advanceBy(3, 4));

        assertEquals(7, playerOne.getPosition());
    }

    @Test
    void shouldLogMessageWhenPlayerWins() {
        playerOne.updatePosition(Turn.advanceTo(100));

        gameBoard.playerWon(playerOne);

        assertEquals("Player one wins! Game finished.", logger.getMessageAtPosition(0));
    }

    @Test
    void shouldLogMessageForYetToStartPlayer() {
        playerOne.updatePosition(Turn.advanceTo(0));

        gameBoard.yetToStart(playerOne);

        assertEquals("Player one did not score 6. First a 6 needs to be scored to start moving on board.", logger.getMessageAtPosition(0));
    }

    @Test
    void shouldIndicateWhenGameHasFinished() {
        gameBoard.playerWon(playerOne);

        assertFalse(gameBoard.isGameInProgress());
    }
}