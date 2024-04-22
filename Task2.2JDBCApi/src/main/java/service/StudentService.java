package service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.StudentDao;
import dao.StudentRepository;
import entity.Student;
import exception.DAOException;
import exception.ServiceException;
import rowmapper.StudentRowMapper;

@Service
public class StudentService {
    public static final String INSERT = "INSERT INTO school.students (student_id,group_id, first_name, last_name) VALUES (?, ?, ?,?)";
    public static final String FIND_BY_ID = "SELECT * FROM school.students WHERE student_id = ?";
    public static final String FIND_ALL = "SELECT * FROM school.students";
    public static final String DELETE = "DELETE FROM school.students, school.student_courses WHERE student_id = ?";
    public static final String UPDATE = "UPDATE school.students SET group_id =?, first_name = ?, last_name = ? WHERE student_id = ?";
    private final JdbcTemplate jdbcTemplate;
    public StudentDao studentDao;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, JdbcTemplate jdbcTemplate) {
        this.studentRepository = studentRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(List<Student> data) throws SQLException, DAOException, ServiceException {
        for (Student student : data) {
            studentDao.save(student);
        }
    }

    public void removeStudentFromCourse(int studentId, int courseId) throws SQLException {
        studentRepository.removeStudentFromCourse(studentId, courseId);
    }

    public void addStudentToCourse(int studentId, int courseId) {
        studentRepository.addStudentToCourse(studentId, courseId);
    }

    public void deleteStudentById(int studentId) {
        studentRepository.deleteStudentById(studentId);
    }

    public void addStudent(Student student) {
        studentRepository.addStudent(student);
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        return studentRepository.findStudentsByCourseName(courseName);
    }

    public Student findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new StudentRowMapper(), id);
    }

    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, new StudentRowMapper());
    }

    @Transactional
    public void update(Student student) {
        jdbcTemplate.update(UPDATE, student.getGroupId(), student.getFirstName(), student.getLastName(),
                student.getStudentId());
    }

    @Transactional
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }

    @Transactional
    public void expelStudent(int studentId) throws DAOException {
        Student student = studentRepository.findById(studentId);
        if (student != null) {
            student.setActive(false);
            studentRepository.update(student);
        } else {
            throw new DAOException("Student with ID " + studentId + " not found");
        }
    }
}
