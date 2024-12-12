-- springdb

--Course
CREATE TABLE course
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(200) NOT NULL,
    description TEXT,
    duration    INT          NOT NULL CHECK (duration > 0),
    is_active   BOOLEAN DEFAULT true
);

INSERT INTO course (title, description, duration)
VALUES ('Spring Boot Basics', 'Poznaj podstawy Spring Boot', 10),
       ('React Native', 'Twórz aplikacje mobilne za pomocą React Native', 8),
       ('Advanced Java', 'Master advanced concepts of Java programming', 12),
       ('Node.js & Express', 'Build backend services using Node.js and Express', 9),
       ('HTML & CSS Fundamentals', 'Learn the basics of web development with HTML and CSS', 6),
       ('JavaScript ES6+', 'Explore modern JavaScript features from ES6 and beyond', 7),
       ('Database Management', 'Understand relational databases and SQL', 10),
       ('Firebase Integration', 'Integrate Firebase services in your mobile and web apps', 5),
       ('React.js', 'Build interactive UIs with React.js', 8),
       ('REST API Development', 'Design and implement RESTful APIs', 6),
       ('Python Basics', 'Learn the fundamentals of Python programming', 10),
       ('C++ Programming', 'Master the basics and advanced features of C++', 11),
       ('Data Structures and Algorithms', 'Learn the essential algorithms and data structures', 14),
       ('Cloud Computing with AWS', 'Understand cloud computing and services with AWS', 13),
       ('Mobile App Development with Flutter', 'Create mobile applications with Flutter', 10),
       ('Agile Software Development', 'Learn Agile principles and practices', 9),
       ('DevOps Fundamentals', 'Introduction to DevOps and CI/CD pipelines', 12),
       ('Cybersecurity Basics', 'Learn about cybersecurity principles and best practices', 8),
       ('Machine Learning with Python', 'Get started with machine learning using Python', 15),
       ('Introduction to AI', 'Learn the basics of artificial intelligence and its applications', 10),
       ('Blockchain Fundamentals', 'Understand blockchain technology and its applications', 11),
       ('Data Science with R', 'Learn data science concepts and techniques using R programming', 12),
       ('Digital Marketing', 'Learn strategies and tools for effective digital marketing', 9),
       ('UI/UX Design', 'Understand the principles of user interface and user experience design', 8);


--Exam
CREATE TABLE exam
(
    id        SERIAL PRIMARY KEY,
    subject   VARCHAR(255) NOT NULL,
    date      DATE         NOT NULL,
    max_score INTEGER      NOT NULL,
    is_active BOOLEAN DEFAULT true
);

INSERT INTO exam (subject, date, max_score)
VALUES ('Spring Boot Basics', '2024-10-16', 100),
       ('React Native', '2024-10-19', 90),
       ('Advanced Java', '2024-10-26', 85),
       ('Node.js & Express', '2024-10-31', 95),
       ('HTML & CSS Fundamentals', '2024-11-02', 80),
       ('JavaScript ES6+', '2024-11-06', 90),
       ('Database Management', '2024-11-12', 100),
       ('Firebase Integration', '2024-11-15', 85),
       ('React.js', '2024-11-17', 95),
       ('REST API Development', '2024-11-22', 80),
       ('Python Basics', '2024-11-24', 90),
       ('C++ Programming', '2024-11-28', 85),
       ('Data Structures and Algorithms', '2024-12-05', 100),
       ('Cloud Computing with AWS', '2024-12-10', 90),
       ('Mobile App Development with Flutter', '2024-12-13', 85),
       ('Agile Software Development', '2024-12-18', 80),
       ('DevOps Fundamentals', '2024-12-22', 90),
       ('Cybersecurity Basics', '2024-12-28', 85),
       ('Machine Learning with Python', '2025-01-05', 100);


-- Schedule
CREATE TABLE schedule
(
    id           SERIAL PRIMARY KEY,
    course_title VARCHAR(255) NOT NULL,
    start_date   VARCHAR(255) NOT NULL,
    end_date     VARCHAR(255) NOT NULL,
    location     VARCHAR(255) NOT NULL,
    is_active    BOOLEAN DEFAULT true
);

