package com.example.splitWiser.commands;

public interface Command {
    boolean matches(String input);
    void execute(String input);
}
