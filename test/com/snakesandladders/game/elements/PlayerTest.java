package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.rules.RuleEvaluationListener;
import com.snakesandladders.game.rules.RuleEvaluator;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Logger logger = new ConsoleLogger();
    private final HashMap<Integer, Integer> emptySnakeAndLadderPositions = new HashMap<>();
    private RuleEvaluator ruleEvaluator = new RuleEvaluator(emptySnakeAndLadderPositions, emptySnakeAndLadderPositions, logger);

    @Test
    void shouldBeAbleToGivePlayerNumber() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals(1, playerOne.getNumber());
    }

    @Test
    void shouldBeAbleToGivePlayerName() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals("one", playerOne.getName());
    }

    @Test
    void shouldHaveZeroAsTheDefaultPosition() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals(0, playerOne.getPosition());
    }

    @Test
    void shouldBeAbleToRollDice() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);
        playerOne.updatePosition(Turn.advanceTo(12));

        playerOne.takeTurn(ruleEvaluator, getRuleEvaluationListener(playerOne));

        assertEquals(14, playerOne.getPosition());
    }

    @Test
    void shouldUpdatePositionBasedUsingTurn() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);

        playerOne.updatePosition(Turn.advanceBy(3, 3));

        assertEquals(6, playerOne.getPosition());
    }

    private RuleEvaluationListener getRuleEvaluationListener(final Player playerToUpdate) {
        return new RuleEvaluationListener() {
            @Override
            public void skipTurnFor(Player player) {

            }

            @Override
            public void yetToStart(Player player) {

            }

            @Override
            public void playerWon(Player player) {

            }

            @Override
            public void updatedTurnFor(Player player, Turn turn) {
                playerToUpdate.updatePosition(turn);
            }
        };
    }
}