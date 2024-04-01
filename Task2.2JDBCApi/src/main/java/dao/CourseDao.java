package dao;

import java.util.List;
import entity.Course;

public interface CourseDao {
    void save(Course student);

    Course findById(int id);

    List<Course> findAll();

    void update(Course student);

    void delete(int id);
}
