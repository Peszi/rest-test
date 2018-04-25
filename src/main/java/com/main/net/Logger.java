package com.main.net;

public class Logger {

    public static void info(String message) {
//        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }

    public static void err(String message) {
        System.err.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
