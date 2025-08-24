package com.genai.jokesgpt.controller;

import com.genai.jokesgpt.exception.JokeException;
import com.genai.jokesgpt.helper.Tags;
import com.genai.jokesgpt.model.JokeResponse;
import com.genai.jokesgpt.service.JokesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that handles HTTP requests for the jokes generator application.
 * It manages the chat interface and processes user prompts to generate jokes.
 */
@Controller
public class JokesController {

    /** Service layer for handling joke generation logic. */
    @Autowired
    JokesService jokesService;

    /**
     * Handles GET requests to the root URL ("/") and displays the chat page.
     *
     * @param model The model object used to pass attributes to the view.
     * @return The name of the chat view template.
     */
    @GetMapping("/")
    public String showChat(Model model) {
        model.addAttribute(Tags.RESPONSE, ""); // Initialize response attribute
        return Tags.CHAT; // Return view name defined in Tags
    }

    /**
     * Handles POST requests to "/ask" with a user-submitted prompt.
     * It calls the joke generation service and updates the model with the result.
     *
     * @param prompt The user's input prompt for joke generation.
     * @param model  The model object used to pass attributes to the view.
     * @return The name of the chat view template with the joke or error message.
     */
    @PostMapping("/ask")
    public String handlePrompt(@RequestParam(Tags.PROMPT) String prompt, Model model) {
        try {
            // Call service to get joke based on prompt
            JokeResponse jokeResponse = jokesService.getJoke(prompt);
            model.addAttribute(Tags.PROMPT, prompt); // Preserve user input

            // If joke generation is successful
            if (jokeResponse.getStatus() == 0) {
                String content = jokeResponse.getJoke().getContent();
                model.addAttribute(Tags.RESPONSE, content); // Display joke content
            } else {
                // Handle error response from joke service
                String errorMessage = jokeResponse.getError() != null ?
                        jokeResponse.getError() :
                        Tags.GENERIC_ERROR;
                model.addAttribute(Tags.RESPONSE, errorMessage);
            }
        } catch (Exception e) {
            // Catch-all for unexpected exceptions
            model.addAttribute(Tags.RESPONSE, Tags.GENERIC_ERROR);
        } catch (JokeException ex) {
            // Handle custom joke-related exceptions
            model.addAttribute(Tags.RESPONSE, ex.getErrorMessage());
        }
        return Tags.CHAT; // Return view name to render response
    }

}
