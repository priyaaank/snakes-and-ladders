package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleMoveTest {

    private SimpleMove simpleMove;
    private Player playerOne;

    @BeforeEach
    void setUp() {
        playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        this.simpleMove = new SimpleMove();
    }

    @Test
    void shouldUpdateTurnBasedOnRolledNumber() {
        MoveUpdateResult result = new MoveUpdateResult(Boolean.FALSE);

        this.simpleMove.evaluate(playerOne, Turn.advanceBy(23, 2), getMoveUpdater(result));

        assertTrue(result.turnUpdated);
        assertEquals(25, result.updatedTurn.nextPosition());
    }

    private static class MoveUpdateResult {
        Boolean turnUpdated;
        Turn updatedTurn;

        public MoveUpdateResult(Boolean turnUpdated) {
            this.turnUpdated = turnUpdated;
        }
    }

    private RuleEvaluationListener getMoveUpdater(MoveUpdateResult result) {
        return new RuleEvaluationListener() {
            @Override
            public void skipTurnFor(Player player) { /* Do Nothing */ }

            @Override
            public void yetToStart(Player player) { /* Do nothing */ }

            @Override
            public void playerWon(Player player) { /* Do Nothing */ }

            @Override
            public void updatedTurnFor(Player player, Turn turn) {
                result.turnUpdated = Boolean.TRUE;
                result.updatedTurn = turn;
            }
        };
    }


}