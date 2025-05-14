package quiz.model;

/**
 * Represents a single question in the quiz application.
 * This class holds the unique identifier for a question and its textual content.
 */
public class Question {
    /**
     * The unique identifier for this question.
     */
    private Integer id;

    /**
     * The text content of the question itself.
     */
    private String question;

    /**
     * Default constructor for the Question class.
     * Initializes a new Question object with default values (likely null).
     */
    public Question() {}

    /**
     * Constructor for the Question class that allows setting the ID and question
     * text upon object creation.
     *
     * @param id       The unique identifier for the question.
     * @param question The text content of the question.
     */
    public Question(Integer id, String question) {
        this.id = id;
        this.question = question;
    }

    /**
     * Retrieves the unique identifier of this question.
     *
     * @return The ID of the question.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this question.
     *
     * @param id The new ID to assign to the question.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the text content of the question.
     *
     * @return The text of the question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the text content of the question.
     *
     * @param question The new text content for the question.
     */
    public void setQuestion(String question) {
        this.question = question;
    }
}