package com.snakesandladders.game.elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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


}