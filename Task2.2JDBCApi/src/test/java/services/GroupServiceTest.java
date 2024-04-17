package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import dao.GroupDao;
import dao.GroupRepository;
import entity.Group;
import exception.DAOException;
import exception.ServiceException;
import rowmapper.GroupRowMapper;

@SpringBootTest(classes = { GroupService.class })
class GroupServiceTest {
    private static final int TEST_GROUP_ID = 1;
    private static final int TEST_SECOND_GROUP_ID = 2;
    private static final String TEST_GROUP_NAME = "Group 1";
    private static final String TEST_SECOND_GROUP_NAME = "Group 2";
    private static final String TEST_QUERY_SELECT = "SELECT * FROM school.groups";
    private static final String TEST_QUERY_UPDATE = "UPDATE school.groups SET group_name = ? WHERE group_id = ?";
    private static final String TEST_QUERY_DELETE = "DELETE FROM school.groups WHERE group_id = ?";

    @MockBean
    GroupDao groupDao;
    JdbcTemplate jdbcTemplate;

    @Autowired
    GroupService groupService;

    @Test
    public void testSave() throws SQLException, DAOException, ServiceException {
        GroupDao groupDaoMock = mock(GroupDao.class);
        List<Group> testData = new ArrayList<>();
        testData.add(new Group(TEST_GROUP_ID, TEST_GROUP_NAME));
        groupService.save(testData);
        for (Group group : testData) {
            verify(groupDaoMock).save(group);
        }
    }

    @Test
    public void testFindGroupsWithEqualOrFewerStudents() {
        GroupRepository groupRepositoryMock = mock(GroupRepository.class);
        GroupService groupService = new GroupService(jdbcTemplate, groupRepositoryMock);
        int maxStudentCount = 10;
        List<Group> expected = new ArrayList<>();
        when(groupRepositoryMock.findGroupsWithEqualOrFewerStudents(maxStudentCount)).thenReturn(expected);
        List<Group> actual = groupService.findGroupsWithEqualOrFewerStudents(maxStudentCount);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindById() {
        GroupRepository groupRepositoryMock = mock(GroupRepository.class);
        JdbcTemplate jdbcTemplateMock = mock(JdbcTemplate.class);
        GroupService groupService = new GroupService(jdbcTemplateMock, groupRepositoryMock);
        Group expected = new Group();
        when(jdbcTemplateMock.queryForObject(anyString(), new GroupRowMapper(), eq(TEST_GROUP_ID)))
                .thenReturn(expected);
        Group actual = groupService.findById(TEST_GROUP_ID);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindAll() {
        jdbcTemplate = mock(JdbcTemplate.class);
        GroupDao groupDao = mock(GroupDao.class);
        List<Group> testGroups = new ArrayList<>();
        testGroups.add(new Group(TEST_GROUP_ID, TEST_GROUP_NAME));
        testGroups.add(new Group(TEST_SECOND_GROUP_ID, TEST_SECOND_GROUP_NAME));
        when(jdbcTemplate.query(TEST_QUERY_SELECT, new GroupRowMapper())).thenReturn(testGroups);
        List<Group> result = groupDao.findAll();

        assertEquals(2, result.size());
        assertEquals(TEST_GROUP_NAME, result.get(0).getGroupName());
        assertEquals(TEST_SECOND_GROUP_NAME, result.get(1).getGroupName());
    }

    @Test
    public void testUpdate() {
        jdbcTemplate = mock(JdbcTemplate.class);
        GroupDao groupDao = mock(GroupDao.class);
        Group group = new Group(TEST_GROUP_ID, TEST_GROUP_NAME);
        groupDao.update(group);
        verify(jdbcTemplate).update(TEST_QUERY_UPDATE, TEST_GROUP_NAME, TEST_GROUP_ID);
    }

    @Test
    public void testDelete() {
        jdbcTemplate = mock(JdbcTemplate.class);
        GroupDao groupDao = mock(GroupDao.class);
        groupDao.delete(TEST_GROUP_ID);
        verify(jdbcTemplate).update(TEST_QUERY_DELETE, TEST_GROUP_ID);
    }
}
