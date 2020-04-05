package com.snakesandladders.game.elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    @Test
    void shouldBeAbleToGivePlayerNumber() {
        Player playerOne = new Player(1, "one");

        assertEquals(1, playerOne.getNumber());
    }

    @Test
    void shouldBeAbleToGivePlayerName() {
        Player playerOne = new Player(1, "one");

        assertEquals("one", playerOne.getName());
    }

    @Test
    void shouldHaveZeroAsTheDefaultPosition() {
        Player playerOne = new Player(1, "one");

        assertEquals(0, playerOne.getPosition());
    }

    @Test
    void shouldBeAbleToStorePosition() {
        //given
        Player playerOne = new Player(1, "one");

        //when
        playerOne.setPosition(28);

        //then
        assertEquals(28, playerOne.getPosition());
    }
}