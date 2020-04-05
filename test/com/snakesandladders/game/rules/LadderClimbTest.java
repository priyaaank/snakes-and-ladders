package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class LadderClimbTest {

    private LadderClimb ladderClimb;
    private Player playerOne;

    @BeforeEach
    void setUp() {
        this.ladderClimb = new LadderClimb(new HashMap<Integer, Integer>() {
            {
                put(12, 2);
            }
        });
        this.playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
    }

    @Test
    void shouldUpdatePlayerPositionWhenBittenBySnake() {
        LadderClimbCall ladderClimbCall = new LadderClimbCall(FALSE);

        this.ladderClimb.evaluate(playerOne, Turn.advanceTo(12), snakeBiteUpdater(ladderClimbCall));

        assertTrue(ladderClimbCall.climbedLadder);
        assertEquals(2, ladderClimbCall.newTurn.nextPosition());
    }

    @Test
    void shouldDoNothingWhenSnakeNotEncountered() {
        LadderClimbCall ladderClimbCall = new LadderClimbCall(FALSE);

        this.ladderClimb.evaluate(playerOne, Turn.advanceTo(2), snakeBiteUpdater(ladderClimbCall));

        assertFalse(ladderClimbCall.climbedLadder);
        assertNull(ladderClimbCall.newTurn);
    }

    private RuleEvaluationListener snakeBiteUpdater(LadderClimbCall ladderClimbCall) {
        return new RuleEvaluationListener() {
            @Override
            public void skipTurnFor(Player player) { /* Do nothing */ }

            @Override
            public void playerWon(Player player) { /* Do nothing */ }

            @Override
            public void updatedTurnFor(Player player, Turn turn) {
                ladderClimbCall.climbedLadder = TRUE;
                ladderClimbCall.newTurn = turn;
            }
        };
    }

    private static class LadderClimbCall {
        Boolean climbedLadder;
        Turn newTurn;

        public LadderClimbCall(Boolean climbedLadder) {
            this.climbedLadder = climbedLadder;
        }
    }

}