package com.snakesandladders.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SnakesAndLaddersGame {

  public static void main(String[] args) {
    new SnakesAndLaddersGame().beginGamePlay();
  }

  public void beginGamePlay() {
    //declare variables
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
    int activePlayer = 1;
    int playerOnePosition = 0, playerTwoPosition = 0, playerThreePosition = 0, playerFourPosition = 0;
    boolean skipPositionUpdate = false;


    //continue to play the game until it is over
    while (true) {

      int newHopCount = rollDice();
      logMessage("Player " + activePlayer + " got dice roll of " + newHopCount);

      if (activePlayer == 1) {
        int newPosition = playerOnePosition + newHopCount;

        if (hopsNotPossible(newPosition)) {
          logMessage("Player one needs to score exactly " + (100 - playerOnePosition) + " on dice roll to win. Passing chance.");
          skipPositionUpdate = true;
        }

        if (reachedWinningPosition(newPosition)) {
          logMessage("Player one wins! Game finished.");
          System.exit(1);
        }

        if (yetToStart(playerOnePosition) && hasntRolledASix(newHopCount)) {
          logMessage("Player one did not score 6. First a 6 needs to be scored to start moving on board.");
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

        logMessage("Next position for player one is " + playerOnePosition);
        skipPositionUpdate = false;
        activePlayer = 2;
        logMessage("Player two will play next turn");

      } else if (activePlayer == 2) {

        int newPosition = playerTwoPosition + newHopCount;

        if (hopsNotPossible(newPosition)) {
          logMessage("Player two needs to score exactly " + (100 - playerTwoPosition) + " on dice roll to win. Passing chance.");
          skipPositionUpdate = true;
        }

        if (reachedWinningPosition(newPosition)) {
          logMessage("Player two wins! Game finished.");
          System.exit(1);
        }

        if (yetToStart(playerTwoPosition) && hasntRolledASix(newHopCount)) {
          logMessage("Player two did not score 6. First a 6 needs to be scored to start moving on board.");
          skipPositionUpdate = true;
        }

        if (bittenBySnake(snakesBoardPositions, newPosition)) {
          logMessage("Player got bit by snake a position " + newPosition);
          playerTwoPosition = snakesBoardPositions.get(newPosition);
          skipPositionUpdate = true;
        }

        if (chancedUponALadder(ladderBoardPositions, newPosition)) {
          logMessage("Player got chanced upon a ladder at position " + newPosition + "!");
          playerTwoPosition = ladderBoardPositions.get(newPosition);
          skipPositionUpdate = true;
        }

        if (!skipPositionUpdate) {
          playerTwoPosition = newPosition;
        }
        logMessage("Next position for player two is " + playerTwoPosition);
        skipPositionUpdate = false;
        activePlayer = 3;
        logMessage("Player three will play next turn");

      } else if (activePlayer == 3) {

        int newPosition = playerThreePosition + newHopCount;

        if (hopsNotPossible(newPosition)) {
          logMessage("Player three needs to score exactly " + (100 - playerThreePosition) + " on dice roll to win. Passing chance.");
          skipPositionUpdate = true;
        }

        if (reachedWinningPosition(newPosition)) {
          logMessage("Player three wins! Game finished.");
          System.exit(1);
        }

        if (yetToStart(playerThreePosition) && hasntRolledASix(newHopCount)) {
          logMessage("Player three did not score 6. First a 6 needs to be scored to start moving on board.");
          skipPositionUpdate = true;
        }

        if (bittenBySnake(snakesBoardPositions, newPosition)) {
          logMessage("Player got bit by snake a position " + newPosition);
          playerThreePosition = snakesBoardPositions.get(newPosition);
          skipPositionUpdate = true;
        }

        if (chancedUponALadder(ladderBoardPositions, newPosition)) {
          logMessage("Player got chanced upon a ladder at position " + newPosition + "!");
          playerThreePosition = ladderBoardPositions.get(newPosition);
          skipPositionUpdate = true;
        }

        if (!skipPositionUpdate) {
          playerThreePosition = newPosition;
        }
        logMessage("Next position for player three is " + playerThreePosition);
        skipPositionUpdate = false;
        activePlayer = 4;
        logMessage("Player four will play next turn");

      } else if (activePlayer == 4) {

        int newPosition = playerFourPosition + newHopCount;

        if (hopsNotPossible(newPosition)) {
          logMessage("Player four needs to score exactly " + (100 - playerFourPosition) + " on dice roll to win. Passing chance.");
          skipPositionUpdate = true;
        }

        if (reachedWinningPosition(newPosition)) {
          logMessage("Player four wins! Game finished.");
          System.exit(1);
        }

        if (yetToStart(playerFourPosition) && hasntRolledASix(newHopCount)) {
          logMessage("Player four did not score 6. First a 6 needs to be scored to start moving on board.");
          skipPositionUpdate = true;
        }

        if (bittenBySnake(snakesBoardPositions, newPosition)) {
          logMessage("Player got bit by snake a position " + newPosition);
          playerFourPosition = snakesBoardPositions.get(newPosition);
          skipPositionUpdate = true;
        }

        if (chancedUponALadder(ladderBoardPositions, newPosition)) {
          logMessage("Player got chanced upon a ladder at position " + newPosition + "!");
          playerFourPosition = ladderBoardPositions.get(newPosition);
          skipPositionUpdate = true;
        }

        if (!skipPositionUpdate) {
          playerFourPosition = newPosition;
        }
        logMessage("Next position for player four is " + playerFourPosition);
        skipPositionUpdate = false;
        activePlayer = 1;
        logMessage("Player one will play next turn");
      }

    }

  }

  private void logMessage(String message) {
    System.out.println(message);
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

  private static boolean hopsNotPossible(int newPosition) {
    return newPosition > 100;
  }

  //throw number at random
  private static Integer rollDice() {
    Random rand = new Random();
    return rand.nextInt(6) + 1;
  }

}
