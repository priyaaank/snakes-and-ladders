package com.snakesandladders.game;

import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.props.RandomDice;
import com.snakesandladders.game.props.RollBehavior;
import com.snakesandladders.game.state.BoardGameController;
import com.snakesandladders.game.state.BoardGameEvents;

import java.util.HashMap;
import java.util.Map;

public class SnakesAndLaddersGame {

    private Map<Integer, Integer> snakesBoardPositions;
    private Map<Integer, Integer> ladderBoardPositions;
    private int activePlayer = 1;
    private boolean skipPositionUpdate = false;
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
                playerOnePosition = takeTurn(playerOnePosition, newHopCount, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            } else if (activePlayer == 2) {
                String currentPlayerNum = "two";
                int nextPlayerNum = 3;
                String nextPlayerNumStr = "three";
                playerTwoPosition = takeTurn(playerTwoPosition, newHopCount, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            } else if (activePlayer == 3) {
                String currentPlayerNum = "three";
                int nextPlayerNum = 4;
                String nextPlayerNumStr = "four";
                playerThreePosition = takeTurn(playerThreePosition, newHopCount, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            } else if (activePlayer == 4) {
                String currentPlayerNum = "four";
                int nextPlayerNum = 1;
                String nextPlayerNumStr = "one";
                playerFourPosition = takeTurn(playerFourPosition, newHopCount, currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
            }
        }
    }

    private int takeTurn(int playerOnePosition, int newHopCount, String currentPlayerNum, int nextPlayerNum, String nextPlayerNumStr) {
        int newPosition = playerOnePosition + newHopCount;

        if (hopsNotPossible(newPosition)) {
            logMessage("Player " + currentPlayerNum + " needs to score exactly " + (100 - playerOnePosition) + " on dice roll to win. Passing chance.");
            skipPositionUpdate = true;
        }

        if (reachedWinningPosition(newPosition)) {
            logMessage("Player " + currentPlayerNum + " wins! Game finished.");
            endGame();
        }

        if (yetToStart(playerOnePosition) && hasntRolledASix(newHopCount)) {
            logMessage("Player " + currentPlayerNum + " did not score 6. First a 6 needs to be scored to start moving on board.");
            skipPositionUpdate = true;
        }

        if (bittenBySnake(snakesBoardPositions, newPosition)) {
            logMessage("Player got bit by snake a position " + newPosition);
            playerOnePosition = snakesBoardPositions.get(newPosition);
            skipPositionUpdate = true;
        }

        if (chancedUponALadder(ladderBoardPositions, newPosition)) {
            logMessage("Player got chanced upon a ladder at position " + newPosition + "!");
            playerOnePosition = ladderBoardPositions.get(newPosition);
            skipPositionUpdate = true;
        }

        if (!skipPositionUpdate) {
            playerOnePosition = newPosition;
        }

        logMessage("Next position for player " + currentPlayerNum + " is " + playerOnePosition);
        skipPositionUpdate = false;
        this.activePlayer = nextPlayerNum;
        logMessage("Player " + nextPlayerNumStr + " will play next turn");
        return playerOnePosition;
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
