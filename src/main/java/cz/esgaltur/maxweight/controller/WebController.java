package cz.esgaltur.maxweight.controller;

import cz.esgaltur.maxweight.model.TrainingProgram;
import cz.esgaltur.maxweight.model.Week;
import cz.esgaltur.maxweight.service.ProgramFactory;
import cz.esgaltur.maxweight.service.ProgramDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Web controller for the MaxWeight application.
 * Handles HTTP requests and returns Thymeleaf views.
 */
@Controller
public class WebController {

    private final ProgramFactory programFactory;

    @Autowired
    public WebController(ProgramFactory programFactory) {
        this.programFactory = programFactory;
    }

    /**
     * Display the home page
     * 
     * @param model The Spring MVC model
     * @return The name of the Thymeleaf template
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "MaxWeight - Bench Press Training Program Generator");
        return "index";
    }

    /**
     * Generate and display training programs
     * 
     * @param fromWeek The starting week number
     * @param toWeek The ending week number
     * @param maxWeight The maximum weight for bench press
     * @param model The Spring MVC model
     * @return The name of the Thymeleaf template
     */
    @PostMapping("/generate")
    public String generateProgram(
            @RequestParam("fromWeek") int fromWeek,
            @RequestParam("toWeek") int toWeek,
            @RequestParam("maxWeight") int maxWeight,
            Model model) {
        
        // Validate input
        if (fromWeek < 1 || fromWeek > 6 || toWeek < 1 || toWeek > 6 || fromWeek > toWeek) {
            model.addAttribute("error", "Invalid week range. Please select weeks between 1 and 6, with 'from' week less than or equal to 'to' week.");
            return "index";
        }
        
        if (maxWeight <= 0) {
            model.addAttribute("error", "Max weight must be greater than 0.");
            return "index";
        }
        
        // Generate programs
        List<TrainingProgram> programs = new ArrayList<>();
        for (int weekNumber = fromWeek; weekNumber <= toWeek; weekNumber++) {
            Week week = Week.fromWeekNumber(weekNumber);
            TrainingProgram program = programFactory.createProgram(week, maxWeight);
            programs.add(program);
        }
        
        model.addAttribute("programs", programs);
        model.addAttribute("maxWeight", maxWeight);
        model.addAttribute("fromWeek", fromWeek);
        model.addAttribute("toWeek", toWeek);
        
        return "result";
    }
}