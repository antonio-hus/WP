<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="quiz.model.*, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz Results</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background: #f4f4f4;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
            box-sizing: border-box;
        }

        .result-page {
            background: #fff;
            padding: 20px;
            width: 90%;
            max-width: 600px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            box-sizing: border-box;
            text-align: center;
        }

        h1 {
            margin-top: 0;
            color: #007bff;
        }

        .score {
            font-size: 48px;
            font-weight: bold;
            margin: 20px 0;
        }
        .good { color: #27ae60; }
        .average { color: #f39c12; }
        .poor { color: #e74c3c; }

        .stats {
            display: flex;
            justify-content: space-around;
            margin-bottom: 20px;
            flex-wrap: wrap;
        }
        .stat-item {
            flex: 1 1 30%;
            margin: 10px 0;
        }
        .stat-item h3 {
            margin-bottom: 5px;
            color: #2c3e50;
            font-size: 16px;
        }
        .stat-item div {
            font-size: 24px;
            color: #34495e;
        }

        .best-result {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 6px;
            background: #f9f9f9;
            text-align: left;
            margin-bottom: 20px;
        }
        .best-result h2 {
            margin-top: 0;
            color: #007bff;
            font-size: 20px;
        }
        .best-result p {
            margin: 8px 0;
            color: #2c3e50;
        }
        .new-best { color: #2e7d32; font-weight: bold; }
        .tied-best { color: #1565c0; font-weight: bold; }
        .keep-going { color: #c62828; }

        .actions {
            text-align: center;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
<%
    QuizRun quizRun = (QuizRun) request.getAttribute("quizRun");
    Integer bestResult = (Integer) request.getAttribute("bestResult");
    if (quizRun == null) {
        response.sendRedirect(request.getContextPath() + "/protected/home.jsp");
        return;
    }
    int totalQuestions = quizRun.getTotalQuestions();
    int correctAnswers = quizRun.getCorrectCount();
    int wrongAnswers = quizRun.getWrongCount();
    double percentage = (double) correctAnswers / totalQuestions * 100;
    String cls = percentage >= 80 ? "good" : percentage >= 60 ? "average" : "poor";
    int previousBest = bestResult != null ? bestResult : 0;
%>
<div class="result-page">
    <h1>Quiz Results</h1>
    <div class="score <%= cls %>">
        <%= String.format("%.1f%%", percentage) %>
    </div>

    <div class="stats">
        <div class="stat-item">
            <h3>Total Questions</h3>
            <div><%= totalQuestions %></div>
        </div>
        <div class="stat-item">
            <h3>Correct Answers</h3>
            <div><%= correctAnswers %></div>
        </div>
        <div class="stat-item">
            <h3>Wrong Answers</h3>
            <div><%= wrongAnswers %></div>
        </div>
    </div>

    <div class="best-result">
        <h2>Quiz Summary</h2>
        <p>You scored <strong><%= correctAnswers %></strong> correct answer<%= (correctAnswers==1?"":"s") %>.</p>
        <p>Your previous best was <strong><%= previousBest %></strong> correct answer<%= (previousBest==1?"":"s") %>.</p>
        <% if (correctAnswers > previousBest) { %>
        <p class="new-best">🎉 Congratulations! This is your new best result!</p>
        <% } else if (correctAnswers == previousBest) { %>
        <p class="tied-best">👍 You’ve tied your best score—well done!</p>
        <% } else { %>
        <p class="keep-going">👟 Keep going—you’ll beat your best next time!</p>
        <% } %>
    </div>

    <div class="actions">
        <a href="<%= request.getContextPath() %>/protected/home.jsp" class="btn">Back to Home</a>
    </div>
</div>
</body>
</html>
