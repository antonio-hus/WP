INSERT INTO QuizQuestions (question) VALUES
('What color is the sky on a clear day?'),
('How many legs does a spider have?'),
('What is the capital of France?'),
('Which planet is known as the Red Planet?'),
('What do bees produce?'),
('What is 2 + 2?'),
('Which animal is known as the King of the Jungle?'),
('What shape has three sides?'),
('How many days are in a week?'),
('What do you call water in its solid form?'),
('Which month comes after April?'),
('What is the opposite of hot?'),
('Which fruit is yellow and curved?'),
('How many wheels does a bicycle have?'),
('What do you use to write on a blackboard?'),
('Which animal says "meow"?'),
('What is H2O commonly called?'),
('Which day is the first day of the weekend?'),
('What color do you get by mixing red and white?'),
('What is the primary food for pandas?');

INSERT INTO QuizQuestionsAnswerOptions (question_id, option_label, option_text, is_correct) VALUES
(1,  'A', 'Blue',    TRUE),
(1,  'B', 'Green',   FALSE),
(1,  'C', 'Yellow',  FALSE),

(2,  'A', '6',       FALSE),
(2,  'B', '8',       TRUE),
(2,  'C', '10',      FALSE),

(3,  'A', 'Berlin',  FALSE),
(3,  'B', 'Madrid',  FALSE),
(3,  'C', 'Paris',   TRUE),

(4,  'A', 'Mars',    TRUE),
(4,  'B', 'Venus',   FALSE),
(4,  'C', 'Jupiter', FALSE),

(5,  'A', 'Milk',    FALSE),
(5,  'B', 'Honey',   TRUE),
(5,  'C', 'Silk',    FALSE),

(6,  'A', '3',       FALSE),
(6,  'B', '4',       TRUE),
(6,  'C', '5',       FALSE),

(7,  'A', 'Lion',     TRUE),
(7,  'B', 'Elephant', FALSE),
(7,  'C', 'Tiger',    FALSE),

(8,  'A', 'Square',   FALSE),
(8,  'B', 'Triangle', TRUE),
(8,  'C', 'Circle',   FALSE),

(9,  'A', '5',        FALSE),
(9,  'B', '6',        FALSE),
(9,  'C', '7',        TRUE),

(10, 'A', 'Steam',    FALSE),
(10, 'B', 'Ice',      TRUE),
(10, 'C', 'Vapor',    FALSE),

(11, 'A', 'May',      TRUE),
(11, 'B', 'June',     FALSE),
(11, 'C', 'March',    FALSE),

(12, 'A', 'Warm',     FALSE),
(12, 'B', 'Cold',     TRUE),
(12, 'C', 'Cool',     FALSE),

(13, 'A', 'Apple',    FALSE),
(13, 'B', 'Banana',   TRUE),
(13, 'C', 'Grapes',   FALSE),

(14, 'A', '2',        TRUE),
(14, 'B', '3',        FALSE),
(14, 'C', '4',        FALSE),

(15, 'A', 'Pen',      FALSE),
(15, 'B', 'Chalk',    TRUE),
(15, 'C', 'Brush',    FALSE),

(16, 'A', 'Dog',      FALSE),
(16, 'B', 'Cat',      TRUE),
(16, 'C', 'Cow',      FALSE),

(17, 'A', 'Salt',     FALSE),
(17, 'B', 'Water',    TRUE),
(17, 'C', 'Air',      FALSE),

(18, 'A', 'Friday',   FALSE),
(18, 'B', 'Saturday', TRUE),
(18, 'C', 'Sunday',   FALSE),

(19, 'A', 'Pink',     TRUE),
(19, 'B', 'Purple',   FALSE),
(19, 'C', 'Orange',   FALSE),

(20, 'A', 'Bamboo',   TRUE),
(20, 'B', 'Fish',     FALSE),
(20, 'C', 'Grass',    FALSE);
