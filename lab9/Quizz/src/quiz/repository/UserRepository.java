package quiz.repository;

import quiz.model.User;
import java.sql.*;

/**
 * Repository class for managing User data in the database.
 * Provides methods for finding users, creating new users, and updating user information.
 */
public class UserRepository {

    /**
     * Retrieves a User object from the database based on the provided user ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A User object if found, null otherwise.
     * @throws Exception If a database access error occurs.
     */
    public User findById(int id) throws Exception {
        String sql = "SELECT id, username, password_hash, best_result FROM QuizUsers WHERE id = ?";

        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getInt("best_result")
                    );
                }
                return null;
            }
        }
    }

    /**
     * Retrieves a User object from the database based on the provided username.
     *
     * @param username The username of the user to retrieve.
     * @return A User object if found, null otherwise.
     * @throws Exception If a database access error occurs.
     */
    public User findByUsername(String username) throws Exception {
        String sql = "SELECT id, username, password_hash, best_result FROM QuizUsers WHERE username = ?";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            // Execute the query and get the result set
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    // If a user is found, create a User object from the result set data
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password_hash"),
                            rs.getInt("best_result")
                    );
                }

                // Return null if no user is found
                return null;
            }
        }
    }

    /**
     * Creates a new user in the database.
     *
     * @param user The User object containing the user's data to be inserted.
     * @return The created user with the database-generated ID, or null if creation failed.
     * @throws Exception If a database access error occurs.
     */
    public User createUser(User user) throws Exception {
        String sql = "INSERT INTO QuizUsers (username, password_hash, best_result) VALUES (?, ?, ?)";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setInt(3, user.getBestResult() != null ? user.getBestResult() : 0);

            // Execute the update
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {

                // No rows affected means insert failed
                return null;
            }

            // Get the auto-generated ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    return user;
                } else {

                    // No ID means something went wrong
                    return null;
                }
            }
        }
    }

    /**
     * Updates the best result for a user in the database.
     *
     * @param username      The username of the user whose best result needs to be updated.
     * @param newBestResult The new best result to be stored for the user.
     * @return True if the best result was updated successfully, false otherwise.
     * @throws Exception If a database access error occurs.
     */
    public boolean updateBestResult(String username, int newBestResult) throws Exception {
        String sql = "UPDATE QuizUsers SET best_result = ? WHERE username = ?";

        // Use try-with-resources to automatically close the connection and statement
        try (Connection conn = DbContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newBestResult);
            ps.setString(2, username);

            // Execute the update and return true if successful
            return ps.executeUpdate() > 0;
        }
    }
}
