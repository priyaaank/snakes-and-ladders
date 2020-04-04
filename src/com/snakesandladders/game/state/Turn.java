package com.snakesandladders.game.state;

public class Turn {

    private static final int NO_HOPS = 0;
    private int currentPosition;
    private Integer hopCount;

    private Turn(final Integer currentPosition, final Boolean stayPut, final Integer advanceBy) {
        this.hopCount = 0;
        this.currentPosition = currentPosition;
        if (!stayPut) hopCount = advanceBy;
    }

    public Integer nextPosition() {
        return currentPosition + hopCount;
    }

    public static Turn skipTurn(final Integer currentPosition) {
        return new Turn(currentPosition, Boolean.TRUE, NO_HOPS);
    }

    public static Turn advanceBy(final Integer currentPosition, final Integer hopCount) {
        return new Turn(currentPosition, Boolean.FALSE, hopCount);
    }

    public static Turn advanceTo(final Integer newPosition) {
        return new Turn(newPosition, Boolean.FALSE, NO_HOPS);
    }

}
