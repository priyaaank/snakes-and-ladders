package com.snakesandladders.game;

import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.props.RandomDice;
import com.snakesandladders.game.props.RollBehavior;
import com.snakesandladders.game.state.BoardGameController;
import com.snakesandladders.game.state.BoardGameEvents;
import com.snakesandladders.game.state.Turn;

import java.util.HashMap;
import java.util.Map;

public class SnakesAndLaddersGame {

    private Map<Integer, Integer> snakesBoardPositions;
    private Map<Integer, Integer> ladderBoardPositions;
    private int activePlayer = 1;
    private RollBehavior dice;
    private Logger msgLogger;
    private BoardGameEvents controller;

    public SnakesAndLaddersGame(RollBehavior dice, Logger msgLogger, BoardGameEvents controller) {
        this.dice = dice;
        this.msgLogger = msgLogger;
        this.controller = controller;
        snakesBoardPositions = new HashMap<Integer, Integer>() {
            {
                put(18, 2);
                put(25, 8);
                put(38, 11);
                put(41, 19);
                put(59, 21);
                put(72, 12);
                put(78, 7);
                put(86, 31);
                put(92, 26);
                put(97, 5);
            }
        };

        ladderBoardPositions = new HashMap<Integer, Integer>() {
            {
                put(9, 32);
                put(12, 53);
                put(17, 90);
                put(21, 50);
                put(27, 66);
                put(29, 42);
                put(44, 73);
                put(63, 88);
            }
        };
    }

    public static void main(String[] args) {
        new SnakesAndLaddersGame(new RandomDice(), new ConsoleLogger(), new BoardGameController()).beginGamePlay();
    }

    public void beginGamePlay() {
        int playerOnePosition = 0, playerTwoPosition = 0, playerThreePosition = 0, playerFourPosition = 0;

        //continue to play the game until it is over
        while (true) {

            int newHopCount = rollDice();
            logMessage("Player " + activePlayer + " got dice roll of " + newHopCount);

            if (activePlayer == 1) {
                String currentPlayerNum = "one";
                int nextPlayerNum = 2;
                String nextPlayerNumStr = "two";
                playerOnePosition = takeTurn(playerOnePosition, newHopCount, currentPlayerNum).nextPosition();
                passTurnToNextPlayer(playerOnePosition, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            } else if (activePlayer == 2) {
                String currentPlayerNum = "two";
                int nextPlayerNum = 3;
                String nextPlayerNumStr = "three";
                playerTwoPosition = takeTurn(playerTwoPosition, newHopCount, currentPlayerNum).nextPosition();
                passTurnToNextPlayer(playerTwoPosition, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            } else if (activePlayer == 3) {
                String currentPlayerNum = "three";
                int nextPlayerNum = 4;
                String nextPlayerNumStr = "four";
                playerThreePosition = takeTurn(playerThreePosition, newHopCount, currentPlayerNum).nextPosition();
                passTurnToNextPlayer(playerThreePosition, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            } else if (activePlayer == 4) {
                String currentPlayerNum = "four";
                int nextPlayerNum = 1;
                String nextPlayerNumStr = "one";
                playerFourPosition = takeTurn(playerFourPosition, newHopCount, currentPlayerNum).nextPosition();
                passTurnToNextPlayer(playerFourPosition, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            }

        }
    }

    private Turn takeTurn(int playerCurrentPosition, int newHopCount, String currentPlayerNum) {
        Turn turn = Turn.advanceBy(playerCurrentPosition, newHopCount);

        if (hopsNotPossible(turn.nextPosition())) {
            logMessage("Player " + currentPlayerNum + " needs to score exactly " + (100 - playerCurrentPosition) + " on dice roll to win. Passing chance.");
            return Turn.skipTurn(playerCurrentPosition);
        }

        if (reachedWinningPosition(turn.nextPosition())) {
            logMessage("Player " + currentPlayerNum + " wins! Game finished.");
            endGame();
        }

        if (yetToStart(playerCurrentPosition) && hasntRolledASix(newHopCount)) {
            logMessage("Player " + currentPlayerNum + " did not score 6. First a 6 needs to be scored to start moving on board.");
            return Turn.skipTurn(playerCurrentPosition);
        }

        if (bittenBySnake(snakesBoardPositions, turn.nextPosition())) {
            logMessage("Player got bit by snake a position " + turn.nextPosition());
            return Turn.advanceTo(snakesBoardPositions.get(turn.nextPosition()));
        }

        if (chancedUponALadder(ladderBoardPositions, turn.nextPosition())) {
            logMessage("Player got chanced upon a ladder at position " + turn.nextPosition() + "!");
            return Turn.advanceTo(ladderBoardPositions.get(turn.nextPosition()));
        }

        return turn;
    }

    private void passTurnToNextPlayer(int playerCurrentPosition, String currentPlayerNum, int nextPlayerNum, String nextPlayerNumStr) {
        logMessage("Next position for player " + currentPlayerNum + " is " + playerCurrentPosition);
        this.activePlayer = nextPlayerNum;
        logMessage("Player " + nextPlayerNumStr + " will play next turn");
    }

    private void endGame() {
        this.controller.finished();
    }

    private void logMessage(String message) {
        msgLogger.log(message);
    }

    private boolean chancedUponALadder(Map<Integer, Integer> ladderBoardPositions, int newPosition) {
        return ladderBoardPositions.get(newPosition) != null;
    }

    private boolean bittenBySnake(Map<Integer, Integer> snakesBoardPositions, int newPosition) {
        return snakesBoardPositions.get(newPosition) != null;
    }

    private boolean hasntRolledASix(int newHopCount) {
        return newHopCount != 6;
    }

    private boolean yetToStart(int playerOnePosition) {
        return playerOnePosition == 0;
    }

    private boolean reachedWinningPosition(int newPosition) {
        return newPosition == 100;
    }

    private boolean hopsNotPossible(int newPosition) {
        return newPosition > 100;
    }

    //throw number at random
    private Integer rollDice() {
        return this.dice.roll();
    }

}
