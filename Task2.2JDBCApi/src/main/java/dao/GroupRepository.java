package dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import entity.Group;
import rowmapper.GroupRowMapper;

@Repository
public class GroupRepository implements GroupDao {

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

    @Override
    public void update(Group group) {
        jdbcTemplate.update(UPDATE_GROUP_QUERY, group.getGroupName(), group.getGroupId());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_GROUP_QUERY, id);
    }
}