INSERT INTO schedule (course_title, start_date, end_date, location)
VALUES ('Spring Boot Basics', '2024-10-05', '2024-10-15', 'lecture hall 201'),
       ('React Native', '2024-10-10', '2024-10-18', 'lecture hall 301'),
       ('Advanced Java', '2024-10-15', '2024-10-25', 'lecture hall 402'),
       ('Node.js & Express', '2024-10-20', '2024-10-30', 'lecture hall 202'),
       ('HTML & CSS Fundamentals', '2024-10-25', '2024-10-30', 'lecture hall 203'),
       ('JavaScript ES6+', '2024-10-27', '2024-11-03', 'lecture hall 204'),
       ('Database Management', '2024-11-01', '2024-11-10', 'lecture hall 205'),
       ('Firebase Integration', '2024-11-05', '2024-11-12', 'lecture hall 206'),
       ('React.js', '2024-11-07', '2024-11-14', 'lecture hall 207'),
       ('REST API Development', '2024-11-10', '2024-11-16', 'lecture hall 208'),
       ('Python Basics', '2024-11-12', '2024-11-22', 'lecture hall 301'),
       ('C++ Programming', '2024-11-15', '2024-11-25', 'lecture hall 302'),
       ('Data Structures and Algorithms', '2024-11-20', '2024-12-04', 'lecture hall 303'),
       ('Cloud Computing with AWS', '2024-12-01', '2024-12-14', 'lecture hall 304'),
       ('Mobile App Development with Flutter', '2024-12-05', '2024-12-15', 'lecture hall 305'),
       ('Agile Software Development', '2024-12-10', '2024-12-20', 'lecture hall 306'),
       ('DevOps Fundamentals', '2024-12-15', '2024-12-25', 'lecture hall 307'),
       ('Cybersecurity Basics', '2024-12-20', '2024-12-30', 'lecture hall 308'),
       ('Machine Learning with Python', '2024-12-25', '2025-01-10', 'lecture hall 309');


-- --Student
-- CREATE TABLE student
-- (
--     id        SERIAL PRIMARY KEY,
--     name      VARCHAR(100) NOT NULL,
--     age       INT          NOT NULL CHECK (age > 0),
--     is_active BOOLEAN DEFAULT true
-- );
--
-- INSERT INTO student (name, age)
-- VALUES ('Milena Runets', 21),
--        ('Max Shepeta', 20),
--        ('Leanid Shaveika', 21),
--        ('John Doe', 22),
--        ('Oleg Nowak', 21),
--        ('Jane Doe', 21),
--        ('Justin Quinn', 15),
--        ('Rayan Goslindg', 13),
--        ('Oleg Nowak', 25),
--        ('John Doe', 22),
--        ('Adam Kim', 55),
--        ('Nicholas Jones', 15),
--        ('Olga Kowalski', 19),
--        ('David Smith', 23),
--        ('Eva Johnson', 20),
--        ('Lucas Brown', 18),
--        ('Sophia Davis', 22),
--        ('Michael Lee', 24),
--        ('Isabella Wilson', 17),
--        ('Gabriel Martinez', 22),
--        ('Chloe White', 19),
--        ('Ethan Taylor', 21),
--        ('Ava Harris', 20),
--        ('Mason Clark', 23),
--        ('Lily Scott', 16),
--        ('Mia Robinson', 20),
--        ('James Lewis', 21),
--        ('Liam Walker', 22),
--        ('Charlotte Allen', 18),
--        ('Amelia Young', 20),
--        ('Henry King', 19),
--        ('Ella Adams', 21),
--        ('Benjamin Hill', 22),
--        ('Lucas Scott', 20),
--        ('Charlotte Turner', 18),
--        ('Zoe Lopez', 23),
--        ('William Parker', 19),
--        ('Nathaniel Carter', 21),
--        ('Sophie Mitchell', 20),
--        ('Oliver Evans', 18),
--        ('Sophia Thomas', 21),
--        ('Daniel Garcia', 20),
--        ('Layla Nelson', 17),
--        ('Elijah Harris', 22),
--        ('Mohamed Alli', 24);





