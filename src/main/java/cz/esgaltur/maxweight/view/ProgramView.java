package cz.esgaltur.maxweight.view;

import cz.esgaltur.maxweight.model.Day;
import cz.esgaltur.maxweight.model.Exercise;
import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import cz.esgaltur.maxweight.util.ConsoleUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * View class for displaying training programs.
 * Responsible for formatting and printing programs to the console.
 */
public class ProgramView {
    private static final Logger logger = LoggerFactory.getLogger(ProgramView.class);

    /**
     * Display a training program
     * 
     * @param program The program to display
     */
    public void displayProgram(TrainingProgram program) {
        logger.info("Displaying program for week {}", program.getWeek());
        displayProgramHeader(program.getWeek());

        if (program.isSpecialWeek()) {
            displayStandardProgram(program);
        } else {
            displayProgramWithExtraExercises(program);
        }
    }

    /**
     * Display the header for a program
     * 
     * @param week The week
     */
    private void displayProgramHeader(Week week) {
        String weekKey = week.toLegacyKey();
        ConsoleUtils.printHeader(weekKey);
        ConsoleUtils.printColored("Day 1                  Day 2", ConsoleUtils.ANSI_CYAN);
        logger.debug("Displayed header for {}", weekKey);
    }

    /**
     * Display a standard program (for weeks 5 and 6)
     * 
     * @param program The program to display
     */
    private void displayStandardProgram(TrainingProgram program) {
        logger.debug("Displaying standard program for week {}", program.getWeek());
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);

        int minExercises = Math.min(day1Exercises.size(), day2Exercises.size());

        boolean isDay1 = true;
        for (int i = 0; i < minExercises; i++) {
            if (isDay1) {
                displayExercise(day1Exercises.get(i), true);
            } else {
                displayExercise(day2Exercises.get(i), false);
            }
            isDay1 = !isDay1;
        }
    }

    /**
     * Display a program with extra exercises (for weeks 1-4)
     * 
     * @param program The program to display
     */
    private void displayProgramWithExtraExercises(TrainingProgram program) {
        logger.debug("Displaying program with extra exercises for week {}", program.getWeek());
        List<Exercise> day1Exercises = program.getExercisesForDay(Day.DAY1);
        List<Exercise> day2Exercises = program.getExercisesForDay(Day.DAY2);

        int minExercises = Math.min(day1Exercises.size(), day2Exercises.size());

        // Display alternating exercises
        boolean isDay1 = true;
        for (int i = 0; i < minExercises; i++) {
            if (isDay1) {
                displayExercise(day1Exercises.get(i), true);
            } else {
                displayExercise(day2Exercises.get(i), false);
            }
            isDay1 = !isDay1;
        }

        // Display extra exercises for day 2
        for (int i = minExercises; i < day2Exercises.size(); i++) {
            displayExercise(day2Exercises.get(i), false);
        }
    }

    /**
     * Display a single exercise
     * 
     * @param exercise The exercise to display
     * @param isDay1 Whether this is a day 1 exercise
     */
    private void displayExercise(Exercise exercise, boolean isDay1) {
        StringBuilder sb = new StringBuilder();

        if (!isDay1) {
            sb.append("                 * ");
        }

        sb.append(exercise.getWeightPercentage()).append("% - ");
        sb.append(exercise.getActualWeight()).append(" x ");
        sb.append(exercise.getRepetitions());

        if (isDay1) {
            ConsoleUtils.printColoredNoLn(sb.toString(), ConsoleUtils.ANSI_GREEN);
            System.out.print("\t");
        } else {
            ConsoleUtils.printColored(sb.toString(), ConsoleUtils.ANSI_YELLOW);
        }

        logger.debug("Displayed exercise: {}", sb.toString());
    }
}
