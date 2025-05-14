package quiz.model;

import java.time.LocalDateTime;

/**
 * Represents a single instance of a user taking a quiz.
 * This class stores information about when the quiz was taken,
 * the user who took it, the total number of questions, the number of questions
 * displayed per page (if applicable), and the counts of correct and wrong answers.
 */
public class QuizRun {
    /**
     * The unique identifier for this specific quiz run.
     */
    private Integer id;

    /**
     * The identifier of the user who participated in this quiz run.
     * This links the quiz attempt to a specific user.
     */
    private Integer userId;

    /**
     * The date and time when this quiz run was initiated.
     */
    private LocalDateTime runDate;

    /**
     * The total number of questions included in this quiz run.
     */
    private Integer totalQuestions;

    /**
     * The number of questions presented to the user on each page during the quiz.
     * This is relevant for paginated quizzes.
     */
    private Integer questionsPerPage;

    /**
     * The number of questions the user answered correctly during this quiz run.
     */
    private Integer correctCount;

    /**
     * The number of questions the user answered incorrectly during this quiz run.
     */
    private Integer wrongCount;

    /**
     * Default constructor for the QuizRun class.
     * Initializes a new QuizRun object with default values (likely null or zero).
     */
    public QuizRun() {}

    /**
     * Constructor for the QuizRun class that allows setting all properties
     * upon object creation.
     *
     * @param id              The unique identifier for this quiz run.
     * @param userId          The ID of the user who took the quiz.
     * @param runDate         The date and time the quiz was run.
     * @param totalQuestions  The total number of questions in the quiz.
     * @param questionsPerPage The number of questions displayed per page.
     * @param correctCount    The number of correct answers given by the user.
     * @param wrongCount      The number of incorrect answers given by the user.
     */
    public QuizRun(Integer id, Integer userId, LocalDateTime runDate, Integer totalQuestions, Integer questionsPerPage, Integer correctCount, Integer wrongCount) {
        this.id = id;
        this.userId = userId;
        this.runDate = runDate;
        this.totalQuestions = totalQuestions;
        this.questionsPerPage = questionsPerPage;
        this.correctCount = correctCount;
        this.wrongCount = wrongCount;
    }

    /**
     * Retrieves the unique identifier of this quiz run.
     *
     * @return The ID of the quiz run.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this quiz run.
     *
     * @param id The new ID to assign to the quiz run.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Retrieves the identifier of the user who participated in this quiz run.
     *
     * @return The ID of the user.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the identifier of the user who participated in this quiz run.
     *
     * @param userId The new ID of the user.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the date and time when this quiz run was initiated.
     *
     * @return The date and time of the quiz run.
     */
    public LocalDateTime getRunDate() {
        return runDate;
    }

    /**
     * Sets the date and time when this quiz run was initiated.
     *
     * @param runDate The new date and time for the quiz run.
     */
    public void setRunDate(LocalDateTime runDate) {
        this.runDate = runDate;
    }

    /**
     * Retrieves the total number of questions in this quiz run.
     *
     * @return The total number of questions.
     */
    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    /**
     * Sets the total number of questions in this quiz run.
     *
     * @param totalQuestions The new total number of questions.
     */
    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    /**
     * Retrieves the number of questions displayed per page during the quiz.
     *
     * @return The number of questions per page.
     */
    public Integer getQuestionsPerPage() {
        return this.questionsPerPage;
    }

    /**
     * Sets the number of questions displayed per page during the quiz.
     *
     * @param questionsPerPage The new number of questions per page.
     */
    public void setQuestionsPerPage(Integer questionsPerPage) {
        this.questionsPerPage = questionsPerPage;
    }

    /**
     * Retrieves the number of questions answered correctly by the user.
     *
     * @return The count of correct answers.
     */
    public Integer getCorrectCount() {
        return correctCount;
    }

    /**
     * Sets the number of questions answered correctly by the user.
     *
     * @param correctCount The new count of correct answers.
     */
    public void setCorrectCount(Integer correctCount) {
        this.correctCount = correctCount;
    }

    /**
     * Retrieves the number of questions answered incorrectly by the user.
     *
     * @return The count of wrong answers.
     */
    public Integer getWrongCount() {
        return wrongCount;
    }

    /**
     * Sets the number of questions answered incorrectly by the user.
     *
     * @param wrongCount The new count of wrong answers.
     */
    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }
}