package cz.esgaltur.maxweight.controller;

import cz.esgaltur.maxweight.exception.ValidationException;
import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import cz.esgaltur.maxweight.service.ProgramDataService;
import cz.esgaltur.maxweight.service.ProgramFactory;
import cz.esgaltur.maxweight.util.ConsoleUtils;
import cz.esgaltur.maxweight.view.ProgramView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Controller class for the MaxWeight application.
 * Handles user input and coordinates the model and view.
 */
public class ProgramController {
    private static final Logger logger = LoggerFactory.getLogger(ProgramController.class);

    private final ProgramDataService dataService;
    private final ProgramFactory programFactory;
    private final ProgramView programView;
    private final BufferedReader reader;

    /**
     * Constructor
     */
    public ProgramController() {
        this.dataService = new ProgramDataService();
        this.programFactory = new ProgramFactory(dataService);
        this.programView = new ProgramView();
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        logger.info("ProgramController initialized");
    }

    /**
     * Start the interactive menu
     * 
     * @throws IOException If there's an error reading from the console
     */
    public void start() throws IOException {
        logger.info("Starting MaxWeight application");
        ConsoleUtils.clearConsole();

        while (true) {
            try {
                displayMenu();

                int from = readWeekNumber("From: ");
                int to = readWeekNumber("To:");

                if (from == 0 && to == 0) {
                    ConsoleUtils.printSuccess("*****Thanks for using my program****");
                    logger.info("User exited the application");
                    break;
                }

                validateWeekRange(from, to);

                int maxWeight = readMaxWeight();
                logger.info("Generating program for weeks {}-{} with max weight {}", from, to, maxWeight);
                generateAndDisplayPrograms(from, to, maxWeight);

                // Add a separator between program runs
                ConsoleUtils.printColored("\n" + "-".repeat(50) + "\n", ConsoleUtils.ANSI_BLUE);

            } catch (ValidationException e) {
                ConsoleUtils.printError(e.getMessage());
                logger.warn("Validation error: {}", e.getMessage());
            } catch (NumberFormatException e) {
                // This should not happen anymore since we're handling it in readWeekNumber and readMaxWeight
                ConsoleUtils.printError("You have entered a word or letter or empty string");
                logger.error("Unexpected NumberFormatException", e);
            }
        }
    }

    /**
     * Display the menu
     */
    private void displayMenu() {
        ConsoleUtils.printHeader("Program of calculate weight of bench press");
        ConsoleUtils.printInfo("Please choose week from 1 to 6 (or 0 to exit):");
        logger.debug("Menu displayed");
    }

    /**
     * Read a week number from the console
     * 
     * @param prompt The prompt to display
     * @return The week number
     * @throws IOException If there's an error reading from the console
     * @throws ValidationException If the input is not a valid number
     */
    private int readWeekNumber(String prompt) throws IOException, ValidationException {
        ConsoleUtils.printPrompt(prompt);
        String input = reader.readLine();
        try {
            int weekNumber = Integer.parseInt(input);
            logger.debug("Read week number: {}", weekNumber);
            return weekNumber;
        } catch (NumberFormatException e) {
            logger.error("Invalid week number format: {}", input, e);
            throw ValidationException.invalidNumberFormat(input, "week number");
        }
    }

    /**
     * Read the maximum weight from the console
     * 
     * @return The maximum weight
     * @throws IOException If there's an error reading from the console
     * @throws ValidationException If the input is not a valid number or is not greater than 0
     */
    private int readMaxWeight() throws IOException, ValidationException {
        ConsoleUtils.printPrompt("Enter please your max weight for bench press: ");
        String input = reader.readLine();
        try {
            int maxWeight = Integer.parseInt(input);
            if (maxWeight <= 0) {
                logger.error("Invalid max weight: {}", maxWeight);
                throw ValidationException.invalidMaxWeight(maxWeight);
            }
            logger.debug("Read max weight: {}", maxWeight);
            return maxWeight;
        } catch (NumberFormatException e) {
            logger.error("Invalid max weight format: {}", input, e);
            throw ValidationException.invalidNumberFormat(input, "max weight");
        }
    }

    /**
     * Validate the week range
     * 
     * @param from The starting week number
     * @param to The ending week number
     * @throws ValidationException If the week range is invalid
     */
    private void validateWeekRange(int from, int to) throws ValidationException {
        boolean isValid = from >= 1 && from <= 6 && to >= 1 && to <= 6 && from <= to;
        logger.debug("Week range validation: from={}, to={}, isValid={}", from, to, isValid);
        if (!isValid) {
            logger.error("Invalid week range: from={}, to={}", from, to);
            throw ValidationException.invalidWeekRange(from, to);
        }
    }

    /**
     * Generate and display programs for a range of weeks
     * 
     * @param from The starting week number
     * @param to The ending week number
     * @param maxWeight The maximum weight for bench press
     */
    private void generateAndDisplayPrograms(int from, int to, int maxWeight) {
        logger.info("Generating programs for weeks {}-{} with max weight {}", from, to, maxWeight);
        for (int weekNumber = from; weekNumber <= to; weekNumber++) {
            Week week = Week.fromWeekNumber(weekNumber);
            TrainingProgram program = programFactory.createProgram(week, maxWeight);
            programView.displayProgram(program);
        }
    }
}
