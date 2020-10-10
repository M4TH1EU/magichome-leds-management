package ch.mathieubroillet.leds.utils;


public class Logger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String RED_BOLD = "\033[1;31m";
    public static final String GREEN_BOLD = "\033[1;32m";


    public static void error(String message) {
        print(ANSI_RED + "[ERROR] " + message + ANSI_RESET);
    }

    public static void info(String message) {
        print(ANSI_GREEN + "[INFO] " + message + ANSI_RESET);
    }

    public static void debug(String message) {
        print(GREEN_BOLD + "[DEBUG] " + message + ANSI_RESET);
    }

    public static void warn(String message) {
        print(ANSI_YELLOW + "[WARN] " + message + ANSI_RESET);
    }

    public static void fatal(String message) {
        print(RED_BOLD + "[FATAL] " + message + ANSI_RESET);
    }


    private static void print(String string) {
        System.out.println(string);
    }

}