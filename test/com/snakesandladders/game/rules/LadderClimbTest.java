package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.CallbackRecorder;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class LadderClimbTest {

    private LadderClimb ladderClimb;
    private Player playerOne;
    private CallbackRecorder callbackRecorder;

    @BeforeEach
    void setUp() {
        this.ladderClimb = new LadderClimb(new HashMap<Integer, Integer>() {
            {
                put(12, 2);
            }
        }, new ConsoleLogger());
        this.playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        callbackRecorder = new CallbackRecorder();
    }

    @Test
    void shouldUpdatePlayerPositionWhenBittenBySnake() {
        this.ladderClimb.evaluate(playerOne, Turn.advanceTo(12), callbackRecorder);

        assertTrue(callbackRecorder.isUpdateTurnCallBackCalled());
        assertEquals(2, callbackRecorder.getUpdatedTurn().nextPosition());
    }

    @Test
    void shouldDoNothingWhenSnakeNotEncountered() {
        this.ladderClimb.evaluate(playerOne, Turn.advanceTo(2), callbackRecorder);

        assertFalse(callbackRecorder.isUpdateTurnCallBackCalled());
        assertNull(callbackRecorder.getUpdatedTurn());
    }

}