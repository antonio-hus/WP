package quiz.repository;

import quiz.model.QuizRun;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing QuizRun data in the database.
 * Provides methods for finding quiz runs by ID, user ID, and inserting new quiz runs.
 */
public class QuizRunRepository {

    /**
     * Retrieves a QuizRun object from the database based on its ID.
     *
     * @param id The ID of the QuizRun to retrieve.
     * @return A QuizRun object if found, null otherwise.
     * @throws Exception If a database access error occurs.
     */
    public QuizRun findById(int id) throws Exception {
        String sql = "SELECT id, user_id, run_date, total_questions, questions_per_page, correct_count, wrong_count " +
                "FROM QuizRuns WHERE id = ?";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the ID parameter in the prepared statement
            ps.setInt(1, id);

            // Execute the query and get the result set
            try (ResultSet rs = ps.executeQuery()) {

                // If a result is found, map it to a QuizRun object
                if (rs.next()) return mapRow(rs);

                // Return null if no QuizRun is found with the given ID
                return null;
            }
        }
    }

    /**
     * Retrieves a list of QuizRun objects from the database based on the user ID.
     * The results are ordered by run_date in descending order (most recent first).
     *
     * @param userId The ID of the user to retrieve QuizRuns for.
     * @return A List of QuizRun objects for the given user ID, or an empty list if none are found.
     * @throws Exception If a database access error occurs.
     */
    public List<QuizRun> findByUserId(int userId) throws Exception {
        String sql = "SELECT id, user_id, run_date, total_questions, questions_per_page, correct_count, wrong_count " +
                "FROM QuizRuns WHERE user_id = ? ORDER BY run_date DESC";

        // Create a new list to store the results
        List<QuizRun> list = new ArrayList<>();

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the user ID parameter
            ps.setInt(1, userId);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {

                // Map each row to a QuizRun object and add it to the list
                while (rs.next()) list.add(mapRow(rs));
            }
        }

        // Return the list of QuizRun objects
        return list;
    }

    /**
     * Inserts a new QuizRun record into the database.  Also retrieves the generated ID.
     *
     * @param run The QuizRun object containing the data for the new record.
     * @return True if the insertion was successful, false otherwise.
     * @throws Exception If a database access error occurs.
     */
    public boolean insert(QuizRun run) throws Exception {
        String sql = "INSERT INTO QuizRuns " +
                "(user_id, total_questions, questions_per_page, correct_count, wrong_count) VALUES (?,?,?,?,?)";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();

             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, run.getUserId());
            ps.setInt(2, run.getTotalQuestions());
            ps.setInt(3, run.getQuestionsPerPage());
            ps.setInt(4, run.getCorrectCount());
            ps.setInt(5, run.getWrongCount());

            // Execute the insert query
            int affected = ps.executeUpdate();

            // If no rows were affected, the insertion failed
            if (affected == 0) return false;

            // Get the generated keys
            try (ResultSet keys = ps.getGeneratedKeys()) {

                // Retrieve the ID and set it on the QuizRun object
                if (keys.next()) run.setId(keys.getInt(1));
            }

            // Return true if the insertion was successful
            return true;
        }
    }

    /**
     * Maps a row from the ResultSet to a QuizRun object.
     * This method encapsulates the logic of extracting data from the ResultSet
     * and creating a QuizRun object.
     *
     * @param rs The ResultSet containing the data for a single QuizRun.
     * @return A QuizRun object populated with the data from the ResultSet.
     * @throws SQLException If an error occurs while accessing the ResultSet.
     */
    private QuizRun mapRow(ResultSet rs) throws SQLException {
        return new QuizRun(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getTimestamp("run_date").toLocalDateTime(),
                rs.getInt("total_questions"),
                rs.getInt("questions_per_page"),
                rs.getInt("correct_count"),
                rs.getInt("wrong_count")
        );
    }
}
