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
 * Servlet to handle the starting of a new quiz.
 * This servlet is responsible for processing the request to begin a quiz,
 * validating the input, creating a new QuizRun, and redirecting the user
 * to the quiz page.
 */
@WebServlet("/protected/startQuiz")
public class StartQuizServlet extends HttpServlet {

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
     * Handles the HTTP POST request to start a new quiz.
     *
     * @param req  The HttpServletRequest object representing the client's request.
     * @param resp The HttpServletResponse object representing the server's response.
     * @throws ServletException If an error occurs during the execution of the servlet.
     * @throws IOException      If an I/O error occurs during the handling of the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get the user from the session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        // Redirect to login if the user is not logged in
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            // Get quiz configuration parameters from the request
            int totalQuestions = Integer.parseInt(req.getParameter("totalQuestions"));
            int questionsPerPage = Integer.parseInt(req.getParameter("questionsPerPage"));

            // Validate input parameters
            if (totalQuestions < 1 || questionsPerPage < 1) {
                req.setAttribute("error", "Invalid quiz configuration");
                req.getRequestDispatcher("/protected/home.jsp").forward(req, resp);
                return;
            }

            // Start a new quiz run using the QuizService
            QuizRun quizRun = quizService.startNewQuiz(
                    user.getId(),
                    totalQuestions,
                    questionsPerPage
            );

            // Store the quiz run and current page in the session
            session.setAttribute("currentQuizRun", quizRun);
            session.setAttribute("currentQuizPage", 1);

            // Redirect the user to the quiz page
            resp.sendRedirect(req.getContextPath() + "/protected/quiz");

        } catch (NumberFormatException e) {

            // Handle invalid number format in the input parameters
            req.setAttribute("error", "Invalid input. Please enter valid numbers.");
            req.getRequestDispatcher("/protected/home.jsp").forward(req, resp);
        } catch (Exception e) {

            // Handle any other exceptions that might occur during quiz start
            throw new ServletException("Failed to start quiz", e);
        }
    }
}
