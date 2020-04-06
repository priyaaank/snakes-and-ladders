package com.snakesandladders.game.io;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLogger implements Logger {

    private List<String> messageHistory;

    public InMemoryLogger() {
        this.messageHistory = new ArrayList<>();
    }

    @Override
    public void log(String message) {
        System.out.println("Storing message " + message);
        this.messageHistory.add(message);
    }

    public String getMessageAtPosition(Integer index) {
        return messageHistory.get(index);
    }
}
