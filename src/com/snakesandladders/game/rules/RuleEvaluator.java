package com.snakesandladders.game.rules;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.Turn;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class RuleEvaluator {

    private List<GameRule> rules;

    public RuleEvaluator(Map<Integer, Integer> snakePositions, Map<Integer, Integer> ladderPositions, Logger logger) {
        this.rules = Arrays.asList(
                new MovesNotPossible(),
                new YetToStart(),
                new PlayerWins(),
                new SnakeBite(snakePositions, logger),
                new LadderClimb(ladderPositions, logger),
                new SimpleMove()
        );
    }

    public void evaluateRules(Player player, Turn turn, RuleEvaluationListener listener) {
        EvaluationInterrupter evaluationInterrupter = new EvaluationInterrupter(FALSE);
        this.rules.forEach(rule -> {
            if (!evaluationInterrupter.terminateEvaluation)
                rule.evaluate(player, turn, callbackDelegator(listener, evaluationInterrupter));
        });
    }

    private RuleEvaluationListener callbackDelegator(RuleEvaluationListener listener,
                                                     EvaluationInterrupter evaluationInterrupter) {
        return new RuleEvaluationListener() {
            @Override
            public void skipTurnFor(Player player) {
                evaluationInterrupter.terminateEvaluation = TRUE;
                listener.skipTurnFor(player);
            }

            @Override
            public void yetToStart(Player player) {
                evaluationInterrupter.terminateEvaluation = TRUE;
                listener.yetToStart(player);
            }

            @Override
            public void playerWon(Player player) {
                evaluationInterrupter.terminateEvaluation = TRUE;
                listener.playerWon(player);
            }

            @Override
            public void updatedTurnFor(Player player, Turn turn) {
                evaluationInterrupter.terminateEvaluation = TRUE;
                listener.updatedTurnFor(player, turn);
            }
        };
    }

    private static class EvaluationInterrupter {

        Boolean terminateEvaluation;

        public EvaluationInterrupter(Boolean terminateEvaluation) {
            this.terminateEvaluation = terminateEvaluation;
        }
    }
}
