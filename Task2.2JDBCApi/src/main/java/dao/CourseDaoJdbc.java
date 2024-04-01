package dao;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import entity.Course;
import rowmapper.CourseRowMapper;
import rowmapper.RowMapper;

@Repository
public class CourseDaoJdbc implements CourseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL = "SELECT * FROM school.courses";
    private static final String SELECT_COURSE_BY_ID_QUERY = "SELECT * FROM school.courses WHERE course_id=?";
    private static final String INSERT_COURSE_QUERY = "INSERT INTO school.courses (course_id, course_name, course_description) VALUES (?, ?, ?)";
    private static final String UPDATE_COURSE_QUERY = "UPDATE school.courses SET course_name=?, course_description=? WHERE course_id=?";
    private static final String DELETE_COURSE_QUERY = "DELETE FROM school.courses WHERE course_id=?";
    private final RowMapper<Course> courseRowMapper = new CourseRowMapper();

    public CourseDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Course course) {
        jdbcTemplate.update(INSERT_COURSE_QUERY, course.getCourseId(), course.getCourseName(),
                course.getCourseDescription());
    }

    @Override
    public Course findById(int id) {
        return jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID_QUERY, courseRowMapper, id);
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new CourseRowMapper());
    }

    @Override
    public void update(Course course) {
        jdbcTemplate.update(UPDATE_COURSE_QUERY, course.getCourseName(), course.getCourseDescription(),
                course.getCourseId());
    }

    @Override
    public void delete(int course_id) {
        jdbcTemplate.update(DELETE_COURSE_QUERY, course_id);
    }
}
