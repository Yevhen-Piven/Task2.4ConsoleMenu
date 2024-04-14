package ua.foxminded.yevhenpiven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import execute.Executor;

@SpringBootApplication
public class Application {
    private static final String FILE_INIT_SQL = "init.sql";

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        Executor executor = context.getBean(Executor.class);
        try {
            executor.execute(FILE_INIT_SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.close();
    }
}
