<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="quiz.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quiz Home</title>
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
            background-color: #f44336;
            transition: background-color 0.3s ease;
        }
        .header a:hover {
            background-color: #d32f2f;
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
        .container h2 {
            margin-top: 0;
            color: #007bff;
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
            display: flex;
            flex-direction: column;
        }
        .form-group label {
            margin-bottom: 5px;
            color: #34495e;
            font-size: 16px;
        }
        .form-group input[type="number"] {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button[type="submit"] {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button[type="submit"]:hover {
            background-color: #0056b3;
        }

        .container a {
            display: inline-block;
            margin-top: 10px;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .container a:hover {
            background-color: #0056b3;
        }

        .best-result {
            text-align: center;
            font-size: 16px;
            color: #2c3e50;
            margin-bottom: 10px;
        }
        .best-result strong {
            color: #27ae60;
        }

        .actions {
            margin-top: 10px;
        }
        .actions .btn {
            display: block;
            width: 100%;
            text-align: center;
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
%>
<div class="header">
    <h1>Welcome, <%= user.getUsername() %></h1>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</div>

<div class="container">
    <h2>Configure Your Quiz</h2>
    <form action="${pageContext.request.contextPath}/protected/startQuiz" method="post">
        <div class="form-group">
            <label for="totalQuestions">Number of Questions:</label>
            <input type="number" id="totalQuestions" name="totalQuestions" min="1" max="20" value="10" required>
        </div>
        <div class="form-group">
            <label for="questionsPerPage">Questions Per Page:</label>
            <input type="number" id="questionsPerPage" name="questionsPerPage" min="1" max="10" value="5" required>
        </div>
        <button type="submit">Start Quiz</button>
    </form>
</div>

<div class="container">
    <h2>Your Quiz History</h2>
    <div class="best-result">
        Your best result: <strong><%= user.getBestResult() %></strong> correct answers
    </div>
    <div class="actions">
        <a href="${pageContext.request.contextPath}/protected/history"
           class="btn btn-primary">
            View Quiz History
        </a>
    </div>

</div>
</body>
</html>
