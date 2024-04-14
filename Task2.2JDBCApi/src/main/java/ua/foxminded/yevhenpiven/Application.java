package ua.foxminded.yevhenpiven;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.datasource.init.ScriptException;

import configuration.SpringJdbcConfig;
import execute.Executor;

@SpringBootApplication
public class Application {
    private static final String FILE_INIT_SQL = "init.sql";

    public static void main(String[] args) throws ScriptException, IOException, SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);
        Executor executor = context.getBean(Executor.class);
        try {
            executor.execute(FILE_INIT_SQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.close();
    }
}
