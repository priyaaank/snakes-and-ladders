package com.snakesandladders.game.state;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.rules.RuleEvaluationListener;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class CallbackRecorder implements RuleEvaluationListener {

    private Boolean playerWonCallbackCalled;
    private Boolean skipTurnCallbackCalled;
    private Boolean yetToStartCallbackCalled;
    private Boolean updateTurnCallBackCalled;
    private Turn updatedTurn;

    public CallbackRecorder() {
        this.playerWonCallbackCalled = FALSE;
        this.skipTurnCallbackCalled = FALSE;
        this.yetToStartCallbackCalled = FALSE;
        this.updateTurnCallBackCalled = FALSE;
        this.updatedTurn = null;
    }

    @Override
    public void skipTurnFor(Player player) {
        this.skipTurnCallbackCalled = TRUE;
    }

    @Override
    public void yetToStart(Player player) {
        this.yetToStartCallbackCalled = TRUE;
    }

    @Override
    public void playerWon(Player player) {
        this.playerWonCallbackCalled = TRUE;
    }

    @Override
    public void updatedTurnFor(Player player, Turn turn) {
        this.updateTurnCallBackCalled = TRUE;
        this.updatedTurn = turn;
    }

    public Boolean isPlayerWonCallbackCalled() {
        return playerWonCallbackCalled;
    }

    public Boolean isSkipTurnCallbackCalled() {
        return skipTurnCallbackCalled;
    }

    public Boolean isYetToStartCallbackCalled() {
        return yetToStartCallbackCalled;
    }

    public Boolean isUpdateTurnCallBackCalled() {
        return updateTurnCallBackCalled;
    }

    public Turn getUpdatedTurn() {
        return updatedTurn;
    }
}
