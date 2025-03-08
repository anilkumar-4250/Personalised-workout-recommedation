package com.example.fitness.model;

import jakarta.persistence.*;

@Entity
public class UserExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String fitnessLevelofuser;

    @Column(length = 1000)
    private String exerciseDetailsofuser;

    @Column(nullable = false, length = 500)
    private String usersGoals;

    @Column(length = 1000)
    private String injuryNotesandlimitations;

    public UserExercise() {}

    public UserExercise(String fitnessLevel, String exerciseDetails, String userGoals, String injuryNotes) {
        this.fitnessLevelofuser = fitnessLevel;
        this.exerciseDetailsofuser = exerciseDetails;
        this.usersGoals = userGoals;
        this.injuryNotesandlimitations = injuryNotes;
    }

    
}
