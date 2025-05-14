package quiz.service;

import quiz.model.*;
import quiz.repository.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class for managing quiz logic, including retrieving questions,
 * starting quizzes, submitting answers, and finalizing quizzes.
 */
public class QuizService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuizRunRepository quizRunRepository;
    private final QuizRunQuestionRepository quizRunQuestionRepository;

    /**
     * Constructor for QuizService.
     * Initializes all the required repositories.
     *
     * @throws Exception If any of the repositories fail to initialize.
     */
    public QuizService() throws Exception {
        this.userRepository = new UserRepository();
        this.questionRepository = new QuestionRepository();
        this.answerOptionRepository = new AnswerOptionRepository();
        this.quizRunRepository = new QuizRunRepository();
        this.quizRunQuestionRepository = new QuizRunQuestionRepository();
    }

    /**
     * Retrieves all questions from the database.
     *
     * @return A List of all Question objects.
     * @throws Exception If a database access error occurs.
     */
    public List<Question> getAllQuestions() throws Exception {
        // Return all questions from the repository
        return questionRepository.findAll();
    }

    /**
     * Retrieves all answer options for a specific question from the database.
     *
     * @param questionId The ID of the question to retrieve answer options for.
     * @return A List of AnswerOption objects for the given question ID.
     * @throws Exception If a database access error occurs.
     */
    public List<AnswerOption> getAnswerOptionsForQuestion(int questionId) throws Exception {
        // Return answer options for a question from the repository
        return answerOptionRepository.findByQuestionId(questionId);
    }

    /**
     * Starts a new quiz for a user, selecting questions and creating a QuizRun.
     *
     * @param userId The ID of the user starting the quiz.
     * @param totalQuestions The total number of questions for the quiz.
     * @param questionsPerPage The number of questions to display per page.
     * @return The newly created QuizRun object.
     * @throws Exception If a database access error occurs.
     */
    public QuizRun startNewQuiz(int userId, int totalQuestions, int questionsPerPage) throws Exception {
        // Create new quiz run
        QuizRun quizRun = new QuizRun();
        quizRun.setUserId(userId);
        quizRun.setTotalQuestions(totalQuestions);
        quizRun.setQuestionsPerPage(questionsPerPage);
        quizRun.setCorrectCount(0);
        quizRun.setWrongCount(0);
        quizRun.setRunDate(LocalDateTime.now());

        // Insert into database
        quizRunRepository.insert(quizRun);

        // Get all available questions
        List<Question> allQuestions = questionRepository.findAll();

        // Randomly select questions for this quiz run
        Collections.shuffle(allQuestions);
        int numQuestions = Math.min(totalQuestions, allQuestions.size());

        // Create quiz run questions
        for (int i = 0; i < numQuestions; i++) {
            Question question = allQuestions.get(i);
            QuizRunQuestion quizRunQuestion = new QuizRunQuestion();
            quizRunQuestion.setQuizRunId(quizRun.getId());
            quizRunQuestion.setQuestionId(question.getId());
            quizRunQuestion.setIsCorrect(false); // Initially not answered

            quizRunQuestionRepository.insert(quizRunQuestion);
        }

        return quizRun;
    }

    /**
     * Retrieves all QuizRunQuestion objects for a given quiz run ID.
     *
     * @param quizRunId The ID of the quiz run.
     * @return A List of QuizRunQuestion objects.
     * @throws Exception If a database access error occurs.
     */
    public List<QuizRunQuestion> getQuizRunQuestions(int quizRunId) throws Exception {
        // Return quiz run questions for a quiz run
        return quizRunQuestionRepository.findByQuizRunId(quizRunId);
    }

    /**
     * Retrieves a subset of questions for a specific page in a quiz run.
     *
     * @param quizRunId The ID of the quiz run.
     * @param page The page number to retrieve questions for.
     * @param questionsPerPage The number of questions per page.
     * @return A List of QuizRunQuestion objects for the specified page.
     * @throws Exception If a database access error occurs.
     */
    public List<QuizRunQuestion> getQuestionsForPage(int quizRunId, int page, int questionsPerPage) throws Exception {
        // Get all questions for the quiz run
        List<QuizRunQuestion> allQuestions = quizRunQuestionRepository.findByQuizRunId(quizRunId);

        // Calculate start and end indices for the page
        int startIndex = (page - 1) * questionsPerPage;
        int endIndex = Math.min(startIndex + questionsPerPage, allQuestions.size());

        // Handle cases where the start index is out of bounds
        if (startIndex >= allQuestions.size()) {
            return new ArrayList<>();
        }

        // Return the sublist representing the questions for the page
        return allQuestions.subList(startIndex, endIndex);
    }

    /**
     * Retrieves a question by its ID.
     *
     * @param questionId The ID of the question to retrieve.
     * @return The Question object if found.
     * @throws Exception If a database access error occurs.
     */
    public Question getQuestionById(int questionId) throws Exception {
        // Return question by its ID
        return questionRepository.findById(questionId);
    }

    /**
     * Records the user's answer to a question in a quiz run.
     *
     * @param quizRunQuestionId The ID of the QuizRunQuestion being answered.
     * @param chosenOptionId The ID of the answer option chosen by the user.
     * @throws Exception If a database access error occurs.
     */
    public void answerQuestion(int quizRunQuestionId, int chosenOptionId) throws Exception {
        // Get the quiz run question
        QuizRunQuestion quizRunQuestion = quizRunQuestionRepository.findById(quizRunQuestionId);

        // Get the selected answer option
        AnswerOption chosenOption = answerOptionRepository.findById(chosenOptionId);

        // Update the quiz run question with the chosen option
        quizRunQuestionRepository.updateChosenOption(
                quizRunQuestionId,
                chosenOptionId,
                chosenOption.getIsCorrect()
        );
    }

    /**
     * Finalizes a quiz run, calculates the score, updates the database,
     * and updates the user's best result if necessary.
     *
     * @param quizRunId The ID of the quiz run to finalize.
     * @throws Exception If a database access error occurs.
     */
    public void finalizeQuiz(int quizRunId) throws Exception {
        // Get all questions for this quiz run
        List<QuizRunQuestion> quizRunQuestions = quizRunQuestionRepository.findByQuizRunId(quizRunId);

        // Count correct and wrong answers
        int correctCount = 0;
        int wrongCount = 0;

        for (QuizRunQuestion question : quizRunQuestions) {
            if (question.getChosenOptionId() != null) {
                if (question.getIsCorrect()) {
                    correctCount++;
                } else {
                    wrongCount++;
                }
            } else {
                wrongCount++; // Count unanswered questions as wrong
            }
        }

        // Get the quiz run
        QuizRun quizRun = quizRunRepository.findById(quizRunId);
        quizRun.setCorrectCount(correctCount);
        quizRun.setWrongCount(wrongCount);

        // Update the database
        updateQuizRunResults(quizRun);

        // Update user's best result if necessary
        User user = userRepository.findById(quizRun.getUserId());
        if (user != null && correctCount > user.getBestResult()) {
            user.setBestResult(correctCount);
            userRepository.updateBestResult(user.getUsername(), correctCount);
        }
    }

    /**
     * Updates the quiz run results (correct and wrong counts) in the database.
     *
     * @param quizRun The QuizRun object with the updated results.
     * @throws Exception If a database access error occurs.
     */
    private void updateQuizRunResults(QuizRun quizRun) throws Exception {
        // Update quiz run with the correct/wrong counts
        String sql = "UPDATE QuizRuns SET correct_count = ?, wrong_count = ? WHERE id = ?";
        try (java.sql.Connection conn = DbContext.getInstance().getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quizRun.getCorrectCount());
            ps.setInt(2, quizRun.getWrongCount());
            ps.setInt(3, quizRun.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Retrieves the quiz history for a specific user.
     *
     * @param userId The ID of the user.
     * @return A List of QuizRun objects representing the user's quiz history.
     * @throws Exception If a database access error occurs.
     */
    public List<QuizRun> getUserQuizHistory(int userId) throws Exception {
        // Return quiz history for a user
        return quizRunRepository.findByUserId(userId);
    }

    /**
     * Retrieves the best result (highest correct count) for a user.
     *
     * @param username The username of the user.
     * @return The user's best result (highest correct count).
     * @throws Exception If a database access error occurs.
     */
    public int getBestResult(String username) throws Exception {
        // Return best result for a user
        User user = userRepository.findByUsername(username);
        return user != null ? user.getBestResult() : 0;
    }

    /**
     * Retrieves a QuizRun by its ID.
     *
     * @param quizRunId The ID of the QuizRun to retrieve.
     * @return The QuizRun object if found, null otherwise.
     * @throws Exception If a database access error occurs.
     */
    public QuizRun getQuizRun(Integer quizRunId) throws Exception {
        return this.quizRunRepository.findById(quizRunId);
    }
}
