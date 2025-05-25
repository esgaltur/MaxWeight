package cz.esgaltur.maxweight.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for console output with color.
 * Uses ANSI escape codes for color support on compatible terminals.
 */
public class ConsoleUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUtils.class);

    // ANSI color codes
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Print a message with the specified color
     * 
     * @param message The message to print
     * @param color The ANSI color code
     */
    public static void printColored(String message, String color) {
        System.out.println(color + message + ANSI_RESET);
        logger.debug(message);
    }

    /**
     * Print a message with the specified color without a newline
     * 
     * @param message The message to print
     * @param color The ANSI color code
     */
    public static void printColoredNoLn(String message, String color) {
        System.out.print(color + message + ANSI_RESET);
        logger.debug(message);
    }

    /**
     * Print a header with a border
     * 
     * @param header The header text
     */
    public static void printHeader(String header) {
        StringBuilder border = new StringBuilder();
        for (int i = 0; i < header.length() + 4; i++) {
            border.append("*");
        }
        printColored(border.toString(), ANSI_CYAN);
        printColored("* " + header + " *", ANSI_CYAN);
        printColored(border.toString(), ANSI_CYAN);
        logger.info("Header: {}", header);
    }

    /**
     * Print a success message
     * 
     * @param message The message to print
     */
    public static void printSuccess(String message) {
        printColored(message, ANSI_GREEN);
        logger.info("Success: {}", message);
    }

    /**
     * Print an error message
     * 
     * @param message The message to print
     */
    public static void printError(String message) {
        printColored(message, ANSI_RED);
        logger.error(message);
    }

    /**
     * Print a warning message
     * 
     * @param message The message to print
     */
    public static void printWarning(String message) {
        printColored(message, ANSI_YELLOW);
        logger.warn(message);
    }

    /**
     * Print an info message
     * 
     * @param message The message to print
     */
    public static void printInfo(String message) {
        printColored(message, ANSI_BLUE);
        logger.info(message);
    }

    /**
     * Print a prompt message and return the cursor to the same line
     * 
     * @param message The message to print
     */
    public static void printPrompt(String message) {
        printColoredNoLn(message + " ", ANSI_WHITE);
        logger.debug("Prompt: {}", message);
    }

    /**
     * Clear the console
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        logger.debug("Console cleared");
    }
}
