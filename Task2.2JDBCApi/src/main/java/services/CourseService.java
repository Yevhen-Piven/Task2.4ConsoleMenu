package services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CourseDao;
import entity.Course;
import exception.DAOException;
import exception.ServiceException;

@Service
public class CourseService implements EntityService<Course> {

    @Autowired
    public CourseDao courseDao;

    @Override
    public void save(List<Course> data) throws SQLException, DAOException, ServiceException {
        for (Course course : data) {
            courseDao.save(course);
        }
    }
}
