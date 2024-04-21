package services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.CourseDao;
import entity.Course;
import exception.DAOException;
import exception.ServiceException;
import rowmapper.CourseRowMapper;

@Service
public class CourseService {
    private static final String SELECT_ALL = "SELECT * FROM school.courses";
    private static final String SELECT_COURSE_BY_ID_QUERY = "SELECT * FROM school.courses WHERE course_id=?";
    private static final String UPDATE_COURSE_QUERY = "UPDATE school.courses SET course_name=?, course_description=?,interval=? WHERE course_id=?";
    private static final String DELETE_COURSE_QUERY = "DELETE FROM school.courses WHERE course_id=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public CourseDao courseDao;

    public void save(List<Course> data) throws SQLException, DAOException, ServiceException {
        for (Course course : data) {
            courseDao.save(course);
        }
    }

    public Course findById(int id) {
        return jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID_QUERY, new CourseRowMapper(), id);
    }

    public List<Course> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new CourseRowMapper());
    }

    @Transactional
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE_QUERY, course.getCourseName(), course.getCourseDescription(),course.getInterval(),
                course.getCourseId());
    }

    @Transactional
    public void delete(int course_id) {
        jdbcTemplate.update(DELETE_COURSE_QUERY, course_id);
    }

    @Transactional
    public String timeRemaining(int studentId, int courseId) throws DAOException {
        Course course = courseDao.findById(courseId);
        if (course != null) {
            String enrollmentTime = course.getInterval();
            return enrollmentTime;
        } else {
            throw new DAOException("Course with ID " + courseId + " not found");
        }
    }
}
