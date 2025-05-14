package quiz.model;

/**
 * Represents a specific question answered within a particular quiz run.
 * This class tracks which question was presented, the user's chosen option,
 * and whether the chosen option was correct.
 */
public class QuizRunQuestion {
    /**
     * The unique identifier for this specific answered question within a quiz run.
     */
    private Integer id;

    /**
     * The identifier of the quiz run to which this answered question belongs.
     * This links the answer to a specific instance of a user taking a quiz.
     */
    private Integer quizRunId;

    /**
     * The identifier of the actual question from the question bank that was answered.
     * This links to the content of the question.
     */
    private Integer questionId;

    /**
     * The identifier of the option chosen by the user for this question.
     * This links to one of the possible answers for the question.
     */
    private Integer chosenOptionId;

    /**
     * A boolean value indicating whether the user's chosen option was the correct answer.
     */
    private Boolean isCorrect;

    /**
     * Default constructor for the QuizRunQuestion class.
     * Initializes a new QuizRunQuestion object with default values (likely null or zero).
     */
    public QuizRunQuestion() {}

    /**
     * Constructor for the QuizRunQuestion class that allows setting all properties
     * upon object creation.
     *
     * @param id             The unique identifier for this answered question.
     * @param quizRunId      The ID of the quiz run this answer belongs to.
     * @param questionId     The ID of the question that was answered.
     * @param chosenOptionId The ID of the option chosen by the user.
     * @param isCorrect      A boolean indicating if the chosen option was correct.
     */
    public QuizRunQuestion(Integer id, Integer quizRunId, Integer questionId, Integer chosenOptionId, Boolean isCorrect) {
        this.id = id;
        this.quizRunId = quizRunId;
        this.questionId = questionId;
        this.chosenOptionId = chosenOptionId;
        this.isCorrect = isCorrect;
    }

    /**
     * Retrieves the unique identifier of this answered question.
     *
     * @return The ID of the quiz run question.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this answered question.
     *
     * @param id The new ID to assign to the quiz run question.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the identifier of the quiz run this answer belongs to.
     *
     * @return The ID of the quiz run.
     */
    public Integer getQuizRunId() {
        return quizRunId;
    }

    /**
     * Sets the identifier of the quiz run this answer belongs to.
     *
     * @param quizRunId The new ID of the quiz run.
     */
    public void setQuizRunId(Integer quizRunId) {
        this.quizRunId = quizRunId;
    }

    /**
     * Retrieves the identifier of the question that was answered.
     *
     * @return The ID of the question.
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * Sets the identifier of the question that was answered.
     *
     * @param questionId The new ID of the question.
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * Retrieves the identifier of the option chosen by the user.
     *
     * @return The ID of the chosen option.
     */
    public Integer getChosenOptionId() {
        return chosenOptionId;
    }

    /**
     * Sets the identifier of the option chosen by the user.
     *
     * @param chosenOptionId The new ID of the chosen option.
     */
    public void setChosenOptionId(Integer chosenOptionId) {
        this.chosenOptionId = chosenOptionId;
    }

    /**
     * Retrieves whether the chosen option was correct.
     *
     * @return True if the chosen option was correct, false otherwise.
     */
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    /**
     * Sets whether the chosen option was correct.
     *
     * @param isCorrect The new boolean value indicating if the chosen option was correct.
     */
    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}