package com.genai.jokesgpt.helper;

/**
 * A utility class containing constant string tags used throughout the application.
 * These tags help maintain consistency across views, prompts, and API interactions.
 */
public class Tags {

    /** View name for the chat interface. */
    public static final String CHAT = "chat";

    /** Attribute name for storing the joke response in the model. */
    public static final String RESPONSE = "response";

    /** Attribute name for storing the user's prompt in the model. */
    public static final String PROMPT = "prompt";

    /** Generic error message shown when joke generation fails. */
    public static final String GENERIC_ERROR = "Sorry, Please try again later!";

    /** JSON key for the list of choices returned by the OpenRouter API. */
    public static final String CHOICES = "choices";

    /** JSON key for the message object inside each choice. */
    public static final String MESSAGE = "message";

    /** JSON key for the actual joke content. */
    public static final String CONTENT = "content";

    /** JSON key for specifying the role in the prompt (e.g., user, assistant). */
    public static final String ROLE = "role";

    /** Value representing the user role in the prompt. */
    public static final String USER = "user";

    /** JSON key for specifying the model used in the request. */
    public static final String MODEL = "model";

    /** JSON key for the array of messages in the request body. */
    public static final String MESSAGES = "messages";

    /** Placeholder used in prompt templates to inject the joke subject. */
    public static final String SUBJECT_VALUE = "subject_value";

    /** First part of the prompt template, requesting jokes about a specific subject. */
    public static final String PROMPT_1 = "1. Tell me 3 funny jokes about subject_value include relevant emoji";

    /** Second part of the prompt template, instructing the model to respond only with jokes. */
    public static final String PROMPT_2 = " 2. Do not say anything else apart from the joke like";
}
