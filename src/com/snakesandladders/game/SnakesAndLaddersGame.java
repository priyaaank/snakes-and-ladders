package com.snakesandladders.game;

import com.snakesandladders.game.elements.*;
import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.rules.RuleEvaluationListener;
import com.snakesandladders.game.rules.RuleEvaluator;

import java.util.HashMap;
import java.util.Map;

public class SnakesAndLaddersGame implements RuleEvaluationListener {

    private GameBoard gameBoard;
    private Logger msgLogger;

    public SnakesAndLaddersGame() {
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

        msgLogger = new ConsoleLogger();
        RollBehavior dice = new RandomDice();

        PlayerGroup playerGroup = new PlayerGroup(
                new Player(1, "one", dice, msgLogger),
                new Player(2, "two", dice, msgLogger),
                new Player(3, "three", dice, msgLogger),
                new Player(4, "four", dice, msgLogger)
        );

        gameBoard = new GameBoard(playerGroup, msgLogger, new RuleEvaluator(snakesBoardPositions, ladderBoardPositions, msgLogger));
    }

    public SnakesAndLaddersGame(GameBoard gameBoard, Logger msgLogger) {
        this.gameBoard = gameBoard;
        this.msgLogger = msgLogger;
    }

    public static void main(String[] args) {
        new SnakesAndLaddersGame().beginGamePlay();
    }

    public void beginGamePlay() {
        Player currentPlayer;
        do {
            currentPlayer = gameBoard.currentPlayer();
            gameBoard.takeTurn(this);
            gameBoard.moveToNextPlayer();
        } while (!currentPlayer.isWinner());
    }

    @Override
    public void skipTurnFor(Player player) {
        msgLogger.log("Player " + player.getName() + " needs to score exactly " + (100 - player.getPosition()) + " on dice roll to win. Passing chance.");
    }

    @Override
    public void yetToStart(Player player) {
        msgLogger.log("Player " + player.getName() + " did not score 6. First a 6 needs to be scored to start moving on board.");
    }

    @Override
    public void playerWon(Player player) {
        msgLogger.log("Player " + player.getName() + " wins! Game finished.");
    }

    @Override
    public void updatedTurnFor(Player player) {
        //do nothing
    }
}
