package ua.foxminded.yevhenpiven;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import dao.GroupDao;
import dao.GroupRepository;
import entity.Group;
import rowmapper.GroupRowMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = { "/sql/clear_tables.sql",
        "/sql/sample_data.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

class GroupRepositoryTest {
    public static final String INSERT = "SELECT COUNT(*) FROM school.group WHERE course_id = ?";
    public static final String SELECT = "SELECT * FROM school.group WHERE course_id = ?";
    public static final String INSERT_INTO = "INSERT INTO students (student_id, group_id, first_name, last_name) VALUES (?, ?, ?, ?)";
    public static final String TEST_GROUP_NAME = "groupName";
    public static final int TEST_GROUP_ID = 11;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private GroupDao groupDao;

    @BeforeEach
    void setUp() {
        groupDao = new GroupRepository(jdbcTemplate);
    }

    @Test
    public void testSave() {
        Group group = new Group(TEST_GROUP_ID, TEST_GROUP_NAME);
        groupDao.save(group);
        int count = jdbcTemplate.queryForObject(INSERT, Integer.class, TEST_GROUP_ID);
        assertEquals(1, count);
    }

    @Test
    public void testFindById() {
        Group foundGroup = groupDao.findById(TEST_GROUP_ID);
        assertNotNull(foundGroup);
        assertEquals(TEST_GROUP_ID, foundGroup.getGroupId());
        assertEquals(TEST_GROUP_NAME, foundGroup.getGroupName());
    }

    @Test
    public void testFindAll() {
        List<Group> group = groupDao.findAll();
        assertNotNull(group);
        assertEquals(1, group.size());
        Group testGroup = group.get(0);
        assertEquals(TEST_GROUP_ID, testGroup.getGroupId());
        assertEquals(TEST_GROUP_NAME, testGroup.getGroupName());
    }

    @Test
    public void testUpdate() {
        Group updatedGroup = new Group();
        updatedGroup.setGroupId(TEST_GROUP_ID);
        updatedGroup.setGroupName(TEST_GROUP_NAME);
        groupDao.update(updatedGroup);
        Group testGroup = jdbcTemplate.queryForObject(SELECT, new GroupRowMapper(), updatedGroup.getGroupId());
        assertNotNull(testGroup);
        assertEquals(updatedGroup.getGroupName(), testGroup.getGroupName());
    }

    @Test
    public void testDelete() {
        groupDao.delete(1);
        Group deletedGroup = jdbcTemplate.queryForObject(SELECT, new GroupRowMapper());
        assertNull(deletedGroup);
    }
}
