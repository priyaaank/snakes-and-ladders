package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.*;

class YetToStartTest {

    private YetToStart yetToStart;
    private Player playerOne;

    @BeforeEach
    void setUp() {
        this.yetToStart = new YetToStart();
        this.playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
    }

    @Test
    void shouldSkipTurnIfPlayerHasNotStartedMovingAndHasNotScoredASix() {
        ShouldSkipMovement call = new ShouldSkipMovement(FALSE);
        playerOne.updatePosition(Turn.advanceTo(0));

        yetToStart.evaluate(playerOne, Turn.advanceBy(0, 4), turnSkipUpdater(call));

        assertTrue(call.hopsSkipped);
    }

    private RuleEvaluationListener turnSkipUpdater(ShouldSkipMovement call) {
        return new RuleEvaluationListener() {
            @Override
            public void skipTurnFor(Player player) { /* Do nothing */}

            @Override
            public void yetToStart(Player player) {
                call.hopsSkipped = TRUE;
            }

            @Override
            public void playerWon(Player player) { /* Do nothing */ }

            @Override
            public void updatedTurnFor(Player player, Turn turn) { /* Do nothing */ }
        };
    }

    private static class ShouldSkipMovement {
        private Boolean hopsSkipped;

        ShouldSkipMovement(Boolean skipHops) {
            this.hopsSkipped = skipHops;
        }
    }

}