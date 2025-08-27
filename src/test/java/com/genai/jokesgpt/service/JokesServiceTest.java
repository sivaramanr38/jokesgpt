package com.genai.jokesgpt.service;

import com.genai.jokesgpt.config.ModelConfiguration;
import com.genai.jokesgpt.exception.JokeException;
import com.genai.jokesgpt.helper.Tags;
import com.genai.jokesgpt.model.JokeResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JokesService.class)
public class JokesServiceTest {

    private JokesService jokesService;

    @Mock
    private ModelConfiguration modelConfiguration;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        PowerMockito.whenNew(RestTemplate.class).withNoArguments().thenReturn(restTemplate);
        jokesService = new JokesService();
        jokesService.openRouterUrl = "https://api.openrouter.ai";
        jokesService.apiKey = "test-api-key";
        jokesService.modelConfiguration = modelConfiguration;
    }

    @Test
    public void testGetJoke_successfulResponse_structureValidation() throws JokeException {
        String jokeSubject = "penguins";
        String mockContent = "This is a mock joke about penguins.";

        JsonObject messageObj = new JsonObject();
        messageObj.addProperty(Tags.CONTENT, mockContent);

        JsonObject choiceObj = new JsonObject();
        choiceObj.add(Tags.MESSAGE, messageObj);

        JsonArray choices = new JsonArray();
        choices.add(choiceObj);

        JsonObject responseJson = new JsonObject();
        responseJson.add(Tags.CHOICES, choices);

        JsonObject dummyRequestBody = new JsonObject();
        when(modelConfiguration.configureModel(any())).thenReturn(dummyRequestBody);
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(responseJson.toString(), HttpStatus.OK));

        JokeResponse response = jokesService.getJoke(jokeSubject);

        assertEquals(0, response.getStatus());
        assertNotNull(response.getJoke());
        assertTrue(response.getJoke().getContent().contains("penguins"));
    }

    @Test
    public void testGetJoke_apiFailureHandledGracefully() {
        String jokeSubject = "robots";
        when(modelConfiguration.configureModel(any())).thenReturn(new JsonObject());
        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenThrow(new RuntimeException("API unreachable"));

        try {
            jokesService.getJoke(jokeSubject);
            fail("Expected JokeException to be thrown");
        } catch (JokeException e) {
            assertEquals(1, e.getStatus());
            assertTrue(e.getMessage().contains("API unreachable"));
        }
    }
}
