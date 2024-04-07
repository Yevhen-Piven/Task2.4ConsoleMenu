package ua.foxminded.yevhenpiven;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import dao.StudentDao;
import dao.StudentRepository;
import entity.Student;
import rowmapper.StudentRowMapper;

@SpringBootTest
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clear_tables.sql",
        "/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class StudentRepositoryTest {
    public static final String INSERT = "SELECT COUNT(*) FROM school.students WHERE student_id = ?";
    public static final String SELECT = "SELECT * FROM school.students WHERE student_id = ?";
    public static final String INSERT_INTO = "INSERT INTO school.students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
    public static final String TEST_STUDENT_NAME = "StudentName";
    public static final String TEST_STUDENT_SURNAME = "StudentSurname";
    public static final int TEST_STUDENT_ID = 201;
    public static final int TEST_STUDENT_ID_GROUP = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private StudentDao studentDao;

    @BeforeEach
    void setUp() {
        studentDao = new StudentRepository(jdbcTemplate);
    }

    @Test
    public void testSaveStudent() {
        Student student = new Student(TEST_STUDENT_ID, TEST_STUDENT_ID_GROUP, TEST_STUDENT_NAME, TEST_STUDENT_SURNAME);
        studentDao.save(student);
        int count = jdbcTemplate.queryForObject(INSERT, Integer.class, TEST_STUDENT_ID);
        assertEquals(1, count);
    }

    @Test
    public void testFindStudentById() {
        Student student = studentDao.findById(TEST_STUDENT_ID);
        assertNotNull(student);
        assertEquals(TEST_STUDENT_ID, student.getStudentId());
    }

    @Test
    public void testFindAll() {
        List<Student> students = studentDao.findAll();
        assertNotNull(students);
        assertEquals(1, students.size());
    }

    @Test
    public void testUpdate() {
        Student student = studentDao.findById(1);
        assertNotNull(student);
        student.setFirstName(TEST_STUDENT_NAME);
        student.setLastName(TEST_STUDENT_SURNAME);
        studentDao.update(student);
        Student updatedStudent = jdbcTemplate.queryForObject(SELECT, new StudentRowMapper(), student.getStudentId());
        assertNotNull(updatedStudent);
        assertEquals(TEST_STUDENT_NAME, updatedStudent.getFirstName());
        assertEquals(TEST_STUDENT_SURNAME, updatedStudent.getLastName());
    }

    @Test
    public void testDelete() {
        jdbcTemplate.update(INSERT_INTO, TEST_STUDENT_ID, TEST_STUDENT_ID_GROUP, TEST_STUDENT_NAME,
                TEST_STUDENT_SURNAME);

        Student existingStudent = studentDao.findById(TEST_STUDENT_ID);
        assertEquals(TEST_STUDENT_NAME, existingStudent.getFirstName());
        assertEquals(TEST_STUDENT_SURNAME, existingStudent.getLastName());
        studentDao.delete(TEST_STUDENT_ID);
        Student deletedStudent = studentDao.findById(TEST_STUDENT_ID);
        assertNull(deletedStudent);
    }
}
