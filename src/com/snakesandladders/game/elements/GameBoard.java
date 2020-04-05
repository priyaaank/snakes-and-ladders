package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.GameEventsListener;
import com.snakesandladders.game.state.Turn;

import java.util.Map;

public class GameBoard {

    private final Map<Integer, Integer> snakesBoardPositions;
    private final Map<Integer, Integer> ladderBoardPositions;
    private PlayerGroup playerGroup;
    private final Logger messageLogger;

    public GameBoard(Map<Integer, Integer> snakesBoardPositions, Map<Integer, Integer> ladderBoardPositions,
                     PlayerGroup playerGroup, Logger messageLogger) {
        this.snakesBoardPositions = snakesBoardPositions;
        this.ladderBoardPositions = ladderBoardPositions;
        this.playerGroup = playerGroup;
        this.messageLogger = messageLogger;
    }

    public Turn evaluateTurn(Turn currentTurn) {

        if (bittenBySnake(currentTurn.nextPosition())) {
            messageLogger.log("Player got bit by snake a position " + currentTurn.nextPosition());
            return Turn.advanceTo(snakesBoardPositions.get(currentTurn.nextPosition()));
        }

        if (chancedUponALadder(currentTurn.nextPosition())) {
            messageLogger.log("Player got chanced upon a ladder at position " + currentTurn.nextPosition() + "!");
            return Turn.advanceTo(ladderBoardPositions.get(currentTurn.nextPosition()));
        }

        return currentTurn;
    }

    public Player currentPlayer() {
        return this.playerGroup.currentPlayer();
    }

    public void moveToNextPlayer() {
        this.playerGroup.moveToNextPlayer();
    }

    public boolean hopsNotPossible(int newPosition) {
        return newPosition > 100;
    }

    public boolean reachedWinningPosition(int newPosition) {
        return newPosition == 100;
    }

    public boolean yetToStart(int playerOnePosition) {
        return playerOnePosition == 0;
    }

    public boolean hasntRolledASix(int newHopCount) {
        return newHopCount != 6;
    }

    private boolean bittenBySnake(int newPosition) {
        return snakesBoardPositions.get(newPosition) != null;
    }

    private boolean chancedUponALadder(int newPosition) {
        return ladderBoardPositions.get(newPosition) != null;
    }

    public Turn takeTurn(Player player, int newHopCount, GameEventsListener gameEventsListener) {
        Turn turn = Turn.advanceBy(player.getPosition(), newHopCount);

        if (hopsNotPossible(turn.nextPosition())) {
            messageLogger.log("Player " + player.getName() + " needs to score exactly " + (100 - player.getPosition()) + " on dice roll to win. Passing chance.");
            return Turn.skipTurn(player.getPosition());
        }

        if (reachedWinningPosition(turn.nextPosition())) {
            messageLogger.log("Player " + player.getName() + " wins! Game finished.");
            gameEventsListener.gameFinished();
        }

        if (yetToStart(player.getPosition()) && hasntRolledASix(newHopCount)) {
            messageLogger.log("Player " + player.getName() + " did not score 6. First a 6 needs to be scored to start moving on board.");
            return Turn.skipTurn(player.getPosition());
        }

        return evaluateTurn(turn);
    }

    public void updatePlayerPosition(int newHopCount, GameEventsListener gameEventsListener) {
        Player currentPlayer = currentPlayer();
        Integer nextPosition = takeTurn(currentPlayer, newHopCount, gameEventsListener).nextPosition();
        currentPlayer.setPosition(nextPosition);
    }
}