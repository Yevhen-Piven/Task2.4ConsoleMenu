package ua.foxminded.yevhenpiven;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import configuration.SpringJdbcConfig;

@SpringBootTest
class ApplicationTests {

    @Test
    public void testApplicationContextCreation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringJdbcConfig.class);
        assertNotNull(context);
        context.close();
    }
}
