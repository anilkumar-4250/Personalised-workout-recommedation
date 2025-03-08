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

  

   
    private final ObjectMapper oBjectMapper = new ObjectMapper();

   
    @Value("${groq.api.key}")
    private String groqApiKey;

    public String getWorkoutPlan(String fitnessLevelofuser, String exerciseDetailsofuser, String usersGoals, String injuryNotesandlimitations) {
        String url = "https://api.groq.com/openai/v1/chat/completions";

        if (groqApiKey == null || groqApiKey.isBlank()) {
           
            return "<div class='alert'>API key is missing</div>";
        }

        
        String systemPrompt = String.format(
                "As an AI personal trainer, generate a complete workout and nutrition plan for a user with the following details:<br>" +
                        "Fitness Level of user: %s<br>" +
                        "Previous Exercises done by user: %s<br>" +
                        "User Goals to acheive: %s<br>" +
                        "Injury Notes and limitations: %s<br><br>" +
                        "The response must be a fully formatted HTML snippet (without markdown or code formatting) that contains the following sections:<br>" +
                        "- Warm-up routine for user<br>" +
                        "- Main workout plan  (exercises, sets, reps, form tips)<br>" +
                        "- Cool-down  stretches<br>" +
                        "- Nutrition plan  (macros, meals, hydration)<br>" +
                        "- Two sample workout scenarios<br><br>" +
                        "Ensure that the HTML is valid and can be directly rendered in a web page.",
                fitnessLevelofuser, exerciseDetailsofuser, usersGoals, injuryNotesandlimitations
        );

        try {
            
            Map<String, Object> RequestedBody = new HashMap<>();
            RequestedBody.put("model", "llama-3.3-70b-versatile");
            RequestedBody.put("temperature", 1);
            RequestedBody.put("max_completion_tokens", 1024);
            RequestedBody.put("top_p", 1);
            RequestedBody.put("stream", false);
            
            RequestedBody.put("response_format", Map.of("type", "text"));

          
            List<Map<String, String>> messages = new ArrayList<>();
            messages.add(Map.of("role", "system", "content", systemPrompt));
            messages.add(Map.of("role", "user", "content", "Generate my personalized fitness plan as valid HTML"));
            RequestedBody.put("messages", messages);

            String requestBodyJson = oBjectMapper.writeValueAsString(RequestedBody);

            
            HttpHeaders Headers = new HttpHeaders();
            Headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + groqApiKey);
            Headers.set(HttpHeaders.ACCEPT, "application/json");
            Headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> RequestedEntity = new HttpEntity<>(requestBodyJson, Headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, RequestedEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
               

              
                JsonNode rootNode = oBjectMapper.readTree(responseBody);
                JsonNode choices = rootNode.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    JsonNode contentNode = choices.get(0).path("message").path("content");
                    if (!contentNode.isMissingNode() && contentNode.isTextual()) {
                        
                        return contentNode.asText();
                    } 
                }
                return "<div class='danger alert'>Invalid  structure from API</div>";
            } else {
               
                return String.format("<div class='danger alert'>API  status %d</div>", responseEntity.getStatusCodeValue());
            }
        } catch (Exception e) {
           
            return String.format("<div class='danger alert'>API request failed: %s</div>", e.getMessage());
        }
    }
}
