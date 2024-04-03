package dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import entity.Student;
import rowmapper.StudentRowMapper;

@Repository
public class StudentDaoJdbc implements StudentDao {
    public static final String INSERT = "INSERT INTO school.students (student_id,group_id, first_name, last_name) VALUES (?, ?, ?,?)";
    public static final String FIND_BY_ID = "SELECT * FROM school.students WHERE student_id = ?";
    public static final String FIND_ALL = "SELECT * FROM school.students";
    public static final String DELETE = "DELETE FROM school.students, school.student_courses WHERE student_id = ?";
    public static final String UPDATE = "UPDATE school.students SET group_id =?, first_name = ?, last_name = ? WHERE student_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Student student) {
        jdbcTemplate.update(INSERT, student.getStudentId(), student.getGroupId(), student.getFirstName(),
                student.getLastName());
    }

    @Override
    public Student findById(int id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new StudentRowMapper(), id);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, new StudentRowMapper());
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update(UPDATE, student.getGroupId(), student.getFirstName(), student.getLastName(),
                student.getStudentId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE, id);
    }
}
