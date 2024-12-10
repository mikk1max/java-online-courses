-- springdb

--Course
CREATE TABLE course (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        description TEXT,
                        duration INT NOT NULL CHECK (duration > 0)
);

INSERT INTO course (title, description, duration)
VALUES
    ('Spring Boot Basics', 'Poznaj podstawy Spring Boot', 10),
    ('React Native', 'Twórz aplikacje mobilne za pomocą React Native', 8),
    ('Advanced Java', 'Master advanced concepts of Java programming', 12),
    ('Node.js & Express', 'Build backend services using Node.js and Express', 9),
    ('HTML & CSS Fundamentals', 'Learn the basics of web development with HTML and CSS', 6),
    ('JavaScript ES6+', 'Explore modern JavaScript features from ES6 and beyond', 7),
    ('Database Management', 'Understand relational databases and SQL', 10),
    ('Firebase Integration', 'Integrate Firebase services in your mobile and web apps', 5),
    ('React.js', 'Build interactive UIs with React.js', 8),
    ('REST API Development', 'Design and implement RESTful APIs', 6);


--Exam
CREATE TABLE exam (
                      id SERIAL PRIMARY KEY,
                      subject VARCHAR(255) NOT NULL,
                      date DATE NOT NULL,
                      max_score INTEGER NOT NULL,
                      is_active BOOLEAN DEFAULT true
);

INSERT INTO exam (subject, date, max_score, is_active) VALUES
                                                           ('Mathematics', '2024-11-05', 100, true),
                                                           ('History', '2024-11-10', 90, true),
                                                           ('Physics', '2024-11-15', 85, true);


-- Schedule

CREATE TABLE schedule (
                      id SERIAL PRIMARY KEY,
                      course_title VARCHAR(255) NOT NULL,
                      start_date VARCHAR(255) NOT NULL,
                      end_date VARCHAR(255) NOT NULL,
                      location VARCHAR(255) NOT NULL,
                      is_active BOOLEAN DEFAULT true
);

INSERT INTO schedule (course_title, start_date, end_date, location, is_active) VALUES
                                                           ('Pyton courses', '10/05/2024', '10/10/2024', 'lecture hall 201', true),
                                                           ('C++ courses', '15/05/2024', '15/10/2024', 'lecture hall 301', true),
                                                           ('C# courses', '20/05/2024', '20/10/2024', 'lecture hall 402', true);


--Student
CREATE TABLE student (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         age INT NOT NULL CHECK (age > 0)
);

INSERT INTO student (name, age)
VALUES
    ('Milena Runets', 21),
    ('Max Shepeta', 20),
    ('Leanid Shaveika', 21),
    ('John Doe', 22),
    ('Oleg Nowak', 21),
    ('Jane Doe', 21),
    ('Justin Quinn', 15),
    ('Rayan Goslindg', 13),
    ('Oleg Nowak', 25),
    ('John Doe', 22),
    ('Adam Kim', 55),
    ('Nicholas Jones', 15);


--Enrollments
CREATE TABLE enrollment (
                            id SERIAL PRIMARY KEY,  -- Автоматическое увеличение для ID
                            student_id BIGINT NOT NULL,            -- ID студента (внешний ключ)
                            course_id BIGINT NOT NULL,             -- ID курса (внешний ключ)
                            FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE,
                            FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
);

INSERT INTO enrollment (student_id, course_id)
VALUES
    (1, 1),
    (2, 2),
    (1, 3),
    (2, 4),
    (3, 5),
    (3, 6),
    (4, 3),
    (4, 7),
    (5, 8),
    (6, 9),
    (2, 8),
    (6, 10),
    (7, 6),
    (8, 7),
    (9, 5),
    (10, 4),
    (11, 2),
    (12, 1),
    (2, 1);

--Teacher
CREATE TABLE teacher(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    experience INTEGER NOT NULL,
    hourly_rate DOUBLE PRECISION NOT NULL,
    is_active BOOLEAN DEFAULT true
);

INSERT INTO teacher (name, age, experience, hourly_rate, is_active) VALUES
('Adrian Nowak', 30, 5, 20.0, true),
('Beata Karol', 40, 10, 25.0, true),
('Karolina Nowak', 35, 7, 22.5, true),
('Ryan Gosling', 28, 3, 18.0, true),
('John Snow', 45, 15, 30.0, true);

--Users
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(64) NOT NULL,
                       role VARCHAR(20) NOT NULL
);

