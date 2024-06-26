package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import entity.Course;
import rowmapper.CourseRowMapper;

@SpringBootTest
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clear_tables.sql",
        "/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

class CourseRepositoryTest {
    public static final String INSERT = "SELECT COUNT(*) FROM school.courses WHERE course_id = ?";
    public static final String SELECT = "SELECT * FROM school.courses WHERE course_id = ?";
    public static final String TEST_COURSE_NAME = "CourseName";
    public static final String TEST_COURSE_DESCRIPTION = "Description_test";
    public static final String TEST_INTERVAL = "2 days 3 hours 30 minutes";
    public static final int TEST_COURSE_ID = 11;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CourseDao courseDao;

    @BeforeEach
    void setUp() {
        courseDao = new CourseRepository(jdbcTemplate);
    }

    @Test
    public void testSave() {
        Course course = new Course(TEST_COURSE_ID, TEST_COURSE_NAME, TEST_COURSE_DESCRIPTION, TEST_INTERVAL);
        courseDao.save(course);
        int count = jdbcTemplate.queryForObject(INSERT, Integer.class, TEST_COURSE_ID);
        assertEquals(1, count);
    }

    @Test
    public void testFindById() {
        Course foundCourse = courseDao.findById(TEST_COURSE_ID);
        assertNotNull(foundCourse);
        assertEquals(TEST_COURSE_ID, foundCourse.getCourseId());
        assertEquals(TEST_COURSE_NAME, foundCourse.getCourseName());
        assertEquals(TEST_COURSE_DESCRIPTION, foundCourse.getCourseDescription());
        assertEquals(TEST_INTERVAL, foundCourse.getInterval());
    }

    @Test
    public void testFindAll() {
        List<Course> courses = courseDao.findAll();
        assertNotNull(courses);
        assertEquals(1, courses.size());
        Course testCourse = courses.get(0);
        assertEquals(TEST_COURSE_ID, testCourse.getCourseId());
        assertEquals(TEST_COURSE_NAME, testCourse.getCourseName());
        assertEquals(TEST_COURSE_DESCRIPTION, testCourse.getCourseDescription());
        assertEquals(TEST_INTERVAL, testCourse.getInterval());
    }

    @Test
    public void testUpdate() {
        Course updatedCourse = new Course();
        updatedCourse.setCourseId(TEST_COURSE_ID);
        updatedCourse.setCourseName(TEST_COURSE_NAME);
        updatedCourse.setCourseDescription(TEST_COURSE_DESCRIPTION);
        updatedCourse.setInterval(TEST_INTERVAL);
        courseDao.update(updatedCourse);
        Course testCourse = jdbcTemplate.queryForObject(SELECT, new CourseRowMapper(), updatedCourse.getCourseId());
        assertNotNull(testCourse);
        assertEquals(updatedCourse.getCourseName(), testCourse.getCourseName());
        assertEquals(updatedCourse.getCourseDescription(), testCourse.getCourseDescription());
    }

    @Test
    public void testDelete() {
        courseDao.delete(1);
        Course deletedCourse = jdbcTemplate.queryForObject(SELECT, new CourseRowMapper());
        assertNull(deletedCourse);
    }
}
