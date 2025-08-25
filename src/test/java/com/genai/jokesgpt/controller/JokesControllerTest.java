package com.genai.jokesgpt.controller;

import com.genai.jokesgpt.exception.JokeException;
import com.genai.jokesgpt.helper.Tags;
import com.genai.jokesgpt.model.Joke;
import com.genai.jokesgpt.model.JokeResponse;
import com.genai.jokesgpt.service.JokesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("deprecation")
@WebMvcTest(JokesController.class)
class JokesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JokesService jokesService;

    @Test
    void testShowChatPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(Tags.RESPONSE, ""))
                .andExpect(view().name(Tags.CHAT));
    }

    @Test
    void testHandlePromptSuccess() throws Exception, JokeException {
        Joke joke = new Joke();
        joke.setContent("Why did the chicken cross the road? To get to the other side!");
        JokeResponse response = new JokeResponse();
        response.setStatus(0);
        response.setJoke(joke);

        Mockito.when(jokesService.getJoke(anyString())).thenReturn(response);

        mockMvc.perform(post("/ask").param(Tags.PROMPT, "Tell me a joke"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(Tags.PROMPT, "Tell me a joke"))
                .andExpect(model().attribute(Tags.RESPONSE, joke.getContent()))
                .andExpect(view().name(Tags.CHAT));
    }

    @Test
    void testHandlePromptErrorWithMessage() throws Exception, JokeException {
        JokeResponse response = new JokeResponse();
        response.setStatus(1);
        response.setError("Service unavailable");

        Mockito.when(jokesService.getJoke(anyString())).thenReturn(response);

        mockMvc.perform(post("/ask").param(Tags.PROMPT, "Tell me a joke"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(Tags.RESPONSE, "Service unavailable"))
                .andExpect(view().name(Tags.CHAT));
    }

    @Test
    void testHandlePromptErrorWithoutMessage() throws Exception, JokeException {
        JokeResponse response = new JokeResponse();
        response.setStatus(1);
        response.setError(null);

        Mockito.when(jokesService.getJoke(anyString())).thenReturn(response);

        mockMvc.perform(post("/ask").param(Tags.PROMPT, "Tell me a joke"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(Tags.RESPONSE, Tags.GENERIC_ERROR))
                .andExpect(view().name(Tags.CHAT));
    }

    @Test
    void testHandlePromptThrowsException() throws Exception, JokeException {
        Mockito.when(jokesService.getJoke(anyString())).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/ask").param(Tags.PROMPT, "Tell me a joke"))
                .andExpect(status().isOk())
                .andExpect(model().attribute(Tags.RESPONSE, Tags.GENERIC_ERROR))
                .andExpect(view().name(Tags.CHAT));
    }
}
