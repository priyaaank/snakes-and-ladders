package com.snakesandladders.game.elements;

import com.snakesandladders.game.errors.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class PlayerGroupTest {

    private PlayerGroup playerGroup;
    private Player playerTwo;
    private Player playerOne;

    @BeforeEach
    void setUp() {
        playerOne = new Player(1, "one");
        playerTwo = new Player(2, "two");
        playerGroup = new PlayerGroup(playerOne, playerTwo);
    }

    @Test
    void shouldAllowMultiplePlayersInAGroup() {
        assertEquals(2, playerGroup.size());
    }

    @Test
    void shouldMoveToNextPlayer() {
        //when
        assertEquals(playerOne, playerGroup.currentPlayer());

        //then
        assertEquals(playerTwo, playerGroup.moveToNextPlayer());
    }

    @Test
    void shouldSequencePlayerTurnsInRoundRobin() {
        //given
        assertEquals(playerOne, playerGroup.currentPlayer());
        assertEquals(playerTwo, playerGroup.moveToNextPlayer());

        //when
        playerGroup.moveToNextPlayer();

        //then
        assertEquals(playerOne, playerGroup.currentPlayer());
    }

    @Test
    void shouldRaiseErrorIfGroupHasOnlyOnePlayer() {
        try {
            playerGroup = new PlayerGroup(playerOne);
            fail("Expected exception to be raised");
        } catch (GameException ge) {
            assertEquals("Expected at least two unique players in the group!", ge.getMessage());
        }
    }

    @Test
    void shouldRaiseErrorIfSamePlayerRegistersAsMultiplePlayer() {
        try {
            playerGroup = new PlayerGroup(playerOne, playerTwo, playerOne);
            fail("Expected exception to be raised");
        } catch (GameException ge) {
            assertEquals("Same player cannot register multiple times!", ge.getMessage());
        }
    }
}