package com.example.fitness.controller;

import com.example.fitness.model.UserExercise;
import com.example.fitness.repository.UserExerciseRepository;
import com.example.fitness.service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fitness")
public class FitnesssController {

    @Autowired
    private UserExerciseRepository repository;

    @Autowired
    private GroqService groqService;

	private String userGoals;

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/recommend")
    public String getRecommendations(@RequestParam String fitnessLevelofuser,
                                     @RequestParam String exerciseDetailsofuser,
                                     @RequestParam String usersGoals,
                                     @RequestParam(required = false) String injuryNotesandlimitations,
                                     Model model) {
       
        UserExercise userExercise = new UserExercise(fitnessLevelofuser, exerciseDetailsofuser, usersGoals, injuryNotesandlimitations);
        repository.save(userExercise);
        
        String response = groqService.getWorkoutPlan(fitnessLevelofuser, exerciseDetailsofuser, usersGoals, injuryNotesandlimitations);
        model.addAttribute("response", response);
        return "result";
    }
}
