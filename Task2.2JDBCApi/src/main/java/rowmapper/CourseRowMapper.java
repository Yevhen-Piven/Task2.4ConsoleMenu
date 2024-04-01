package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Course;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setCourseName(rs.getString("course_name"));
        course.setCourseDescription(rs.getString("course_description"));
        return course;
    }

}
