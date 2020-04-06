package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.CallbackRecorder;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovesNotPossibleTest {

    private MovesNotPossible hopsNotPossibleRule;
    private Player playerOne;
    private CallbackRecorder callbackRecorder;

    @BeforeEach
    void setUp() {
        playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        hopsNotPossibleRule = new MovesNotPossible();
        callbackRecorder = new CallbackRecorder();
    }

    @Test
    void shouldNotSkipTurnIfMovementPossible() {
        playerOne.updatePosition(Turn.advanceTo(98));
        hopsNotPossibleRule.evaluate(playerOne, Turn.advanceBy(98, 1), callbackRecorder);

        assertFalse(callbackRecorder.isSkipTurnCallbackCalled());
    }

    @Test
    void shouldCallSkipTurnCallbackWhenMovementNotPossible() {
        playerOne.updatePosition(Turn.advanceTo(98));
        hopsNotPossibleRule.evaluate(playerOne, Turn.advanceBy(98, 3), callbackRecorder);

        assertTrue(callbackRecorder.isSkipTurnCallbackCalled());
    }

}