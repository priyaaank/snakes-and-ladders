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

class PlayerWinsTest {

    private PlayerWins playerWinsRule;
    private Player playerOne;
    private CallbackRecorder callbackRecorder;

    @BeforeEach
    void setUp() {
        playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        playerWinsRule = new PlayerWins();
        callbackRecorder = new CallbackRecorder();
    }

    @Test
    void shouldCallPlayerWinsCallbackWhenPlayerWins() {
        playerWinsRule.evaluate(playerOne, Turn.advanceTo(100), callbackRecorder);

        assertTrue(callbackRecorder.isPlayerWonCallbackCalled());
    }

    @Test
    void shouldNotDoAnythingWhenPlayerHasNotWon() {
        playerWinsRule.evaluate(playerOne, Turn.advanceTo(99), callbackRecorder);

        assertFalse(callbackRecorder.isPlayerWonCallbackCalled());
    }

    @Test
    void shouldMarkPlayerAsWinnerWhenPlayerWins() {
        playerWinsRule.evaluate(playerOne, Turn.advanceTo(100), callbackRecorder);

        assertTrue(playerOne.isWinner());
    }
}