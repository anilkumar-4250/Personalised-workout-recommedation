package com.example.fitness.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GroqService {

  

   
    private final ObjectMapper objectMapper = new ObjectMapper();

   
    @Value("${groq.api.key}")
    private String groqApiKey;

    public String getWorkoutPlan(String fitnessLevel, String exerciseDetails, String userGoals, String injuryNotes) {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        if (groqApiKey == null || groqApiKey.isBlank()) {
           
            return "<div class='alert alert-danger'>API key is missing</div>";
        }

        
        String systemPrompt = String.format(
                "As an AI personal trainer, generate a complete workout and nutrition plan for a user with the following details:<br>" +
                        "Fitness Level: %s<br>" +
                        "Previous Exercises: %s<br>" +
                        "User Goals: %s<br>" +
                        "Injury Notes: %s<br><br>" +
                        "The response must be a fully formatted HTML snippet (without markdown or code formatting) that contains the following sections:<br>" +
                        "- Warm-up routine<br>" +
                        "- Main workout (exercises, sets, reps, form tips)<br>" +
                        "- Cool-down stretches<br>" +
                        "- Nutrition plan (macros, meals, hydration)<br>" +
                        "- Two sample workout scenarios<br><br>" +
                        "Ensure that the HTML is valid and can be directly rendered in a web page.",
                fitnessLevel, exerciseDetails, userGoals, injuryNotes
        );

        try {
            
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama-3.3-70b-versatile");
            requestBody.put("temperature", 1);
            requestBody.put("max_completion_tokens", 1024);
            requestBody.put("top_p", 1);
            requestBody.put("stream", false);
            
            requestBody.put("response_format", Map.of("type", "text"));

          
            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", systemPrompt));
            messages.add(Map.of("role", "user", "content", "Generate my personalized fitness plan as valid HTML"));
            requestBody.put("messages", messages);

            String requestBodyJson = objectMapper.writeValueAsString(requestBody);

            
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey);
            headers.set(HttpHeaders.ACCEPT, "application/json");
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyJson, headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
               

              
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode choices = rootNode.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    JsonNode contentNode = choices.get(0).path("message").path("content");
                    if (!contentNode.isMissingNode() && contentNode.isTextual()) {
                        
                        return contentNode.asText();
                    } 
                }
                return "<div class='alert alert-danger'>Invalid response structure from API</div>";
            } else {
               
                return String.format("<div class='alert alert-danger'>API responded with status %d</div>", responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
           
            return String.format("<div class='alert alert-danger'>API request failed: %s</div>", e.getMessage());
        }
    }
}
