#mysql -h localhost -p Ashen123@ -u root

DROP DATABASE IF EXISTS StudentManagementSystem;
CREATE DATABASE IF NOT EXISTS StudentManagementSystem;
SHOW DATABASES ;
USE StudentManagementSystem;

DROP TABLE IF EXISTS Student;
CREATE TABLE IF NOT EXISTS Student(
    student_id VARCHAR(45),
    student_name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    email TEXT,
    contact VARCHAR(20),
    address TEXT,
    nic VARCHAR(45),
    CONSTRAINT PRIMARY KEY (student_id)
);

DROP TABLE IF EXISTS Teacher;
CREATE TABLE IF NOT EXISTS Teacher(
    teacher_id VARCHAR(45),
    name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    nic VARCHAR(45),
    contact VARCHAR(45),
    address TEXT,
    CONSTRAINT PRIMARY KEY (teacher_id)
);

DROP TABLE IF EXISTS Subject;
CREATE TABLE IF NOT EXISTS Subject(
    subject_id VARCHAR(45),
    subject_name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    credit DOUBLE,
    teacher_id VARCHAR(45),
    CONSTRAINT PRIMARY KEY (subject_id),
    CONSTRAINT FOREIGN KEY (teacher_id) REFERENCES Teacher(teacher_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Course;
CREATE TABLE IF NOT EXISTS Course(
    course_id VARCHAR(45),
    course_name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
    cost DOUBLE,
    duration VARCHAR(45),
    subject_id VARCHAR(45),
    CONSTRAINT PRIMARY KEY (course_id),
    CONSTRAINT FOREIGN KEY (subject_id) REFERENCES Subject(subject_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Intake;
CREATE TABLE IF NOT EXISTS Intake(
    intake_id VARCHAR(45),
    start_date DATE,
    intakeCol VARCHAR(45),
    description VARCHAR(45),
    course_id VARCHAR(45),
    CONSTRAINT PRIMARY KEY (intake_id),
    CONSTRAINT FOREIGN KEY (course_id) REFERENCES Course(course_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Registration;
CREATE TABLE IF NOT EXISTS Registration(
    registration_id VARCHAR(45),
    reg_date DATE,
    student_id VARCHAR(45),
    intake_id VARCHAR(45),
    CONSTRAINT PRIMARY KEY (registration_id),
    CONSTRAINT FOREIGN KEY (student_id) REFERENCES Student(student_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (intake_id) REFERENCES Intake(intake_id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS Payment;
CREATE TABLE IF NOT EXISTS Payment(
    payment_id VARCHAR(45),
    date DATE,
    cost DOUBLE,
    registration_id VARCHAR(45),
    CONSTRAINT PRIMARY KEY (payment_id),
    CONSTRAINT FOREIGN KEY (registration_id) REFERENCES Registration(registration_id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO Student VALUES ('S00-001','Ashen','ashen@gmail.com','0762020312','Galle','200112100701');
INSERT INTO Student VALUES ('S00-002','Hansaka','hansaka@gmail.com','0756848595','Matara','200148486222');
INSERT INTO Student VALUES ('S00-003','Danuja','danuja@gmail.com','0758496585','Panadura','200445805456');

INSERT INTO Teacher VALUES ('T001','Danuja','200445805456','0758496585','Panadura');
INSERT INTO Teacher VALUES ('T002','Niroth','200445805456','0758496585','Panadura');
INSERT INTO Teacher VALUES ('T003','Sanuka','200445805456','0758496585','Panadura');

INSERT INTO Subject VALUES ('Su001','OOP','1000','T002');
INSERT INTO Subject VALUES ('Su002','MVC','1000','T003');
INSERT INTO Subject VALUES ('Su003','Hibernate','1000','T001');

INSERT INTO Course VALUES ('C001','GDSE','300000','2 year','Su001');
INSERT INTO Course VALUES ('C002','CMJD','100000','1 year','Su002');
INSERT INTO Course VALUES ('C003','MD','50000','6 month','Su003');