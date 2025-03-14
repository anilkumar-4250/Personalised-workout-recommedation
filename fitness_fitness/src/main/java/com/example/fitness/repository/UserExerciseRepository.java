package com.example.fitness.repository;

import com.example.fitness.model.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExerciseRepository extends JpaRepository<UserExercise, Long> {}
