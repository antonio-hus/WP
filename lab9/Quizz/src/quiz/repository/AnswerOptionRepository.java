package quiz.repository;

import quiz.model.AnswerOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing AnswerOption data in the database.
 * Provides methods for finding answer options by ID and finding answer options by question ID.
 */
public class AnswerOptionRepository {

    /**
     * Retrieves an AnswerOption object from the database based on its ID.
     *
     * @param id The ID of the AnswerOption to retrieve.
     * @return An AnswerOption object if found, null otherwise.
     * @throws Exception If a database access error occurs.
     */
    public AnswerOption findById(int id) throws Exception {
        String sql = "SELECT id, question_id, option_label, option_text, is_correct FROM QuizQuestionsAnswerOptions WHERE id = ?";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the ID parameter
            ps.setInt(1, id);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {

                // If a result is found, map it to an AnswerOption object
                if (rs.next()) {
                    return mapRow(rs);
                }

                // Return null if no answer option is found with the given ID
                return null;
            }
        }
    }

    /**
     * Retrieves a list of AnswerOption objects from the database based on the question ID.
     *
     * @param questionId The ID of the question to retrieve answer options for.
     * @return A List of AnswerOption objects for the given question ID, or an empty list if none are found.
     * @throws Exception If a database access error occurs.
     */
    public List<AnswerOption> findByQuestionId(int questionId) throws Exception {
        String sql = "SELECT id, question_id, option_label, option_text, is_correct " +
                "FROM QuizQuestionsAnswerOptions WHERE question_id = ?";

        // Create a list to store the results
        List<AnswerOption> list = new ArrayList<>();

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the question ID parameter
            ps.setInt(1, questionId);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {

                // Iterate over the result set and map each row to an AnswerOption object
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }

        // Return the list of answer options
        return list;
    }

    /**
     * Maps a row from the ResultSet to an AnswerOption object.
     * This method encapsulates the logic of extracting data from the ResultSet
     * and creating an AnswerOption object.
     *
     * @param rs The ResultSet containing the data for a single AnswerOption.
     * @return An AnswerOption object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    private AnswerOption mapRow(ResultSet rs) throws SQLException {
        return new AnswerOption(
                rs.getInt("id"),
                rs.getInt("question_id"),
                rs.getString("option_label"),
                rs.getString("option_text"),
                rs.getBoolean("is_correct")
        );
    }
}
