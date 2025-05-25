package cz.esgaltur.maxweight;

import cz.esgaltur.maxweight.controller.ProgramController;
import cz.esgaltur.maxweight.util.ConsoleUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Main entry point for the MaxWeight application.
 * This program generates weight training programs based on 
 * Professor Yuri Verkhoshansky's methodology.
 * 
 * @author Sosnovich Dmitriy
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    /**
     * Application entry point
     * 
     * @param args Command line arguments (not used)
     * @throws IOException If there's an error reading from the console
     */
    public static void main(String[] args) throws IOException {
        try {
            logger.info("Starting MaxWeight application");

            // Display welcome message
            ConsoleUtils.clearConsole();
            ConsoleUtils.printHeader("MaxWeight - Bench Press Training Program Generator");
            ConsoleUtils.printInfo("Based on Professor Yuri Verkhoshansky's methodology");
            ConsoleUtils.printColored("", ConsoleUtils.ANSI_RESET); // Empty line

            // Start the controller
            ProgramController controller = new ProgramController();
            controller.start();

            logger.info("MaxWeight application completed successfully");
        } catch (Exception e) {
            logger.error("Unexpected error in MaxWeight application", e);
            ConsoleUtils.printError("An unexpected error occurred: " + e.getMessage());
            ConsoleUtils.printError("Please check the logs for more details.");
        } finally {
            System.exit(0);
        }
    }
}
