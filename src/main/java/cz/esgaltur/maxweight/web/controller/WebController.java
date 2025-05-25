package cz.esgaltur.maxweight.web.controller;

import brave.Tracer;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import cz.esgaltur.maxweight.web.service.ProgramValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Web controller for the MaxWeight application.
 * Handles HTTP requests and returns Thymeleaf views.
 */
@Controller
public class WebController {

    public static final String INDEX = "index";
    private final ProgramValidationService validationService;
    private final ProgramGenerationService generationService;
    private final Tracer tracer;

    @Autowired
    public WebController(ProgramValidationService validationService, ProgramGenerationService generationService, Tracer tracer) {
        this.validationService = validationService;
        this.generationService = generationService;
        this.tracer = tracer;
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

        if (tracer.currentSpan() != null) {
            String traceId = tracer.currentSpan().context().traceIdString();
            model.addAttribute("traceId", traceId);
        }

        return INDEX;
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
        if (!validationService.isValidWeekRange(fromWeek, toWeek)) {
            model.addAttribute("error", validationService.getInvalidWeekRangeMessage());
            return INDEX;
        }

        if (!validationService.isValidMaxWeight(maxWeight)) {
            model.addAttribute("error", validationService.getInvalidMaxWeightMessage());
            return INDEX;
        }

        // Generate programs
        List<TrainingProgram> programs = generationService.generatePrograms(fromWeek, toWeek, maxWeight);

        model.addAttribute("programs", programs);
        model.addAttribute("maxWeight", maxWeight);
        model.addAttribute("fromWeek", fromWeek);
        model.addAttribute("toWeek", toWeek);

        if (tracer.currentSpan() != null) {
            String traceId = tracer.currentSpan().context().traceIdString();
            model.addAttribute("traceId", traceId);
        }

        return "result";
    }
}
