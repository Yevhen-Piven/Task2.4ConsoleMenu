package dao;

import java.util.List;
import entity.Student;

public interface StudentDao {
    void save(Student student);

    Student findById(int id);

    List<Student> findAll();

    void update(Student student);

    void delete(int id);
}
