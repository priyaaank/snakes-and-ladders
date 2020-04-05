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
        messageLogger.log("Next position for player " + currentPlayer().getNumber() + " is " + currentPlayer().getPosition());
        this.playerGroup.moveToNextPlayer();
        messageLogger.log("Player " + currentPlayer().getName() + " will play next turn");
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

    private boolean bittenBySnake(int newPosition) {
        return snakesBoardPositions.get(newPosition) != null;
    }

    private boolean chancedUponALadder(int newPosition) {
        return ladderBoardPositions.get(newPosition) != null;
    }

    public void takeTurn(GameEventsListener gameEventsListener) {
        Player currentPlayer = currentPlayer();
        Turn turn = currentPlayer.takeTurn();

        if (hopsNotPossible(turn.nextPosition())) {
            messageLogger.log("Player " + currentPlayer.getName() + " needs to score exactly " + (100 - currentPlayer.getPosition()) + " on dice roll to win. Passing chance.");
            turn = Turn.skipTurn(currentPlayer.getPosition());
        } else {
            if (reachedWinningPosition(turn.nextPosition())) {
                messageLogger.log("Player " + currentPlayer.getName() + " wins! Game finished.");
                gameEventsListener.gameFinished();
            }
            if (yetToStart(currentPlayer.getPosition()) && !turn.hasRolledASix()) {
                messageLogger.log("Player " + currentPlayer.getName() + " did not score 6. First a 6 needs to be scored to start moving on board.");
                turn = Turn.skipTurn(currentPlayer.getPosition());
            } else {
                turn = evaluateTurn(turn);
            }
        }

        currentPlayer().updatePosition(turn);
    }
}
