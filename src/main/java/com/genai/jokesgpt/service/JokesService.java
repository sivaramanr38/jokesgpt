package com.genai.jokesgpt.service;

import com.genai.jokesgpt.config.ModelConfiguration;
import com.genai.jokesgpt.exception.JokeException;
import com.genai.jokesgpt.helper.Tags;
import com.genai.jokesgpt.model.Joke;
import com.genai.jokesgpt.model.JokeResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class responsible for generating jokes using the OpenRouter API.
 * It builds prompts, sends HTTP requests, and parses responses into structured joke data.
 */
@Service
public class JokesService {

    /** The URL endpoint for the OpenRouter API, injected from application properties. */
    @Value("${openrouter.url}")
    private String openRouterUrl;

    /** The API key used for authenticating requests to OpenRouter, injected from application properties. */
    @Value("${openrouter.key}")
    private String apiKey;

    /** Configuration helper for building model-specific request payloads. */
    @Autowired
    ModelConfiguration modelConfiguration;

    /**
     * Generates a joke based on the provided subject by sending a prompt to the OpenRouter API.
     *
     * @param jokeSubject The subject about which the joke should be generated.
     * @return A {@link JokeResponse} containing either the joke or an error message.
     * @throws JokeException If an error occurs during the API request or response parsing.
     */
    public JokeResponse getJoke(String jokeSubject) throws JokeException {
        JokeResponse jokeResponse = new JokeResponse();

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String promptWithContext = getPromptWithContext(jokeSubject);
            String body = buildRequestBody(promptWithContext);

            HttpEntity<String> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(openRouterUrl, request, String.class);

            String httpResponseBody = response.getBody();

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                JsonObject rootBody = JsonParser.parseString(httpResponseBody).getAsJsonObject();
                Joke joke = new Joke();
                String content = rootBody
                        .getAsJsonArray(Tags.CHOICES)
                        .get(0)
                        .getAsJsonObject()
                        .getAsJsonObject(Tags.MESSAGE)
                        .get(Tags.CONTENT)
                        .getAsString();
                joke.setContent(content);
                jokeResponse.setStatus(0);
                jokeResponse.setJoke(joke);
            } else {
                jokeResponse.setStatus(1);
                jokeResponse.setError(Tags.GENERIC_ERROR);
            }
        } catch (Exception ex) {
            throw new JokeException(1, ex.getMessage());
        }

        return jokeResponse;
    }

    /**
     * Builds the JSON request body to be sent to the OpenRouter API.
     *
     * @param prompt The prompt string to include in the request.
     * @return A JSON-formatted string representing the request body.
     */
    private String buildRequestBody(String prompt) {
        JsonObject message = new JsonObject();
        message.addProperty(Tags.ROLE, Tags.USER);
        message.addProperty(Tags.CONTENT, prompt);

        JsonArray messages = new JsonArray();
        messages.add(message);

        JsonObject requestBody = modelConfiguration.configureModel(messages);
        return requestBody.toString();
    }

    /**
     * Constructs a full prompt by injecting the joke subject into predefined templates.
     *
     * @param jokeSubject The subject to be inserted into the prompt.
     * @return A complete prompt string with context.
     */
    private String getPromptWithContext(String jokeSubject) {
        StringBuilder promptBuilder = new StringBuilder();
        String finalPrompt1 = Tags.PROMPT_1.replace(Tags.SUBJECT_VALUE, jokeSubject);
        promptBuilder.append(finalPrompt1);
        promptBuilder.append(Tags.PROMPT_2);
        return promptBuilder.toString();
    }
}
