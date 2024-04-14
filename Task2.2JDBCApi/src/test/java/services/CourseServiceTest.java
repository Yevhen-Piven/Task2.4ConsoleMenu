package services;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dao.CourseDao;
import exception.DAOException;
import exception.ServiceException;

@SpringBootTest(classes = { CourseService.class })
class CourseServiceTest {
    @MockBean
    CourseDao courseDao;

    @Autowired
    CourseService courseService;

    @Test
    public void testSave() throws SQLException, DAOException, ServiceException {

    }
}
