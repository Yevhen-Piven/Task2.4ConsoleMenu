DROP TABLE school.students CASCADE;
DROP TABLE school.courses CASCADE;
DROP TABLE school.student_groups CASCADE;
DROP TABLE school.student_courses CASCADE;
CREATE TABLE school.student_groups (group_id int primary key, group_name character varying(250) not null );
CREATE TABLE school.students (student_id int primary key, group_id int  references school.student_groups(group_id), first_name character varying(250) not null, last_name character varying(250) not null );
CREATE TABLE school.courses (course_id int primary key, course_name character varying(250) not null, course_description character varying(250));
CREATE TABLE school.student_courses (    student_id INT,    course_id INT,    PRIMARY KEY (student_id, course_id),    FOREIGN KEY (student_id) REFERENCES school.students(student_id),    FOREIGN KEY (course_id) REFERENCES school.courses(course_id));


