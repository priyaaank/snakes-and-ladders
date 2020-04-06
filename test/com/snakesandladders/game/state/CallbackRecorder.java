package com.snakesandladders.game.state;

import com.snakesandladders.game.elements.Player;
import com.snakesandladders.game.rules.RuleEvaluationListener;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class CallbackRecorder implements RuleEvaluationListener {

    private Player player;
    private Boolean playerWonCallbackCalled;
    private Boolean skipTurnCallbackCalled;
    private Boolean yetToStartCallbackCalled;
    private Boolean updateTurnCallBackCalled;

    public CallbackRecorder() {
        this.playerWonCallbackCalled = FALSE;
        this.skipTurnCallbackCalled = FALSE;
        this.yetToStartCallbackCalled = FALSE;
        this.updateTurnCallBackCalled = FALSE;
        this.player = null;
    }

    @Override
    public void skipTurnFor(Player player) {
        this.player = player;
        this.skipTurnCallbackCalled = TRUE;
    }

    @Override
    public void yetToStart(Player player) {
        this.player = player;
        this.yetToStartCallbackCalled = TRUE;
    }

    @Override
    public void playerWon(Player player) {
        this.player = player;
        this.playerWonCallbackCalled = TRUE;
    }

    @Override
    public void updatedTurnFor(Player player) {
        this.player = player;
        this.updateTurnCallBackCalled = TRUE;
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

    public Player getPlayer() {
        return player;
    }
}
