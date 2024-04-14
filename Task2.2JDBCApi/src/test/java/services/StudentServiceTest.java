package services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dao.StudentDao;

@SpringBootTest(classes = { StudentService.class })
class StudentServiceTest {
    @MockBean
    StudentDao studentDao;

    @Autowired
    StudentService studentService;

    @Test
    void test() {
    }

}
