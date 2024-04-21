package services;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import dao.StudentDao;
import dao.StudentRepository;
import entity.Student;
import exception.DAOException;
import exception.ServiceException;
import rowmapper.StudentRowMapper;

@SpringBootTest(classes = { StudentService.class })
class StudentServiceTest {
    private static final int TEST_SECOND_STUDENT_ID = 2;
    private static final int TEST_STUDENT_ID = 1;
    private static final int TEST_SECOND_STUDENT_GROUP_ID = 2;
    private static final int TEST_STUDENT_GROUP_ID = 1;
    private static final int TEST_STUDENT_COURSE_ID = 1;
    public static final boolean TEST_ACTIVE = true;
    private static final String TEST_COURSE_NAME = "CourseName 1";
    private static final String TEST_STUDENT_NAME = "StudentName 1";
    private static final String TEST_STUDENT_SURNAME = "StudentSurName 1";
    private static final String TEST_SECOND_STUDENT_SURNAME = "StudentSurName 2";
    private static final String TEST_SECOND_STUDENT_NAME = "StudentName 2";
    public static final String FIND_BY_ID = "SELECT * FROM school.students WHERE student_id = ?";
    public static final String FIND_ALL = "SELECT * FROM school.students";
    public static final String DELETE = "DELETE FROM school.students, school.student_courses WHERE student_id = ?";
    public static final String UPDATE = "UPDATE school.students SET group_id =?, first_name = ?, last_name = ?, active=? WHERE student_id = ?";

    @MockBean
    private StudentDao studentDao;
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        studentDao = mock(StudentDao.class);
    }

    @Test
    public void testSave() throws SQLException, DAOException, ServiceException {
        List<Student> testData = new ArrayList<>();
        testData.add(new Student(TEST_STUDENT_ID, TEST_STUDENT_GROUP_ID, TEST_STUDENT_NAME, TEST_STUDENT_SURNAME,
                TEST_ACTIVE));
        testData.add(new Student(TEST_SECOND_STUDENT_ID, TEST_SECOND_STUDENT_GROUP_ID, TEST_SECOND_STUDENT_NAME,
                TEST_SECOND_STUDENT_SURNAME, TEST_ACTIVE));
        studentService.save(testData);

        for (Student student : testData) {
            verify(studentDao).save(student);
        }
    }

    @Test
    public void testRemoveStudentFromCourse() throws SQLException {
        studentRepository = mock(StudentRepository.class);
        studentService.removeStudentFromCourse(TEST_STUDENT_ID, TEST_STUDENT_COURSE_ID);
        verify(studentRepository).removeStudentFromCourse(TEST_STUDENT_ID, TEST_STUDENT_COURSE_ID);
    }

    @Test
    public void testAddStudentToCourse() {
        studentService.addStudentToCourse(TEST_STUDENT_ID, TEST_STUDENT_COURSE_ID);
        verify(studentRepository).addStudentToCourse(TEST_STUDENT_ID, TEST_STUDENT_COURSE_ID);
    }

    @Test
    public void testDeleteStudentById() {
        studentService.deleteStudentById(TEST_STUDENT_ID);
        verify(studentRepository).deleteStudentById(TEST_STUDENT_ID);
    }

    @Test
    public void testAddStudent() {
        Student student = new Student(TEST_STUDENT_ID, TEST_STUDENT_GROUP_ID, TEST_STUDENT_NAME, TEST_STUDENT_SURNAME,
                TEST_ACTIVE);
        studentService.addStudent(student);
        verify(studentRepository).addStudent(student);
    }

    @Test
    public void testFindStudentsByCourseName() {
        List<Student> expectedStudents = new ArrayList<>();
        when(studentRepository.findStudentsByCourseName(TEST_COURSE_NAME)).thenReturn(expectedStudents);
        List<Student> actualStudents = studentService.findStudentsByCourseName(TEST_COURSE_NAME);
        verify(studentRepository).findStudentsByCourseName(TEST_COURSE_NAME);
        assertEquals(expectedStudents, actualStudents);
    }

    @Test
    public void testFindById() {
        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        Student expectedStudent = new Student(TEST_STUDENT_ID, TEST_STUDENT_GROUP_ID, TEST_STUDENT_NAME,
                TEST_STUDENT_SURNAME, TEST_ACTIVE);
        when(jdbcTemplateMock.queryForObject(eq(FIND_BY_ID), any(StudentRowMapper.class), eq(TEST_STUDENT_ID)))
                .thenReturn(expectedStudent);
        Student actualStudent = studentService.findById(TEST_STUDENT_ID);
        verify(jdbcTemplateMock).queryForObject(eq(FIND_BY_ID), any(StudentRowMapper.class), eq(TEST_STUDENT_ID));
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void testFindAll() {
        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        StudentDao studentDao = mock(StudentDao.class);
        List<Student> testStudents = new ArrayList<>();
        testStudents.add(new Student(TEST_STUDENT_ID, TEST_STUDENT_GROUP_ID, TEST_STUDENT_NAME, TEST_STUDENT_SURNAME,
                TEST_ACTIVE));
        testStudents.add(new Student(TEST_SECOND_STUDENT_ID, TEST_SECOND_STUDENT_GROUP_ID, TEST_SECOND_STUDENT_NAME,
                TEST_SECOND_STUDENT_SURNAME, TEST_ACTIVE));
        when(jdbcTemplateMock.query(FIND_ALL, new StudentRowMapper())).thenReturn(testStudents);
        List<Student> result = studentDao.findAll();

        assertEquals(2, result.size());
        assertEquals(TEST_STUDENT_NAME, result.get(0).getFirstName());
        assertEquals(TEST_SECOND_STUDENT_NAME, result.get(1).getFirstName());
    }

    @Test
    public void testUpdate() {
        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        StudentDao studentDao = mock(StudentDao.class);
        Student student = new Student(TEST_STUDENT_ID, TEST_STUDENT_GROUP_ID, TEST_STUDENT_NAME, TEST_STUDENT_SURNAME,
                TEST_ACTIVE);
        studentDao.update(student);
        verify(jdbcTemplateMock).update(UPDATE, TEST_STUDENT_ID, TEST_STUDENT_GROUP_ID, TEST_STUDENT_NAME,
                TEST_STUDENT_SURNAME);
    }

    @Test
    public void testDelete() {
        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        StudentDao studentDao = mock(StudentDao.class);
        studentDao.delete(TEST_STUDENT_ID);
        verify(jdbcTemplateMock).update(DELETE, TEST_STUDENT_ID);
    }

    @Test
    public void testExpelStudent() throws DAOException {
        Student student = new Student(TEST_STUDENT_ID, TEST_STUDENT_GROUP_ID, TEST_STUDENT_NAME, TEST_STUDENT_SURNAME,
                TEST_ACTIVE);
        when(studentRepository.findById(TEST_STUDENT_ID)).thenReturn(student);
        studentService.expelStudent(TEST_STUDENT_ID);
        assertFalse(student.isActive());
        verify(studentRepository, times(1)).update(student);
    }
}
