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
-- Schedule schedule = new Schedule("Advanced java", "15/04/2024", "15/10/2024", "Lecture hall 201");





-- for studentClass table
-- Teacher originalTeacher = new Teacher("Jan Kowalski", 40, 15, 50.0, 101);
-- StudentClass studentClass = new StudentClass(students, 'Advanced java', originalTeacher);