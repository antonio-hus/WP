package quiz.controller;

import quiz.model.*;
import quiz.service.QuizService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet to handle the quiz taking process.
 * This servlet manages the display of quiz questions, processing of user answers,
 * and navigation between quiz pages.
 */
@WebServlet("/protected/quiz")
public class QuizServlet extends HttpServlet {

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
     * Handles the HTTP GET request to display the quiz questions.
     *
     * @param req  The HttpServletRequest object representing the client's request.
     * @param resp The HttpServletResponse object representing the server's response.
     * @throws ServletException If an error occurs during the execution of the servlet.
     * @throws IOException      If an I/O error occurs during the handling of the request.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get user, quiz run, and current page from the session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        QuizRun quizRun = (QuizRun) session.getAttribute("currentQuizRun");
        Integer currentPage = (Integer) session.getAttribute("currentQuizPage");

        // Redirect to home if user, quiz run, or page is not in session
        if (user == null || quizRun == null || currentPage == null) {
            resp.sendRedirect(req.getContextPath() + "/protected/home.jsp");
            return;
        }

        try {
            // Get questions for the current page
            List<QuizRunQuestion> pageQuestions = quizService.getQuestionsForPage(
                    quizRun.getId(),
                    currentPage,
                    quizRun.getQuestionsPerPage()
            );

            // If there are no questions on this page, the quiz is complete
            if (pageQuestions.isEmpty()) {
                // Finalize the quiz and redirect to the results page
                quizService.finalizeQuiz(quizRun.getId());
                resp.sendRedirect(req.getContextPath() + "/protected/results.jsp?id=" + quizRun.getId());
                return;
            }

            // Prepare question data for the view
            List<Map<String, Object>> questionDataList = new ArrayList<>();
            for (QuizRunQuestion runQuestion : pageQuestions) {
                Map<String, Object> questionData = new HashMap<>();
                Question question = quizService.getQuestionById(runQuestion.getQuestionId());
                List<AnswerOption> options = quizService.getAnswerOptionsForQuestion(question.getId());

                questionData.put("runQuestionId", runQuestion.getId());
                questionData.put("question", question);
                questionData.put("options", options);
                questionData.put("selectedOptionId", runQuestion.getChosenOptionId()); // added selected option

                questionDataList.add(questionData);
            }

            // Calculate total pages
            int totalQuestions = quizRun.getTotalQuestions();
            int questionsPerPage = quizRun.getQuestionsPerPage();
            int totalPages = (int) Math.ceil((double) totalQuestions / questionsPerPage);

            // Set attributes for the view
            req.setAttribute("questionDataList", questionDataList);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("quizRunId", quizRun.getId());

            // Forward to the quiz page
            req.getRequestDispatcher("/protected/quiz.jsp").forward(req, resp);

        } catch (Exception e) {
            // Handle exceptions during quiz question loading
            throw new ServletException("Error loading quiz questions", e);
        }
    }

    /**
     * Handles the HTTP POST request to process user's answers and navigate between quiz pages.
     *
     * @param req  The HttpServletRequest object representing the client's request.
     * @param resp The HttpServletResponse object representing the server's response.
     * @throws ServletException If an error occurs during the execution of the servlet.
     * @throws IOException      If an I/O error occurs during the handling of the request.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get user, quiz run, and current page from the session
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        QuizRun quizRun = (QuizRun) session.getAttribute("currentQuizRun");
        Integer currentPage = (Integer) session.getAttribute("currentQuizPage");

        // Redirect to home if user, quiz run, or page is not in session
        if (user == null || quizRun == null || currentPage == null) {
            resp.sendRedirect(req.getContextPath() + "/protected/home.jsp");
            return;
        }

        try {
            // Process the submitted answers
            String[] runQuestionIds = req.getParameterValues("runQuestionId");
            if (runQuestionIds != null) {
                for (String runQuestionIdStr : runQuestionIds) {
                    int runQuestionId = Integer.parseInt(runQuestionIdStr);
                    String chosenOptionIdStr = req.getParameter("answer_" + runQuestionId);

                    if (chosenOptionIdStr != null && !chosenOptionIdStr.isEmpty()) {
                        int chosenOptionId = Integer.parseInt(chosenOptionIdStr);
                        quizService.answerQuestion(runQuestionId, chosenOptionId);
                    }
                }
            }

            // Determine the next action based on button clicked
            String action = req.getParameter("action");
            if ("next".equals(action)) {

                // Go to the next page
                session.setAttribute("currentQuizPage", currentPage + 1);
            } else if ("previous".equals(action) && currentPage > 1) {

                // Go to the previous page
                session.setAttribute("currentQuizPage", currentPage - 1);
            } else if ("finish".equals(action)) {

                // Finalize the quiz
                quizService.finalizeQuiz(quizRun.getId());
                resp.sendRedirect(req.getContextPath() + "/protected/results?id=" + quizRun.getId());
                return;
            }

            // Redirect back to the quiz page
            resp.sendRedirect(req.getContextPath() + "/protected/quiz");

        } catch (Exception e) {
            // Handle exceptions during answer processing or page navigation
            throw new ServletException("Error processing quiz answers", e);
        }
    }
}
