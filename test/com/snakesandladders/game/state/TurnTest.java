package com.snakesandladders.game.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void shouldCalculateNextPositionBasedOnIndicatedAdvances() {
        Turn currentTurn = Turn.advanceBy(3,3);

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
}