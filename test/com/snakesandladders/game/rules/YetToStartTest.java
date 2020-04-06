package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.CallbackRecorder;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class YetToStartTest {

    private YetToStart yetToStart;
    private Player playerOne;
    private CallbackRecorder callbackRecorder;

    @BeforeEach
    void setUp() {
        this.yetToStart = new YetToStart();
        this.playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        this.callbackRecorder = new CallbackRecorder();
    }

    @Test
    void shouldSkipTurnIfPlayerHasNotStartedMovingAndHasNotScoredASix() {
        playerOne.updatePosition(Turn.advanceTo(0));

        yetToStart.evaluate(playerOne, Turn.advanceBy(0, 4), callbackRecorder);

        assertTrue(callbackRecorder.isYetToStartCallbackCalled());
    }

}