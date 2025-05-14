package quiz.service;

import quiz.model.User;
import quiz.repository.UserRepository;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Service class for handling user authentication and registration.
 * Provides methods for logging in, registering new users, and password hashing.
 */
public class AuthenticationService {

    private final UserRepository userRepository;

    /**
     * Constructor for AuthenticationService.
     * Initializes the UserRepository.
     *
     * @throws Exception If an error occurs during UserRepository initialization.
     */
    public AuthenticationService() throws Exception {
        this.userRepository = new UserRepository();
    }

    /**
     * Attempts to log in a user with the given username and password.
     *
     * @param username The username of the user trying to log in.
     * @param password The password provided by the user.
     * @return The User object if the login is successful, null otherwise.
     * @throws Exception If a database or hashing error occurs.
     */
    public User login(String username, String password) throws Exception {
        // Retrieve the user from the database by username
        User user = userRepository.findByUsername(username);

        // If the user exists and the password matches the stored hash, return the user
        if (user != null && verifyPassword(password, user.getPasswordHash())) {
            return user;
        }

        // Return null if the login fails
        return null;
    }

    /**
     * Registers a new user with the given username and password.
     *
     * @param username The username for the new user.
     * @param password The password for the new user.
     * @throws Exception If a database or hashing error occurs, or if the username already exists.
     */
    public User register(String username, String password) throws Exception {
        // Check if the username already exists
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        // Hash the password for secure storage
        String hash = hashPassword(password);

        // Create a new User object with initial best result of 0
        User newUser = new User(username, hash, 0);

        // Store the new user in the database and get the updated user with ID
        User createdUser = userRepository.createUser(newUser);

        if (createdUser == null) {
            throw new Exception("Failed to create user in database.");
        }

        return createdUser;
    }

    /**
     * Verifies if the provided password matches the stored hash for a user.
     *
     * @param password The password to verify.
     * @param storedHash The stored hash to compare against.
     * @return True if the password matches the hash, false otherwise.
     * @throws Exception If a hashing error occurs.
     */
    private boolean verifyPassword(String password, String storedHash) throws Exception {
        // Hash the provided password and compare it to the stored hash
        return hashPassword(password).equals(storedHash);
    }

    /**
     * Hashes the given password using the SHA-256 algorithm.
     *
     * @param password The password to hash.
     * @return The hashed password as a hexadecimal string.
     * @throws Exception If the hashing algorithm is not available.
     */
    private String hashPassword(String password) throws Exception {
        // Get the SHA-256 message digest instance
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Calculate the hash of the password
        byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // Convert the byte array to a hexadecimal string representation
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }

        // Return the hexadecimal string
        return sb.toString();
    }
}
