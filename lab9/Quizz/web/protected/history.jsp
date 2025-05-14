<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="quiz.model.*, quiz.service.QuizService, java.util.*, java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz History</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            min-height: 100vh;
            box-sizing: border-box;
        }

        .header {
            background-color: #007bff;
            color: #fff;
            padding: 15px 20px;
            width: 100%;
            box-sizing: border-box;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            margin: 0;
            font-size: 20px;
        }
        .header a {
            color: #fff;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 4px;
            background-color: #6c757d;
            transition: background-color 0.3s ease;
        }
        .header a:hover {
            background-color: #5a6268;
        }

        .container {
            background: #fff;
            padding: 20px;
            margin-top: 20px;
            width: 90%;
            max-width: 800px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            box-sizing: border-box;
        }

        .no-history {
            text-align: center;
            padding: 30px;
            background: #fff;
            border-radius: 8px;
            color: #7f8c8d;
            font-size: 16px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .no-history h3 {
            color: #007bff;
            margin-bottom: 10px;
            font-size: 20px;
        }
        .no-history p {
            margin: 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background: #f2f2f2;
            font-weight: bold;
            color: #2c3e50;
        }
        tr:nth-child(even) {
            background: #f9f9f9;
        }
        tr:hover {
            background: #e0f7fa;
        }

        .score-cell {
            font-weight: bold;
            text-align: center;
        }
        .good {
            color: #27ae60;
        }
        .average {
            color: #f39c12;
        }
        .poor {
            color: #e74c3c;
        }
    </style>
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    QuizService quizService = new QuizService();
    List<QuizRun> quizHistory = quizService.getUserQuizHistory(user.getId());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
%>

<div class="header">
    <h1>Quiz History</h1>
    <a href="${pageContext.request.contextPath}/protected/home.jsp">Back to Home</a>
</div>

<div class="container">
    <% if (quizHistory.isEmpty()) { %>
    <div class="no-history">
        <h3>You haven't taken any quizzes yet!</h3>
        <p>Go back to the home page to start a quiz.</p>
    </div>
    <% } else { %>
    <table>
        <thead>
        <tr>
            <th>Date</th>
            <th>Total Questions</th>
            <th>Score</th>
        </tr>
        </thead>
        <tbody>
        <% for (QuizRun qr : quizHistory) {
            int total = qr.getTotalQuestions();
            int correct = qr.getCorrectCount();
            double pct = (double) correct / total * 100;
            String cls = pct >= 80 ? "good" : pct >= 60 ? "average" : "poor";
        %>
        <tr>
            <td><%= qr.getRunDate().format(formatter) %></td>
            <td><%= total %></td>
            <td class="score-cell <%= cls %>">
                <%= correct %> / <%= total %> (<%= String.format("%.1f%%", pct) %>)
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>
</body>
</html>
