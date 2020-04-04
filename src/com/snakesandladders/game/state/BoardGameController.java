package com.snakesandladders.game.state;

public class BoardGameController implements BoardGameEvents {

    @Override
    public void finished() {
        System.exit(1);
    }

}
