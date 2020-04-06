package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.CallbackRecorder;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleMoveTest {

    private SimpleMove simpleMove;
    private Player playerOne;
    private CallbackRecorder callbackRecorder;

    @BeforeEach
    void setUp() {
        this.playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        this.simpleMove = new SimpleMove();
        this.callbackRecorder = new CallbackRecorder();
    }

    @Test
    void shouldUpdateTurnBasedOnRolledNumber() {
        playerOne.updatePosition(Turn.advanceTo(23));
        this.simpleMove.evaluate(playerOne, Turn.advanceBy(23, 2), callbackRecorder);

        assertEquals(25, callbackRecorder.getPlayer().getPosition());
    }

    @Test
    void shouldTriggerUpdatePositionCallback() {
        playerOne.updatePosition(Turn.advanceTo(23));
        this.simpleMove.evaluate(playerOne, Turn.advanceBy(23, 2), callbackRecorder);

        assertTrue(callbackRecorder.isUpdateTurnCallBackCalled());
    }
    
}