package com.snakesandladders.game.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void shouldCalculateNextPositionBasedOnIndicatedAdvances() {
        Turn currentTurn = Turn.advanceBy(3, 3);

        assertEquals(6, currentTurn.nextPosition());
    }

    @Test
    void shouldReturnCurrentPositionAsNewPositionIfNeedToStayPut() {
        Turn currentTurn = Turn.skipTurn(3);

        assertEquals(3, currentTurn.nextPosition());
    }

    @Test
    void shouldReturnNewPositionWhenDirectlyAdvancingToASpecificPosition() {
        Turn currentTurn = Turn.advanceTo(84);

        assertEquals(84, currentTurn.nextPosition());
    }

    @Test
    void shouldConfirmIfASixHasBeenRolled() {
        Turn currentTurn = Turn.advanceBy(2, 6);

        assertTrue(currentTurn.hasRolledASix());
    }

    @Test
    void shouldNotIndicateASixIfDirectlyAdvancingBySixSpaces() {
        Turn currentTurn = Turn.advanceTo(6);

        assertFalse(currentTurn.hasRolledASix());
    }

    @Test
    void shouldConfirmIfHundredHasBeenReachedByHops() {
        Turn currentTurn = Turn.advanceBy(98, 2);

        assertTrue(currentTurn.hasReachedHundred());
    }

    @Test
    void shouldConfirmIfHundredHasBeenReachedByDirectlyAdvancingToIt() {
        Turn currentTurn = Turn.advanceTo(100);

        assertTrue(currentTurn.hasReachedHundred());
    }

    @Test
    void shouldConfirmHundredHasNotBeenReachedWhenPositionIsLesser() {
        Turn currentTurn = Turn.advanceBy(98, 1);

        assertFalse(currentTurn.hasReachedHundred());
    }

    @Test
    void shouldConfirmHundredHasNotBeenReachedWhenPositionIsGreater() {
        Turn currentTurn = Turn.advanceTo(101);

        assertFalse(currentTurn.hasReachedHundred());
    }

    @Test
    void shouldBeTrueIfNextPositionIsBeyondHundred() {
        Turn turn = Turn.advanceBy(99, 2);

        assertTrue(turn.goingBeyondWinninPosition());
    }

    @Test
    void shouldBeFalseIfNextPositionIsWithinHundred() {
        Turn turn = Turn.advanceBy(99, 1);

        assertFalse(turn.goingBeyondWinninPosition());
    }
}