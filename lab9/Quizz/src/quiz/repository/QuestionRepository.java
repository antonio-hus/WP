package quiz.repository;

import quiz.model.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Question data in the database.
 * Provides methods for finding questions by ID and finding all questions.
 */
public class QuestionRepository {

    /**
     * Retrieves a Question object from the database based on its ID.
     *
     * @param id The ID of the Question to retrieve.
     * @return A Question object if found, null otherwise.
     * @throws Exception If a database access error occurs.
     */
    public Question findById(int id) throws Exception {
        String sql = "SELECT id, question FROM QuizQuestions WHERE id = ?";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set the ID parameter
            ps.setInt(1, id);

            // Execute the query
            try (ResultSet rs = ps.executeQuery()) {

                // If a result is found, map it to a Question object
                if (rs.next()) {
                    return new Question(rs.getInt("id"), rs.getString("question"));
                }

                // Return null if no question is found with the given ID
                return null;
            }
        }
    }

    /**
     * Retrieves all Question objects from the database.
     *
     * @return A List of Question objects, or an empty list if no questions exist.
     * @throws Exception If a database access error occurs.
     */
    public List<Question> findAll() throws Exception {
        String sql = "SELECT id, question FROM QuizQuestions";

        // Create a list to store the results
        List<Question> list = new ArrayList<>();

        // Use try-with-resources to automatically close the connection, statement, and result set
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Iterate over the result set and create a Question object for each row
            while (rs.next()) {
                list.add(new Question(rs.getInt("id"), rs.getString("question")));
            }
        }

        // Return the list of questions
        return list;
    }
}
