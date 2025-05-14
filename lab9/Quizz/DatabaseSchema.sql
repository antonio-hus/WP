DROP TABLE IF EXISTS QuizRunQuestions;
DROP TABLE IF EXISTS QuizRunQuestions;
DROP TABLE IF EXISTS QuizRuns;
DROP TABLE IF EXISTS QuizQuestionsAnswerOptions;
DROP TABLE IF EXISTS QuizQuestions;
DROP TABLE IF EXISTS QuizUsers;

CREATE TABLE QuizUsers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(256) NOT NULL,
    best_result INT DEFAULT 0
);

CREATE TABLE QuizQuestions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  question VARCHAR(256) UNIQUE NOT NULL
);

CREATE TABLE QuizQuestionsAnswerOptions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  question_id INT NOT NULL REFERENCES QuizQuestions(id) ON DELETE CASCADE,
  option_label CHAR(1) NOT NULL,
  option_text VARCHAR(256) NOT NULL,
  is_correct BOOLEAN NOT NULL
);

CREATE TABLE QuizRuns (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL REFERENCES QuizUsers(id),
  run_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total_questions INT NOT NULL,
  questions_per_page INT NOT NULL,
  correct_count INT NOT NULL,
  wrong_count INT NOT NULL
);

CREATE TABLE QuizRunQuestions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  quiz_run_id INT NOT NULL REFERENCES QuizRuns(id) ON DELETE CASCADE,
  question_id INT NOT NULL REFERENCES QuizQuestions(id),
  chosen_option_id INT DEFAULT NULL REFERENCES QuizQuestionsAnswerOptions(id),
  is_correct BOOLEAN NOT NULL
);
