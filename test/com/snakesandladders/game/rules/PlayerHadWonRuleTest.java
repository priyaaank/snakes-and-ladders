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

class PlayerHadWonRuleTest {

    private PlayerWins playerWinsRule;
    private Player playerOne;

    @BeforeEach
    void setUp() {
        playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        playerWinsRule = new PlayerWins();
    }

    @Test
    void shouldIndicateThatPlayerHasWonWhenItReachedHundred() {
        PlayerWonCall playerWonCall = new PlayerWonCall(FALSE);

        playerWinsRule.evaluate(playerOne, Turn.advanceTo(100), updateWinningState(playerWonCall));

        assertTrue(playerWonCall.playerWon);
    }

    @Test
    void shouldNotDoAnythingWhenPlayerHasNotWon() {
        PlayerWonCall playerWonCall = new PlayerWonCall(FALSE);

        playerWinsRule.evaluate(playerOne, Turn.advanceTo(99), updateWinningState(playerWonCall));

        assertFalse(playerWonCall.playerWon);
    }

    private RuleEvaluationListener updateWinningState(PlayerWonCall playerWonCall) {
        return new RuleEvaluationListener() {
            @Override
            public void skipTurnFor(Player player) {
                //do nothing
            }

            @Override
            public void yetToStart(Player player) { /* Do nothing */ }

            @Override
            public void playerWon(Player player) {
                playerWonCall.playerWon = TRUE;
            }

            @Override
            public void updatedTurnFor(Player player, Turn turn) { /* Do nothing */ }
        };
    }

    private static class PlayerWonCall {

        Boolean playerWon;

        public PlayerWonCall(Boolean playerWon) {
            this.playerWon = playerWon;
        }
    }


}