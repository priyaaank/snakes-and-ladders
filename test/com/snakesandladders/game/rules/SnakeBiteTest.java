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

class SnakeBiteTest {

    private SnakeBite snakeBite;
    private Player playerOne;

    @BeforeEach
    void setUp() {
        this.snakeBite = new SnakeBite(new HashMap<Integer, Integer>() {
            {
                put(12, 2);
            }
        });
        this.playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
    }

    @Test
    void shouldUpdatePlayerPositionWhenBittenBySnake() {
        SnakeBiteCall snakeBiteCall = new SnakeBiteCall(FALSE);

        this.snakeBite.evaluate(playerOne, Turn.advanceTo(12), snakeBiteUpdater(snakeBiteCall));

        assertTrue(snakeBiteCall.bittenBySnake);
        assertEquals(2, snakeBiteCall.newTurn.nextPosition());
    }

    @Test
    void shouldDoNothingWhenSnakeNotEncountered() {
        SnakeBiteCall snakeBiteCall = new SnakeBiteCall(FALSE);

        this.snakeBite.evaluate(playerOne, Turn.advanceTo(2), snakeBiteUpdater(snakeBiteCall));

        assertFalse(snakeBiteCall.bittenBySnake);
        assertNull(snakeBiteCall.newTurn);
    }

    private RuleEvaluationListener snakeBiteUpdater(SnakeBiteCall snakeBiteCall) {
        return new RuleEvaluationListener() {
            @Override
            public void skipTurnFor(Player player) { /* Do nothing */ }

            @Override
            public void playerWon(Player player) { /* Do nothing */ }

            @Override
            public void updatedTurnFor(Player player, Turn turn) {
                snakeBiteCall.bittenBySnake = TRUE;
                snakeBiteCall.newTurn = turn;
            }
        };
    }

    private static class SnakeBiteCall {
        Boolean bittenBySnake;
        Turn newTurn;

        public SnakeBiteCall(Boolean bittenBySnake) {
            this.bittenBySnake = bittenBySnake;
        }
    }

}