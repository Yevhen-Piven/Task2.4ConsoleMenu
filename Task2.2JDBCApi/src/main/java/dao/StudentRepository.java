package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.Student;
import rowmapper.StudentRowMapper;

@Repository
public class StudentRepository implements StudentDao {
    private static final String ASIGN_COURSE_TO_STUDENT = "INSERT INTO school.student_courses (student_id, course_id) VALUES (?, ?)";
    private static final String QUERY_REMOVE_STUDENT_FROM_COURSE = "DELETE FROM school.student_courses WHERE student_id = ? AND course_id = ?";
    public static final String INSERT = "INSERT INTO school.students (student_id,group_id, first_name, last_name) VALUES (?, ?, ?,?)";
    public static final String FIND_BY_ID = "SELECT * FROM school.students WHERE student_id = ?";
    public static final String FIND_ALL = "SELECT * FROM school.students";
    public static final String DELETE = "DELETE FROM school.students, school.student_courses WHERE student_id = ?";
    public static final String UPDATE = "UPDATE school.students SET group_id =?, first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String DELETE_STUDENT_COURSE_QUAERY = "DELETE FROM school.student_courses WHERE student_id = ?";
    private static final String QUERY_CREATE = "INSERT INTO school.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
    private static final String QUERY_FIND_BY_COURSE_NAME = "SELECT students.student_id, students.group_id, students.first_name,students.last_name FROM school.students "
            + "JOIN student_courses ON students.id = student_courses.student_id "
            + "WHERE student_courses.course_name = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void save(Student student) {
        jdbcTemplate.update(INSERT, student.getStudentId(), student.getGroupId(), student.getFirstName(),
                student.getLastName());
    }

    @Override
    public Student findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new StudentRowMapper(), id);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, new StudentRowMapper());
    }

    @Transactional
    @Override
    public void update(Student student) {
        jdbcTemplate.update(UPDATE, student.getGroupId(), student.getFirstName(), student.getLastName(),
                student.getStudentId());
    }

    @Transactional
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Transactional
    public void removeStudentFromCourse(int studentId, int courseId) {
        jdbcTemplate.update(QUERY_REMOVE_STUDENT_FROM_COURSE, studentId, courseId);
    }

    @Transactional
    public void addStudentToCourse(int studentId, int courseId) {
        jdbcTemplate.update(ASIGN_COURSE_TO_STUDENT, studentId, courseId);
    }

    @Transactional
    public void deleteStudentById(int studentId) {
        jdbcTemplate.update(DELETE_STUDENT_COURSE_QUAERY, studentId);
    }

    @Transactional
    public void addStudent(Student student) {
        jdbcTemplate.update(QUERY_CREATE, student.getStudentId(), student.getGroupId(), student.getFirstName(),
                student.getLastName());
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        return jdbcTemplate.query(QUERY_FIND_BY_COURSE_NAME, new StudentRowMapper());
    }
}
