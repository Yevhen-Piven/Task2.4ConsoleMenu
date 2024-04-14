package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entity.Group;
import rowmapper.GroupRowMapper;

@Repository
public class GroupRepository implements GroupDao {
    private static final String QUERY_FIND_GROUP_EQUAL_FEWER_STUDENTS = "SELECT student_groups.group_id, student_groups.group_name, COUNT(students.student_id) AS student_count  \r\n"
            + "FROM  school.student_groups LEFT JOIN  school.students ON student_groups.group_id = students.group_id  \r\n"
            + "GROUP BY student_groups.group_id, student_groups.group_name HAVING COUNT(students.student_id) <= ?";
    private static final String SELECT_ALL = "SELECT * FROM school.groups";
    private static final String SELECT_GROUP_BY_ID = "SELECT * FROM school.groups WHERE group_id=?";
    private static final String INSERT_GROUP_QUERY = "INSERT INTO school.groups (group_id, group_name) VALUES (?, ?)";
    private static final String UPDATE_GROUP_QUERY = "UPDATE school.groups SET group_name=? WHERE group_id=?";
    private static final String DELETE_GROUP_QUERY = "DELETE FROM school.groups WHERE group_id=?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void save(Group group) {
        jdbcTemplate.update(INSERT_GROUP_QUERY, group.getGroupName());
    }

    @Override
    public Group findById(long id) {
        return jdbcTemplate.queryForObject(SELECT_GROUP_BY_ID, new GroupRowMapper(), id);
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new GroupRowMapper());
    }

    @Transactional
    @Override
    public void update(Group group) {
        jdbcTemplate.update(UPDATE_GROUP_QUERY, group.getGroupName(), group.getGroupId());
    }

    @Transactional
    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_GROUP_QUERY, id);
    }

    public List<Group> findGroupsWithEqualOrFewerStudents(int maxStudentCount) {
        return jdbcTemplate.query(QUERY_FIND_GROUP_EQUAL_FEWER_STUDENTS, new GroupRowMapper());
    }
}
