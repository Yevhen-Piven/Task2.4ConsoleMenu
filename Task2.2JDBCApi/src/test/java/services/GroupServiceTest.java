package services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import dao.GroupDao;

@SpringBootTest(classes = { GroupService.class })
class GroupServiceTest {
    @MockBean
    GroupDao groupDao;

    @Autowired
    GroupService groupService;

    @Test
    void test() {
    }
}
