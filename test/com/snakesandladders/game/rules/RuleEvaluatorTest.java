package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.elements.RandomDice;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.CallbackRecorder;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RuleEvaluatorTest {

    private RuleEvaluator ruleEvaluator;
    private HashMap<Integer, Integer> snakePositions;
    private HashMap<Integer, Integer> ladderPositions;
    private CallbackRecorder callbackRecorder;
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

        ruleEvaluator = new RuleEvaluator(snakePositions, ladderPositions, new ConsoleLogger());
        callbackRecorder = new CallbackRecorder();
    }

    @Test
    void shouldEvaluateRuleForDeclaringWinner() {
        playerOne.updatePosition(Turn.advanceTo(99));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(99, 1), callbackRecorder);

        assertTrue(callbackRecorder.isPlayerWonCallbackCalled());
    }

    @Test
    void shouldEvaluateRuleForSnakeBite() {
        playerOne.updatePosition(Turn.advanceTo(11));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(11, 1), callbackRecorder);

        assertTrue(callbackRecorder.isUpdateTurnCallBackCalled());
    }

    @Test
    void shouldEvaluateRuleForLadderClimb() {
        playerOne.updatePosition(Turn.advanceTo(10));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(10, 3), callbackRecorder);

        assertTrue(callbackRecorder.isUpdateTurnCallBackCalled());
    }

    @Test
    void shouldEvaluateRuleForSkippingTurn() {
        playerOne.updatePosition(Turn.advanceTo(99));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(99, 3), callbackRecorder);

        assertTrue(callbackRecorder.isSkipTurnCallbackCalled());
    }

    @Test
    void shouldEvaluateRuleForYetToStart() {
        playerOne.updatePosition(Turn.advanceTo(0));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(0, 3), callbackRecorder);

        assertTrue(callbackRecorder.isYetToStartCallbackCalled());
    }

    @Test
    void shouldEvaluateDefaultRuleForSimpleMove() {
        playerOne.updatePosition(Turn.advanceTo(22));
        ruleEvaluator.evaluateRules(playerOne, Turn.advanceBy(22, 3), callbackRecorder);

        assertTrue(callbackRecorder.isUpdateTurnCallBackCalled());
    }

}