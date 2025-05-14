package quiz.controller;

import quiz.model.QuizRun;
import quiz.model.User;
import quiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to handle the display of a user's quiz history.
 * This servlet retrieves the quiz runs for a specific user and displays them.
 */
@WebServlet("/protected/history")
public class QuizHistoryServlet extends HttpServlet {

    private QuizService quizService;

    /**
     * Initializes the servlet by creating an instance of the QuizService.
     *
     * @throws ServletException If an error occurs during QuizService initialization.
     */
    @Override
    public void init() throws ServletException {
        try {
            quizService = new QuizService();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize QuizService", e);
        }
    }

    /**
     * Handles the HTTP GET request to display the user's quiz history.
     *
     * @param req  The HttpServletRequest object representing the client's request.
     * @param resp The HttpServletResponse object representing the server's response.
     * @throws ServletException If an error occurs during the execution of the servlet.
     * @throws IOException      If an I/O error occurs during the handling of the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get user from the session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Redirect to login if the user is not logged in
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            // Get quiz history for the user
            List<QuizRun> quizHistory = quizService.getUserQuizHistory(user.getId());

            // Set attributes for the view
            req.setAttribute("quizHistory", quizHistory);

            // Forward to the history page
            req.getRequestDispatcher("/protected/history.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle exceptions during quiz history retrieval
            throw new ServletException("Error retrieving quiz history", e);
        }
    }
}
