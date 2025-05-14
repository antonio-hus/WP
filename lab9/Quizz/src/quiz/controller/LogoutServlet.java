package quiz.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet to handle user logout.
 * This servlet invalidates the user's session and redirects them to the login page.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Handles the HTTP GET request to log out a user.
     *
     * @param req  The HttpServletRequest object representing the client's request.
     * @param resp The HttpServletResponse object representing the server's response.
     * @throws IOException If an I/O error occurs during the handling of the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Get the current session, and prevent creating a new one if it doesn't exist.
        HttpSession session = req.getSession(false);

        // Invalidate the session if it exists
        if (session != null) {
            session.invalidate();
        }

        // Redirect the user to the login page
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
