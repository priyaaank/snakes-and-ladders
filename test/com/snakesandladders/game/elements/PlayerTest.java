package com.snakesandladders.game.elements;

import com.snakesandladders.game.state.Turn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void shouldBeAbleToGivePlayerNumber() {
        Player playerOne = new Player(1, "one", new RandomDice());

        assertEquals(1, playerOne.getNumber());
    }

    @Test
    void shouldBeAbleToGivePlayerName() {
        Player playerOne = new Player(1, "one", new RandomDice());

        assertEquals("one", playerOne.getName());
    }

    @Test
    void shouldHaveZeroAsTheDefaultPosition() {
        Player playerOne = new Player(1, "one", new RandomDice());

        assertEquals(0, playerOne.getPosition());
    }

    @Test
    void shouldBeAbleToStorePosition() {
        //given
        Player playerOne = new Player(1, "one", new RandomDice());

        //when
        playerOne.setPosition(28);

        //then
        assertEquals(28, playerOne.getPosition());
    }

    @Test
    void shouldBeAbleToRollDice() {
        Player playerOne = new Player(1, "one", new ProgrammableDice(2));
        playerOne.setPosition(12);

        Turn newTurn = playerOne.takeTurn();

        assertEquals(14, newTurn.nextPosition());
    }
}