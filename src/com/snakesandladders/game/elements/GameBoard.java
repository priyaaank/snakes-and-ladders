package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.Turn;

import java.util.Map;

public class GameBoard {


    private final Map<Integer, Integer> snakesBoardPositions;
    private final Map<Integer, Integer> ladderBoardPositions;
    private final Logger messageLogger;

    public GameBoard(Map<Integer, Integer> snakesBoardPositions, Map<Integer, Integer> ladderBoardPositions, Logger messageLogger) {
        this.snakesBoardPositions = snakesBoardPositions;
        this.ladderBoardPositions = ladderBoardPositions;
        this.messageLogger = messageLogger;
    }

    public boolean bittenBySnake(int newPosition) {
        return snakesBoardPositions.get(newPosition) != null;
    }

    public boolean chancedUponALadder(int newPosition) {
        return ladderBoardPositions.get(newPosition) != null;
    }

    public Turn evaluateTurn(Turn currentTurn) {
        if(bittenBySnake(currentTurn.nextPosition())) {
            messageLogger.log("Player got bit by snake a position " + currentTurn.nextPosition());
            return Turn.advanceTo(snakesBoardPositions.get(currentTurn.nextPosition()));
        }

        if(chancedUponALadder(currentTurn.nextPosition())) {
            messageLogger.log("Player got chanced upon a ladder at position " + currentTurn.nextPosition() + "!");
            return Turn.advanceTo(ladderBoardPositions.get(currentTurn.nextPosition()));
        }

        return currentTurn;
    }

}
