package com.snakesandladders.game.elements;

import com.snakesandladders.game.io.ConsoleLogger;
import com.snakesandladders.game.io.Logger;
import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Logger logger = new ConsoleLogger();

    @Test
    void shouldBeAbleToGivePlayerNumber() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals(1, playerOne.getNumber());
    }

    @Test
    void shouldBeAbleToGivePlayerName() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals("one", playerOne.getName());
    }

    @Test
    void shouldHaveZeroAsTheDefaultPosition() {
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        assertEquals(0, playerOne.getPosition());
    }

    @Test
    void shouldBeAbleToStorePosition() {
        //given
        Player playerOne = new Player(1, "one", new RandomDice(), logger);

        //when
        playerOne.setPosition(28);

        //then
        assertEquals(28, playerOne.getPosition());
    }

    @Test
    void shouldBeAbleToRollDice() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);
        playerOne.setPosition(12);

        Turn newTurn = playerOne.takeTurn();

        assertEquals(14, newTurn.nextPosition());
    }

    @Test
    void shouldUpdatePositionBasedUsingTurn() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2), logger);

        playerOne.updatePosition(Turn.advanceBy(3, 3));

        assertEquals(6, playerOne.getPosition());
    }
}