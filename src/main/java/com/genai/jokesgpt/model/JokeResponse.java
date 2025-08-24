package com.genai.jokesgpt.model;

/**
 * Represents the response returned from the joke generation service.
 * Contains the status of the request, any error message, and the generated joke.
 */
public class JokeResponse {

    /** Status code indicating success (0) or failure (1). */
    private int status;

    /** Error message if the joke generation failed. */
    private String error;

    /** The generated joke object. */
    private Joke joke;

    /** Default constructor. */
    public JokeResponse() {
    }

    /**
     * Constructor for successful joke response.
     *
     * @param status Status code (typically 0 for success)
     * @param joke The generated joke
     */
    public JokeResponse(int status, Joke joke) {
        this.status = status;
        this.joke = joke;
    }

    /**
     * Constructor for error response.
     *
     * @param status Status code (typically 1 for error)
     * @param error Error message describing the failure
     * @param joke The joke object (may be null if error occurred)
     */
    public JokeResponse(int status, String error, Joke joke) {
        this.status = status;
        this.error = error;
        this.joke = joke;
    }

    /** @return The status code of the response */
    public int getStatus() {
        return status;
    }

    /** @param status Sets the status code */
    public void setStatus(int status) {
        this.status = status;
    }

    /** @return The error message, if any */
    public String getError() {
        return error;
    }

    /** @param error Sets the error message */
    public void setError(String error) {
        this.error = error;
    }

    /** @return The joke object */
    public Joke getJoke() {
        return joke;
    }

    /** @param joke Sets the joke object */
    public void setJoke(Joke joke) {
        this.joke = joke;
    }
}
