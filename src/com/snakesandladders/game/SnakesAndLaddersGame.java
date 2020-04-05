package com.snakesandladders.game;

import com.snakesandladders.game.elements.*;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.BoardGameController;
import com.snakesandladders.game.state.BoardGameEvents;
import com.snakesandladders.game.state.Turn;

import java.util.HashMap;
import java.util.Map;

public class SnakesAndLaddersGame {

    private RollBehavior dice;
    private Logger msgLogger;
    private GameBoard gameBoard;
    private BoardGameEvents controller;

    public SnakesAndLaddersGame(RollBehavior dice, Logger msgLogger, GameBoard gameBoard, BoardGameEvents controller) {
        this.dice = dice;
        this.msgLogger = msgLogger;
        this.gameBoard = gameBoard;
        this.controller = controller;
    }

    public static void main(String[] args) {
        Map<Integer, Integer> snakesBoardPositions = new HashMap<Integer, Integer>() {
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

        Map<Integer, Integer> ladderBoardPositions = new HashMap<Integer, Integer>() {
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
        ConsoleLogger msgLogger = new ConsoleLogger();
        PlayerGroup playerGroup = new PlayerGroup(
                new Player(1, "one"),
                new Player(2, "two"),
                new Player(3, "three"),
                new Player(4, "four")
        );
        new SnakesAndLaddersGame(new RandomDice(), msgLogger, new GameBoard(snakesBoardPositions, ladderBoardPositions, playerGroup, msgLogger), new BoardGameController()).beginGamePlay();
    }

    public void beginGamePlay() {
        Player currentPlayer, nextPlayer = null;

        //continue to play the game until it is over
        while (true) {
            currentPlayer = gameBoard.currentPlayer();
            gameBoard.moveToNextPlayer();
            nextPlayer = gameBoard.currentPlayer();

            int newHopCount = rollDice();
            logMessage("Player " + currentPlayer.getNumber() + " got dice roll of " + newHopCount);

            String currentPlayerNum = currentPlayer.getName();
            int nextPlayerNum = nextPlayer.getNumber();
            String nextPlayerNumStr = nextPlayer.getName();
            currentPlayer.setPosition(takeTurn(currentPlayer.getPosition(), newHopCount, currentPlayerNum).nextPosition());
            passTurnToNextPlayer(currentPlayer.getPosition(), currentPlayerNum, nextPlayerNum, nextPlayerNumStr);
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

        return gameBoard.evaluateTurn(turn);
    }

    private void passTurnToNextPlayer(int playerCurrentPosition, String currentPlayerNum, int nextPlayerNum, String nextPlayerNumStr) {
        logMessage("Next position for player " + currentPlayerNum + " is " + playerCurrentPosition);
        logMessage("Player " + nextPlayerNumStr + " will play next turn");
    }

    private void endGame() {
        this.controller.finished();
    }

    private void logMessage(String message) {
        msgLogger.log(message);
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
