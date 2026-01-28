package cz.esgaltur.maxweight.web.controller;

import brave.Tracer;
import cz.esgaltur.maxweight.core.model.TrainingProgram;
import cz.esgaltur.maxweight.web.dto.ProgramRangeRequest;
import cz.esgaltur.maxweight.web.service.ProgramGenerationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Web controller for the MaxWeight application.
 * Handles HTTP requests and returns Thymeleaf views.
 */
@Controller
public class WebController {

    public static final String INDEX = "index";
    private static final String PAGE_TITLE = "MaxWeight - Bench Press Training Program Generator";
    private final ProgramGenerationService generationService;
    private final Tracer tracer;

    @Autowired
    public WebController(ProgramGenerationService generationService, Tracer tracer) {
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
        applyPageMetadata(model);

        return INDEX;
    }

    /**
     * Generate and display training programs
     * 
     * @param request The validated program range request
     * @param bindingResult Binding result for validation errors
     * @param model The Spring MVC model
     * @return The name of the Thymeleaf template
     */
    @PostMapping("/generate")
    public String generateProgram(
            @Valid @ModelAttribute("programRange") ProgramRangeRequest request,
            BindingResult bindingResult,
            Model model) {

        applyPageMetadata(model);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return INDEX;
        }

        // Generate programs
        List<TrainingProgram> programs = generationService.generatePrograms(
            request.getFromWeek(),
            request.getToWeek(),
            request.getMaxWeight()
        );

        model.addAttribute("programs", programs);
        model.addAttribute("maxWeight", request.getMaxWeight());
        model.addAttribute("fromWeek", request.getFromWeek());
        model.addAttribute("toWeek", request.getToWeek());

        return "result";
    }

    private void applyPageMetadata(Model model) {
        model.addAttribute("title", PAGE_TITLE);
        if (tracer.currentSpan() != null) {
            String traceId = tracer.currentSpan().context().traceIdString();
            model.addAttribute("traceId", traceId);
        }
    }
}