-- --Teacher
-- CREATE TABLE teacher
-- (
--     id          SERIAL PRIMARY KEY,
--     name        VARCHAR(255)     NOT NULL,
--     age         INTEGER          NOT NULL,
--     experience  INTEGER          NOT NULL,
--     hourly_rate DOUBLE PRECISION NOT NULL,
--     is_active   BOOLEAN DEFAULT true
-- );
--
-- INSERT INTO teacher (name, age, experience, hourly_rate)
-- VALUES ('Adrian Nowak', 30, 5, 20.0),
--        ('Beata Karol', 40, 10, 25.0),
--        ('Karolina Nowak', 35, 7, 22.5),
--        ('Ryan Gosling', 28, 3, 18.0),
--        ('John Snow', 45, 15, 30.0),
--        ('Sarah Williams', 38, 12, 28.0),
--        ('Tom Green', 32, 6, 21.5),
--        ('Sophia Turner', 33, 8, 24.0),
--        ('Peter Lee', 40, 14, 26.5),
--        ('Nina White', 29, 4, 19.0),
--        ('David Smith', 41, 16, 29.0),
--        ('Olivia Brown', 34, 9, 23.0),
--        ('Jack Miller', 37, 10, 25.5),
--        ('Emily Davis', 31, 5, 20.5),
--        ('Christopher Young', 43, 13, 27.0),
--        ('Rachel Clark', 36, 8, 22.0),
--        ('Matthew Taylor', 39, 11, 26.0),
--        ('Victoria Allen', 42, 15, 30.5),
--        ('Brian Scott', 29, 6, 21.0),
--        ('Alice Walker', 30, 7, 23.5);
--
--
-- --Users
-- CREATE TABLE users
-- (
--     id       SERIAL PRIMARY KEY,
--     username VARCHAR(50) UNIQUE NOT NULL,
--     password VARCHAR(64)        NOT NULL,
--     role     VARCHAR(20)        NOT NULL,
--     is_active   BOOLEAN DEFAULT true
-- );

-- Создание таблицы User (базовая таблица)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- Создание таблицы Student (наследует от User)
CREATE TABLE student (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL
);


-- Создание таблицы Teacher (наследует от User)
CREATE TABLE teacher (
    id INTEGER PRIMARY KEY REFERENCES users(id),  -- Ссылаемся на id из таблицы users
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    experience INT NOT NULL,
    hourly_rate DECIMAL(10, 2) NOT NULL
);

CREATE TABLE admin (
                         id BIGINT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         age INT NOT NULL
);

--Enrollments
CREATE TABLE enrollment
(
    id         SERIAL PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    FOREIGN KEY (student_id) REFERENCES student (id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES course (id) ON DELETE CASCADE
);

-- INSERT INTO enrollment (student_id, course_id)
-- VALUES (1, 1),
--        (2, 2),
--        (1, 3),
--        (2, 4),
--        (3, 5),
--        (3, 6),
--        (4, 3),
--        (4, 7),
--        (5, 8),
--        (6, 9),
--        (2, 8),
--        (6, 10),
--        (7, 6),
--        (8, 7),
--        (9, 5),
--        (10, 4),
--        (11, 2),
--        (12, 1),
--        (2, 1),
--        (13, 5),
--        (14, 6),
--        (15, 7),
--        (16, 8),
--        (17, 9),
--        (18, 10),
--        (19, 11),
--        (20, 12),
--        (21, 13),
--        (22, 14),
--        (23, 15),
--        (24, 16),
--        (25, 17),
--        (26, 18),
--        (27, 19),
--        (28, 20),
--        (29, 1),
--        (30, 2),
--        (31, 3),
--        (32, 4),
--        (33, 5),
--        (34, 6),
--        (35, 7),
--        (36, 8),
--        (37, 9),
--        (38, 10),
--        (39, 11),
--        (40, 12),
--        (41, 13),
--        (42, 14),
--        (43, 15),
--        (44, 16),
--        (45, 17);