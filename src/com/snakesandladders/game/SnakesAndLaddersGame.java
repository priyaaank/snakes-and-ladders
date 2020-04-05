package com.snakesandladders.game;

import com.snakesandladders.game.elements.*;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.state.BoardGameController;
import com.snakesandladders.game.state.BoardGameEvents;

import java.util.HashMap;
import java.util.Map;

public class SnakesAndLaddersGame {

    private GameBoard gameBoard;
    private BoardGameEvents controller;

    public SnakesAndLaddersGame(GameBoard gameBoard, BoardGameEvents controller) {
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
                new Player(1, "one", dice, msgLogger),
                new Player(2, "two", dice, msgLogger),
                new Player(3, "three", dice, msgLogger),
                new Player(4, "four", dice, msgLogger)
        );

        new SnakesAndLaddersGame(
                new GameBoard(snakesBoardPositions, ladderBoardPositions, playerGroup, msgLogger),
                new BoardGameController()
        ).beginGamePlay();
    }

    public void beginGamePlay() {
        //continue to play the game until it is over
        while (true) {
            gameBoard.takeTurn(SnakesAndLaddersGame.this::endGame);
            gameBoard.moveToNextPlayer();
        }
    }

    private void endGame() {
        this.controller.finished();
    }

}
