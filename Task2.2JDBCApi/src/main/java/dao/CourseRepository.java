package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.Course;
import rowmapper.CourseRowMapper;

@Repository
public class CourseRepository implements CourseDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL = "SELECT * FROM school.courses";
    private static final String SELECT_COURSE_BY_ID_QUERY = "SELECT * FROM school.courses WHERE course_id=?";
    private static final String INSERT_COURSE_QUERY = "INSERT INTO school.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
    private static final String UPDATE_COURSE_QUERY = "UPDATE school.courses SET course_name=?, course_description=? WHERE course_id=?";
    private static final String DELETE_COURSE_QUERY = "DELETE FROM school.courses WHERE course_id=?";

    @Transactional
    @Override
    public void save(Course course) {
        jdbcTemplate.update(INSERT_COURSE_QUERY, course.getCourseId(), course.getCourseName(),
                course.getCourseDescription(),course.getInterval());
    }

    @Override
    public Course findById(int id) {
        return jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID_QUERY, new CourseRowMapper(), id);
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new CourseRowMapper());
    }

    @Transactional
    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE_QUERY, course.getCourseName(), course.getCourseDescription(),course.getInterval(),
                course.getCourseId());
    }

    @Transactional
    @Override
    public void delete(int course_id) {
        jdbcTemplate.update(DELETE_COURSE_QUERY, course_id);
    }
}
