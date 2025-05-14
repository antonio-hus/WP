package quiz.repository;

import quiz.model.QuizRunQuestion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing QuizRunQuestion data in the database.
 * Provides methods for finding quiz run questions by ID, quiz run ID, inserting new quiz run questions,
 * and updating chosen options for quiz run questions.
 */
public class QuizRunQuestionRepository {

    /**
     * Retrieves a QuizRunQuestion object from the database based on its ID.
     *
     * @param id The ID of the QuizRunQuestion to retrieve.
     * @return A QuizRunQuestion object if found, null otherwise.
     * @throws Exception If a database access error occurs.
     */
    public QuizRunQuestion findById(int id) throws Exception {
        String sql = "SELECT id, quiz_run_id, question_id, chosen_option_id, is_correct " +
                "FROM QuizRunQuestions WHERE id = ?";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the ID parameter
            ps.setInt(1, id);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {

                // If a result is found, map it to a QuizRunQuestion object
                if (rs.next()) return mapRow(rs);

                // Return null if no matching record is found
                return null;
            }
        }
    }

    /**
     * Retrieves a list of QuizRunQuestion objects from the database based on the quiz run ID.
     *
     * @param quizRunId The ID of the quiz run to retrieve questions for.
     * @return A List of QuizRunQuestion objects for the given quiz run ID.  Returns an empty list if no matching questions are found.
     * @throws Exception If a database access error occurs.
     */
    public List<QuizRunQuestion> findByQuizRunId(int quizRunId) throws Exception {
        String sql = "SELECT id, quiz_run_id, question_id, chosen_option_id, is_correct " +
                "FROM QuizRunQuestions WHERE quiz_run_id = ?";

        // Create a list to store the results
        List<QuizRunQuestion> list = new ArrayList<>();

        // Use try-with-resources
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the quiz run ID parameter
            ps.setInt(1, quizRunId);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {

                // Map each row and add to the list
                while (rs.next()) list.add(mapRow(rs));
            }
        }

        // Return the populated list
        return list;
    }

    /**
     * Inserts a new QuizRunQuestion record into the database.  Also retrieves the generated ID.
     *
     * @param qrq The QuizRunQuestion object containing the data for the new record.
     * @return True if the insertion was successful, false otherwise.
     * @throws Exception If a database access error occurs.
     */
    public boolean insert(QuizRunQuestion qrq) throws Exception {
        String sql = "INSERT INTO QuizRunQuestions " +
                "(quiz_run_id, question_id, chosen_option_id, is_correct) VALUES (?,?,?,?)";

        // Use try-with-resources
        try (Connection conn = DbContext.getInstance().getConnection();

             // Use RETURN_GENERATED_KEYS to get the generated ID
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set quiz run ID
            ps.setInt(1, qrq.getQuizRunId());

            // Set question ID
            ps.setInt(2, qrq.getQuestionId());

            // Handle the case where chosen_option_id might be null
            if (qrq.getChosenOptionId() != null) {
                ps.setInt(3, qrq.getChosenOptionId());
            } else {

                // Set to null in the database
                ps.setNull(3, Types.INTEGER);
            }

            // Set is_correct
            ps.setBoolean(4, qrq.getIsCorrect());

            // Execute the insert
            int affected = ps.executeUpdate();

            // If no rows affected, insertion failed
            if (affected == 0) return false;

            // Get generated keys
            try (ResultSet keys = ps.getGeneratedKeys()) {

                // Retrieve and set the generated ID
                if (keys.next()) qrq.setId(keys.getInt(1));
            }

            // Return true on success
            return true;
        }
    }

    /**
     * Updates the chosen option and correctness status for a QuizRunQuestion in the database.
     *
     * @param id             The ID of the QuizRunQuestion to update.
     * @param chosenOptionId The ID of the chosen answer option.  May be null.
     * @param isCorrect      The correctness status (true/false) of the chosen option.
     * @return True if the update was successful, false otherwise.
     * @throws Exception If a database access error occurs.
     */
    public boolean updateChosenOption(int id, Integer chosenOptionId, boolean isCorrect) throws Exception {
        String sql = "UPDATE QuizRunQuestions SET chosen_option_id = ?, is_correct = ? WHERE id = ?";

        // Use try-with-resources
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Handle the case where chosen_option_id might be null
            if (chosenOptionId != null) {
                ps.setInt(1, chosenOptionId);
            } else {

                // Set to null in the database
                ps.setNull(1, Types.INTEGER);
            }

            // Set is_correct
            ps.setBoolean(2, isCorrect);

            // Set the ID for the WHERE clause
            ps.setInt(3, id);

            // Execute the update and return success status
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Maps a row from the ResultSet to a QuizRunQuestion object.
     * This method encapsulates the logic of extracting data from the ResultSet
     * and creating a QuizRunQuestion object.
     *
     * @param rs The ResultSet containing the data for a single QuizRunQuestion.
     * @return A QuizRunQuestion object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    private QuizRunQuestion mapRow(ResultSet rs) throws SQLException {

        // Handle the possibility of a null chosen_option_id
        Integer chosenOptionId = rs.getObject("chosen_option_id") != null ? rs.getInt("chosen_option_id") : null;
        return new QuizRunQuestion(
                rs.getInt("id"),
                rs.getInt("quiz_run_id"),
                rs.getInt("question_id"),
                chosenOptionId,
                rs.getBoolean("is_correct")
        );
    }
}
