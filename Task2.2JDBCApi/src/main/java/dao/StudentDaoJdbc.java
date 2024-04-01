package dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import entity.Student;

@Repository
public class StudentDaoJdbc implements StudentDao {
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Student student) {
        // TODO Auto-generated method stub

    }

    @Override
    public Student findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Student> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Student student) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub

    }

}
