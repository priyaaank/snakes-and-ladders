package com.snakesandladders.game;

import com.snakesandladders.game.elements.*;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.BoardGameController;
import com.snakesandladders.game.state.BoardGameEvents;

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
        RollBehavior dice = new RandomDice();
        PlayerGroup playerGroup = new PlayerGroup(
                new Player(1, "one", dice),
                new Player(2, "two", dice),
                new Player(3, "three", dice),
                new Player(4, "four", dice)
        );
        new SnakesAndLaddersGame(dice, msgLogger, new GameBoard(snakesBoardPositions, ladderBoardPositions, playerGroup, msgLogger), new BoardGameController()).beginGamePlay();
    }

    public void beginGamePlay() {
        Player currentPlayer, nextPlayer = null;

        //continue to play the game until it is over
        while (true) {
            currentPlayer = gameBoard.currentPlayer();
            int newHopCount = rollDice();
            logMessage("Player " + currentPlayer.getNumber() + " got dice roll of " + newHopCount);
            String currentPlayerNum = currentPlayer.getName();
            gameBoard.updatePlayerPosition(newHopCount, SnakesAndLaddersGame.this::endGame);

            gameBoard.moveToNextPlayer();
            nextPlayer = gameBoard.currentPlayer();
            String nextPlayerNumStr = nextPlayer.getName();
            logMessage("Next position for player " + currentPlayerNum + " is " + currentPlayer.getPosition());
            logMessage("Player " + nextPlayerNumStr + " will play next turn");
        }
    }

    private void endGame() {
        this.controller.finished();
    }

    private void logMessage(String message) {
        msgLogger.log(message);
    }

    //throw number at random
    private Integer rollDice() {
        return this.dice.roll();
    }

}
