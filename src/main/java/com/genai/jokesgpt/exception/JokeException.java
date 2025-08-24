package com.genai.jokesgpt.exception;

/**
 * Custom exception class used to represent errors during joke generation.
 * Contains a status code and an error message for detailed error reporting.
 */
public class JokeException extends Throwable {

    /** Status code indicating the type of error (e.g., 1 for failure). */
    private int status;

    /** Human-readable error message describing the issue. */
    private String errorMessage;

    /**
     * Constructs a new {@code JokeException} with the specified status and error message.
     *
     * @param status        The error status code.
     * @param errorMessage  The descriptive error message.
     */
    public JokeException(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the error status code.
     *
     * @return The status code.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the error status code.
     *
     * @param status The status code to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the error message.
     *
     * @return The descriptive error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage The message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
