<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, quiz.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Take Quiz</title>
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
        form {
            background: #fff;
            padding: 20px;
            width: 90%;
            max-width: 700px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #007bff;
            margin-top: 0;
        }
        .pagination {
            text-align: center;
            margin-bottom: 20px;
            font-size: 16px;
            color: #555;
        }

        .question-container {
            margin-bottom: 25px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background: #f9f9f9;
        }
        .question {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 12px;
            color: #2c3e50;
        }
        .options {
            margin-left: 20px;
        }
        .option {
            display: flex;
            align-items: center;
            margin-bottom: 8px;
        }
        .option input[type="radio"] {
            margin-right: 10px;
            cursor: pointer;
        }
        .option label {
            cursor: pointer;
            font-size: 16px;
            color: #34495e;
        }

        .navigation {
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
            color: #fff;
            transition: background-color 0.3s ease;
            min-width: 120px;
            text-align: center;
        }
        .btn-primary {
            background: #007bff;
        }
        .btn-primary:hover {
            background: #0056b3;
        }
        .btn-secondary {
            background: #6c757d;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        .btn:disabled {
            background: #ccc;
            color: #666;
            cursor: not-allowed;
        }
    </style>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/protected/quiz">
    <h1>Quiz</h1>
    <div class="pagination">
        Page <%= request.getAttribute("currentPage") %> of <%= request.getAttribute("totalPages") %>
    </div>

    <input type="hidden" name="quizRunId"   value="<%= request.getAttribute("quizRunId")   %>"/>
    <input type="hidden" name="currentPage" value="<%= request.getAttribute("currentPage") %>"/>

    <%
        List<Map<String, Object>> questionDataList = (List<Map<String, Object>>) request.getAttribute("questionDataList");
        for (Map<String, Object> data : questionDataList) {
            Question q = (Question) data.get("question");
            List<AnswerOption> opts = (List<AnswerOption>) data.get("options");
            Integer runQId = (Integer) data.get("runQuestionId");
            Integer selOpt = (Integer) data.get("selectedOptionId");
    %>
    <div class="question-container">
        <div class="question"><%= q.getQuestion() %></div>
        <input type="hidden" name="runQuestionId" value="<%= runQId %>"/>
        <div class="options">
            <% for (AnswerOption opt : opts) {
                boolean checked = (selOpt != null && selOpt.equals(opt.getId()));
            %>
            <div class="option">
                <input type="radio"
                       id="opt_<%= opt.getId() %>"
                       name="answer_<%= runQId %>"
                       value="<%= opt.getId() %>"
                        <%= checked ? "checked" : "" %> />
                <label for="opt_<%= opt.getId() %>">
                    <%= opt.getOptionLabel() %>. <%= opt.getOptionText() %>
                </label>
            </div>
            <% } %>
        </div>
    </div>
    <% } %>

    <div class="navigation">
        <% if ((Integer)request.getAttribute("currentPage") > 1) { %>
        <button type="submit" name="action" value="previous" class="btn btn-secondary">Previous</button>
        <% } else { %><div></div><% } %>

        <% if ((Integer)request.getAttribute("currentPage") < (Integer)request.getAttribute("totalPages")) { %>
        <button type="submit" name="action" value="next" class="btn btn-primary">Next</button>
        <% } else { %>
        <button type="submit" name="action" value="finish" class="btn btn-primary">Finish Quiz</button>
        <% } %>
    </div>
</form>
</body>
</html>
