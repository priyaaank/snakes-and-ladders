package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleEvaluatorTest {

    private RuleEvaluator ruleEvaluator;
    private HashMap<Integer, Integer> snakePositions;
    private HashMap<Integer, Integer> ladderPositions;
    private Player playerOne;

    @BeforeEach
    void setUp() {
        playerOne = new Player(1, "one", new RandomDice(), new ConsoleLogger());
        snakePositions = new HashMap<Integer, Integer>() {
            {
                put(12, 2);
            }
        };

        ladderPositions = new HashMap<Integer, Integer>() {
            {
                put(13, 89);
            }
        };

        ruleEvaluator = new RuleEvaluator(snakePositions, ladderPositions);
    }

    @Test
    void shouldEvaluateRuleForDeclaringWinner() {
        RuleEvaluationResult result = new RuleEvaluationResult();

        playerOne.updatePosition(Turn.advanceTo(99));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(99, 1), getRuleEvaluationListener(result));

        assertEquals("winner", result.evaluatedRuleName);
    }

    @Test
    void shouldEvaluateRuleForSnakeBite() {
        RuleEvaluationResult result = new RuleEvaluationResult();

        playerOne.updatePosition(Turn.advanceTo(11));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(11, 1), getRuleEvaluationListener(result));

        assertEquals("updatedTurn", result.evaluatedRuleName);
    }

    @Test
    void shouldEvaluateRuleForLadderClimb() {
        RuleEvaluationResult result = new RuleEvaluationResult();

        playerOne.updatePosition(Turn.advanceTo(10));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(10, 3), getRuleEvaluationListener(result));

        assertEquals("updatedTurn", result.evaluatedRuleName);
    }

    @Test
    void shouldEvaluateRuleForSkippingTurn() {
        RuleEvaluationResult result = new RuleEvaluationResult();

        playerOne.updatePosition(Turn.advanceTo(99));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(99, 3), getRuleEvaluationListener(result));

        assertEquals("skipTurn", result.evaluatedRuleName);
    }

    @Test
    void shouldEvaluateRuleForYetToStart() {
        RuleEvaluationResult result = new RuleEvaluationResult();

        playerOne.updatePosition(Turn.advanceTo(0));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(0, 3), getRuleEvaluationListener(result));

        assertEquals("yetToStart", result.evaluatedRuleName);
    }

    @Test
    void shouldEvaluateDefaultRuleForSimpleMove() {
        RuleEvaluationResult result = new RuleEvaluationResult();

        playerOne.updatePosition(Turn.advanceTo(22));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(22, 3), getRuleEvaluationListener(result));

        assertEquals("updatedTurn", result.evaluatedRuleName);
    }

    private static class RuleEvaluationResult {
        String evaluatedRuleName = "NONE";
    }

    private RuleEvaluationListener getRuleEvaluationListener(RuleEvaluationResult result) {
        return new RuleEvaluationListener() {

            @Override
            public void skipTurnFor(Player player) {
                result.evaluatedRuleName = "skipTurn";
            }

            @Override
            public void yetToStart(Player player) { result.evaluatedRuleName = "yetToStart"; }

            @Override
            public void playerWon(Player player) {
                result.evaluatedRuleName = "winner";
            }

            @Override
            public void updatedTurnFor(Player player, Turn turn) {
                result.evaluatedRuleName = "updatedTurn";
            }
        };
    }
}