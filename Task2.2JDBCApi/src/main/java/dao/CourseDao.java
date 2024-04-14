package dao;

import java.util.List;

import entity.Course;

public interface CourseDao {
    void save(Course course);

    Course findById(int id);

    List<Course> findAll();

    void update(Course course);

    void delete(int id);
}
