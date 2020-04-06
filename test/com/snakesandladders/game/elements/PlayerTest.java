package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.rules.RuleEvaluator;
import com.snakesandladders.game.state.CallbackRecorder;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Logger logger = new ConsoleLogger();
    private HashMap<Integer, Integer> emptySnakeAndLadderPositions;
    private RuleEvaluator ruleEvaluator;
    private CallbackRecorder callbackRecorder;

    @BeforeEach
    void setUp() {
        emptySnakeAndLadderPositions = new HashMap<>();
        ruleEvaluator = new RuleEvaluator(emptySnakeAndLadderPositions, emptySnakeAndLadderPositions, logger);
        callbackRecorder = new CallbackRecorder();
    }

    @Test
    void shouldBeAbleToGivePlayerNumber() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals(1, playerOne.getNumber());
    }

    @Test
    void shouldBeAbleToGivePlayerName() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals("one", playerOne.getName());
    }

    @Test
    void shouldHaveZeroAsTheDefaultPosition() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals(0, playerOne.getPosition());
    }

    @Test
    void shouldBeAbleToRollDice() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);
        playerOne.updatePosition(Turn.advanceTo(12));

        playerOne.takeTurn(ruleEvaluator, callbackRecorder);

        assertEquals(14, callbackRecorder.getPlayer().getPosition());
    }

    @Test
    void shouldUpdatePositionBasedUsingTurn() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);

        playerOne.updatePosition(Turn.advanceBy(3, 3));

        assertEquals(6, playerOne.getPosition());
    }

    @Test
    void shouldNotBeAWinnerByDefault() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);

        assertFalse(playerOne.isWinner());
    }

    @Test
    void shouldBeAbleToDeclarePlayerAsWinner() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);

        playerOne.declareWinner();

        assertTrue(playerOne.isWinner());
    }
    
}