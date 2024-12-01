-- springdb

-- for exam table
-- Exam exam = new Exam("Java", LocalDate.of(2024, 12, 16));
-- exam.setMaxScore(100);
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


-- for schedule table

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





-- for teacher table
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

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(64) NOT NULL,
                       role VARCHAR(20) NOT NULL
);

INSERT INTO users (username, password, role) VALUES
                                                 ('admin', 'password', 'ADMIN'),
                                                 ('user', 'password', 'USER');