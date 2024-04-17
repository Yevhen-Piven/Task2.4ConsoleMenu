package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import dao.CourseDao;
import entity.Course;
import exception.DAOException;
import exception.ServiceException;
import rowmapper.CourseRowMapper;

@SpringBootTest(classes = { CourseService.class })
class CourseServiceTest {
    private static final String EXAMPLE_COURSE_FIRST = "Course 1";
    private static final String EXAMPLE_DESCRIPTION_FIRST = "Description 1";
    private static final String EXAMPLE_COURSE_SECOND = "Course 2";
    private static final String EXAMPLE_DESCRIPTION_SECOND = "Description 2";
    private static final String TEST_QUERY_UPDATE = "UPDATE school.courses SET course_name = ?, course_description = ? WHERE course_id = ?";
    private static final String TEST_QUERY_DELETE = "DELETE FROM school.courses WHERE course_id = ?";
    private static final int TEST_ID = 1;
    private static final int SECOND_TEST_ID = 2;

    @MockBean
    CourseDao courseDao;

    @Autowired
    CourseService courseService;

    @Test
    public void testSave() throws SQLException, DAOException, ServiceException {
        CourseDao courseDaoMock = mock(CourseDao.class);
        List<Course> testData = Arrays.asList(new Course(TEST_ID, EXAMPLE_COURSE_FIRST, EXAMPLE_DESCRIPTION_FIRST),
                new Course(SECOND_TEST_ID, EXAMPLE_COURSE_SECOND, EXAMPLE_DESCRIPTION_SECOND));
        courseService.save(testData);
        verify(courseDaoMock, times(1)).save(testData.get(0));
        verify(courseDaoMock, times(1)).save(testData.get(1));
    }

    @Test
    public void testFindById() throws SQLException, DAOException {
        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        CourseService courseService = new CourseService(jdbcTemplateMock);

        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getInt("course_id")).thenReturn(TEST_ID);
        when(resultSetMock.getString("course_name")).thenReturn(EXAMPLE_COURSE_FIRST);
        when(resultSetMock.getString("course_description")).thenReturn(EXAMPLE_DESCRIPTION_FIRST);
        when(jdbcTemplateMock.queryForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any(CourseRowMapper.class),
                ArgumentMatchers.anyInt())).thenReturn((Course) resultSetMock);
        Course result = courseService.findById(1);

        assertNotNull(result);
        assertEquals(TEST_ID, result.getCourseId());
        assertEquals(EXAMPLE_COURSE_FIRST, result.getCourseName());
        assertEquals(EXAMPLE_DESCRIPTION_FIRST, result.getCourseDescription());
    }

    @Test
    public void testFindAll() throws SQLException, DAOException {

        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        CourseService courseService = new CourseService(jdbcTemplateMock);

        ResultSet resultSetMock1 = createResultSetMock(TEST_ID, EXAMPLE_COURSE_FIRST, EXAMPLE_DESCRIPTION_FIRST);
        ResultSet resultSetMock2 = createResultSetMock(SECOND_TEST_ID, EXAMPLE_COURSE_SECOND,
                EXAMPLE_DESCRIPTION_SECOND);

        List<Course> resultSetList = new ArrayList<>();
        resultSetList.add((Course) resultSetMock1);
        resultSetList.add((Course) resultSetMock2);

        when(jdbcTemplateMock.query(ArgumentMatchers.anyString(), ArgumentMatchers.any(CourseRowMapper.class)))
                .thenReturn(resultSetList);

        List<Course> result = courseService.findAll();

        assertNotNull(result);
        assertEquals(SECOND_TEST_ID, result.size());
        assertEquals(TEST_ID, result.get(0).getCourseId());
        assertEquals(EXAMPLE_COURSE_FIRST, result.get(0).getCourseName());
        assertEquals(EXAMPLE_DESCRIPTION_FIRST, result.get(0).getCourseDescription());
        assertEquals(SECOND_TEST_ID, result.get(1).getCourseId());
        assertEquals(EXAMPLE_COURSE_SECOND, result.get(1).getCourseName());
        assertEquals(EXAMPLE_DESCRIPTION_SECOND, result.get(1).getCourseDescription());
    }

    private ResultSet createResultSetMock(int id, String name, String description) throws SQLException {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.getInt("course_id")).thenReturn(id);
        when(resultSetMock.getString("course_name")).thenReturn(name);
        when(resultSetMock.getString("course_description")).thenReturn(description);
        return resultSetMock;
    }

    @Test
    public void testUpdate() throws SQLException, DAOException {

        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        CourseService courseService = new CourseService(jdbcTemplateMock);
        Course courseToUpdate = new Course(TEST_ID, EXAMPLE_COURSE_FIRST, EXAMPLE_DESCRIPTION_FIRST);
        courseService.update(courseToUpdate);
        verify(jdbcTemplateMock).update(TEST_QUERY_UPDATE, EXAMPLE_COURSE_FIRST, EXAMPLE_DESCRIPTION_FIRST, TEST_ID);
    }

    @Test
    public void testDelete() throws SQLException, DAOException {
        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        CourseService courseService = new CourseService(jdbcTemplateMock);
        courseService.delete(TEST_ID);
        verify(jdbcTemplateMock).update(TEST_QUERY_DELETE, TEST_ID);
    }
}
