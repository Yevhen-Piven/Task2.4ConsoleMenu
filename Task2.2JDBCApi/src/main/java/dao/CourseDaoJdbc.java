package dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import entity.Course;

@Repository
public class CourseDaoJdbc implements CourseDao {
    private final JdbcTemplate jdbcTemplate;

    public CourseDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Course student) {
        // TODO Auto-generated method stub

    }

    @Override
    public Course findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Course> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Course student) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub

    }
}
