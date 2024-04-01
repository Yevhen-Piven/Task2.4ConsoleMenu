package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import entity.Course;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Course course = new Course();
        course.setCourseId(resultSet.getInt("course_id"));
        course.setCourseName(resultSet.getString("course_name"));
        course.setCourseDescription(resultSet.getString("course_description"));
        return course;
    }
}
