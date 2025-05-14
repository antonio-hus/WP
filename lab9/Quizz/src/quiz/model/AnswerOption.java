package quiz.model;

/**
 * Represents a single answer option for a question in the quiz.
 * This class stores the option's unique identifier, the question it belongs to,
 * a label for display (e.g., 'A', 'B', '1', '2'), the text of the option,
 * and a boolean indicating if this option is the correct answer.
 */
public class AnswerOption {
    /**
     * The unique identifier for this answer option.
     */
    private Integer id;

    /**
     * The identifier of the question to which this answer option belongs.
     * This links the option back to its parent question.
     */
    private Integer questionId;

    /**
     * A label used to display this answer option to the user
     * (e.g., 'A', 'B', 'C', '1', '2', '3').
     */
    private String optionLabel;

    /**
     * The actual text content of this answer option.
     */
    private String optionText;

    /**
     * A boolean value indicating whether this answer option is the correct answer
     * for its associated question.
     */
    private Boolean isCorrect;

    /**
     * Default constructor for the AnswerOption class.
     * Initializes a new AnswerOption object with default values (likely null or zero).
     */
    public AnswerOption() {}

    /**
     * Constructor for the AnswerOption class that allows setting all properties
     * upon object creation.
     *
     * @param id          The unique identifier for this answer option.
     * @param questionId  The ID of the question this option belongs to.
     * @param optionLabel The label to display for this option.
     * @param optionText  The text content of this answer option.
     * @param isCorrect   True if this option is the correct answer, false otherwise.
     */
    public AnswerOption(Integer id, Integer questionId, String optionLabel, String optionText, Boolean isCorrect) {
        this.id = id;
        this.questionId = questionId;
        this.optionLabel = optionLabel;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
    }

    /**
     * Retrieves the unique identifier of this answer option.
     *
     * @return The ID of the answer option.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this answer option.
     *
     * @param id The new ID to assign to the answer option.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the identifier of the question this answer option belongs to.
     *
     * @return The ID of the question.
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * Sets the identifier of the question this answer option belongs to.
     *
     * @param questionId The new ID of the question.
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * Retrieves the label used to display this answer option.
     *
     * @return The display label of the option.
     */
    public String getOptionLabel() {
        return optionLabel;
    }

    /**
     * Sets the label used to display this answer option.
     *
     * @param optionLabel The new display label for the option.
     */
    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }

    /**
     * Retrieves the text content of this answer option.
     *
     * @return The text of the option.
     */
    public String getOptionText() {
        return optionText;
    }

    /**
     * Sets the text content of this answer option.
     *
     * @param optionText The new text content for the option.
     */
    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    /**
     * Retrieves whether this answer option is the correct answer.
     *
     * @return True if the option is correct, false otherwise.
     */
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    /**
     * Sets whether this answer option is the correct answer.
     *
     * @param isCorrect The new boolean value indicating if the option is correct.
     */
    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}