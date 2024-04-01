package ua.foxminded.yevhenpiven;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import dao.CourseDao;
import dao.CourseDaoJdbc;
import dao.GroupDao;
import dao.GroupDaoJdbc;
import dao.StudentDao;
import dao.StudentDaoJdbc;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Configuration
public class SpringJdbcConfig {
    @Bean
    public DataSource postgresDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/school-console-app");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1234");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public StudentDao studentDao(JdbcTemplate jdbcTemplate) {
        return new StudentDaoJdbc(jdbcTemplate);
    }

    @Bean
    public GroupDao grouptDao(JdbcTemplate jdbcTemplate) {
        return new GroupDaoJdbc(jdbcTemplate);
    }

    @Bean
    public CourseDao courseDao(JdbcTemplate jdbcTemplate) {
        return new CourseDaoJdbc(jdbcTemplate);
    }

}
