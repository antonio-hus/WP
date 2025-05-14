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

/**
 * Servlet to handle the display of quiz results.
 * This servlet retrieves the quiz results for a specific quiz run and
 * displays them to the user.  It also retrieves the user's best result.
 */
@WebServlet("/protected/results")
public class QuizResultsServlet extends HttpServlet {

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
     * Handles the HTTP GET request to display the quiz results.
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
            // Get the quiz run ID from the request parameter
            String quizRunIdStr = req.getParameter("id");
            if (quizRunIdStr == null || quizRunIdStr.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/protected/home.jsp");
                return;
            }

            // Parse the quiz run ID
            int quizRunId = Integer.parseInt(quizRunIdStr);

            // Retrieve the quiz run from the database
            QuizRun quizRun = quizService.getQuizRun(quizRunId);

            // Redirect to home if the quiz run is not found
            if (quizRun == null) {
                resp.sendRedirect(req.getContextPath() + "/protected/home.jsp");
                return;
            }

            // Get the user's best result
            int bestResult = quizService.getBestResult(user.getUsername());

            // Set attributes for the view
            req.setAttribute("quizRun", quizRun);
            req.setAttribute("bestResult", bestResult);

            // Forward to the results page
            req.getRequestDispatcher("/protected/results.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle exceptions during quiz results retrieval
            throw new ServletException("Error retrieving quiz results", e);
        }
    }
}
