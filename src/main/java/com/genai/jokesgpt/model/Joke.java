package com.genai.jokesgpt.model;

/**
 * Represents a joke with textual content.
 * This model is used to encapsulate the joke returned from the joke generation service.
 */
public class Joke {

    /** The actual joke content as a string. */
    private String content;

    /** Default constructor. */
    public Joke() {
    }

    /**
     * Constructs a Joke with the specified content.
     *
     * @param content The text of the joke.
     */
    public Joke(String content) {
        this.content = content;
    }

    /**
     * Gets the joke content.
     *
     * @return The joke text.
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the joke content.
     *
     * @param content The joke text to set.
     */
    public void setContent(String content) {
        this.content = content;
    }
}
