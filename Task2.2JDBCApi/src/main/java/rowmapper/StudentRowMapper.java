package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import entity.Student;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Student student = new Student();
        student.setStudentId(resultSet.getInt("student_id"));
        student.setGroupId(resultSet.getInt("group_id"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        return student;
    }
}
