package com.genai.jokesgpt.config;

import com.genai.jokesgpt.helper.Tags;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class responsible for constructing the request body
 * used to communicate with the OpenRouter API.
 */
@Configuration
public class ModelConfiguration {

    /** The name of the model to be used, injected from application properties. */
    @Value("${openrouter.model.name}")
    private String modelName;

    /**
     * Builds a JSON request body containing the model name and message array.
     *
     * @param messages A {@link JsonArray} of message objects to send to the model.
     * @return A {@link JsonObject} representing the complete request payload.
     */
    public JsonObject configureModel(JsonArray messages) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty(Tags.MODEL, modelName); // Set model name
        requestBody.add(Tags.MESSAGES, messages);       // Add messages array
        return requestBody;
    }

}
