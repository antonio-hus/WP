package quiz.controller;

import quiz.service.AuthenticationService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet to handle user registration.
 * This servlet is responsible for processing the registration request,
 * validating the input, creating a new user account, and redirecting
 * the user to the login page upon successful registration.
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private AuthenticationService authService;

    /**
     * Initializes the servlet by creating an instance of the AuthenticationService.
     *
     * @throws ServletException If an error occurs during AuthenticationService initialization.
     */
    @Override
    public void init() throws ServletException {
        try {
            authService = new AuthenticationService();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize AuthenticationService", e);
        }
    }

    /**
     * Handles the HTTP GET request for the registration page.
     * Forwards the request to the register.jsp page.
     *
     * @param req  The HttpServletRequest object representing the client's request.
     * @param resp The HttpServletResponse object representing the server's response.
     * @throws ServletException If an error occurs during the execution of the servlet.
     * @throws IOException      If an I/O error occurs during the handling of the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    /**
     * Handles the HTTP POST request to register a new user.
     *
     * @param req  The HttpServletRequest object representing the client's request.
     * @param resp The HttpServletResponse object representing the server's response.
     * @throws ServletException If an error occurs during the execution of the servlet.
     * @throws IOException      If an I/O error occurs during the handling of the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get username and password from the request parameters
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Validate that username and password are not null or empty
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            req.setAttribute("error", "Username and password are required.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        try {
            // Register the user using the AuthenticationService
            authService.register(username, password);

            // Redirect to the login page after successful registration
            resp.sendRedirect(req.getContextPath() + "/login");

        } catch (IllegalArgumentException e) {

            // Handle the case where the username already exists
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } catch (Exception e) {

            // Handle any other exceptions that might occur during registration
            throw new ServletException("Registration failed", e);
        }
    }
}
