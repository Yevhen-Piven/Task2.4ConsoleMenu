package ua.foxminded.yevhenpiven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dao.CourseDao;
import dao.GroupDao;
import dao.StudentDao;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);
        StudentDao studentDao = context.getBean(StudentDao.class);
        GroupDao groupDao = context.getBean(GroupDao.class);
        CourseDao courseDao = context.getBean(CourseDao.class);
        
        context.close();
        //SpringApplication.run(Application.class, args);
    }

}
