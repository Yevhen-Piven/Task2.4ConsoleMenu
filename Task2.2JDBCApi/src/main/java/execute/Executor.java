package execute;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.util.ReaderInputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;

public class Executor {
    private final JdbcTemplate jdbcTemplate;

    public Executor(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void execute(String filePath) throws IOException, ScriptException, SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new InputStreamResource(ReaderInputStream.class.getClassLoader().getResourceAsStream(filePath)));
    }
}
