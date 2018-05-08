package com.main;

import com.main.app.Console;

import java.util.Scanner;

public class RestTest<T> {

    private Console console;

    private String[] lineArgs;
    private int lineArgsCount;

    public RestTest() {
        this.console = new Console();
        this.console.executeCommand("login", "login", "admin@email.com", "123456"); // log in on init
        this.readCommandLoop();
    }

    public void readCommandLoop() {
        String line;
        Scanner scanner = new Scanner(System.in);
        while ((line = scanner.nextLine()) != null) {
            if (!this.checkAndReadArgs(line))
                continue;
            this.console.executeCommand(this.lineArgs[0].toLowerCase(), this.lineArgs);
        }
    }

    private boolean checkAndReadArgs(String line) {
        this.lineArgs = line.split(" ");
        this.lineArgsCount = this.lineArgs.length;
        if (this.lineArgsCount > 0)
            return true;
        System.err.println("Incorrect argument!");
        return false;
    }

    public static void main(String[] args) {
        new RestTest();
    }
}
