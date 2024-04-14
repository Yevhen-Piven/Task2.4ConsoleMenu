package services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.StudentDao;
import dao.StudentRepository;
import entity.Student;
import exception.DAOException;
import exception.ServiceException;

@Service
public class StudentService implements EntityService<Student> {

    @Autowired
    public StudentDao studentDao;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void save(List<Student> data) throws SQLException, DAOException, ServiceException {
        for (Student student : data) {
            studentDao.save(student);
        }
    }

    public void removeStudentFromCourse(int studentId, int courseId) throws SQLException {
        studentRepository.removeStudentFromCourse(studentId, courseId);
    }

    public void addStudentToCourse(int studentId, int courseId) {
        studentRepository.addStudentToCourse(studentId, courseId);
    }

    public void deleteStudentById(int studentId) {
        studentRepository.deleteStudentById(studentId);
    }

    public void addStudent(Student student) {
        studentRepository.addStudent(student);
    }

    public List<Student> findStudentsByCourseName(String courseName) {
        return studentRepository.findStudentsByCourseName(courseName);
    }
}
