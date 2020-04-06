package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.rules.RuleEvaluationListener;
import com.snakesandladders.game.rules.RuleEvaluator;
import com.snakesandladders.game.state.Turn;

public class GameBoard implements RuleEvaluationListener {

    private PlayerGroup playerGroup;
    private final Logger messageLogger;
    private RuleEvaluator ruleEvaluator;
    private Boolean isGameInProgress;

    public GameBoard(PlayerGroup playerGroup, Logger messageLogger, RuleEvaluator ruleEvaluator) {
        this.isGameInProgress = Boolean.TRUE;
        this.playerGroup = playerGroup;
        this.messageLogger = messageLogger;
        this.ruleEvaluator = ruleEvaluator;
    }

    public Player currentPlayer() {
        return this.playerGroup.currentPlayer();
    }

    public void moveToNextPlayer() {
        messageLogger.log("Next position for player " + currentPlayer().getNumber() + " is " + currentPlayer().getPosition());
        this.playerGroup.moveToNextPlayer();
        messageLogger.log("Player " + currentPlayer().getName() + " will play next turn");
    }

    public void takeTurn() {
        Player currentPlayer = currentPlayer();
        currentPlayer.takeTurn(ruleEvaluator, this);
    }

    @Override
    public void skipTurnFor(Player player) {
        player.updatePosition(Turn.skipTurn(player.getPosition()));
        messageLogger.log("Player " + player.getName() + " needs to score exactly " + (100 - player.getPosition()) + " on dice roll to win. Passing chance.");
    }

    @Override
    public void yetToStart(Player player) {
        messageLogger.log("Player " + player.getName() + " did not score 6. First a 6 needs to be scored to start moving on board.");
    }

    @Override
    public void playerWon(Player player) {
        messageLogger.log("Player " + player.getName() + " wins! Game finished.");
        isGameInProgress = Boolean.FALSE;
    }

    @Override
    public void updatedTurnFor(Player player) {
        //do nothing
    }

    public Boolean isGameInProgress() {
        return isGameInProgress;
    }
}
