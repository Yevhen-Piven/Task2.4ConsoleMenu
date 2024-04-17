package services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.GroupDao;
import dao.GroupRepository;
import entity.Group;
import exception.DAOException;
import exception.ServiceException;
import rowmapper.GroupRowMapper;

@Service
public class GroupService {

    private static final String SELECT_ALL = "SELECT * FROM school.groups";
    private static final String SELECT_GROUP_BY_ID = "SELECT * FROM school.groups WHERE group_id=?";
    private static final String UPDATE_GROUP_QUERY = "UPDATE school.groups SET group_name=? WHERE group_id=?";
    private static final String DELETE_GROUP_QUERY = "DELETE FROM school.groups WHERE group_id=?";
    private final GroupRepository groupRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupService(JdbcTemplate jdbcTemplate, GroupRepository groupRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupRepository = groupRepository;
    }

    @Autowired
    public GroupDao groupDao;

    public void save(List<Group> data) throws SQLException, DAOException, ServiceException {
        for (Group group : data) {
            groupDao.save(group);
        }
    }

    public List<Group> findGroupsWithEqualOrFewerStudents(int maxStudentCount) {
        return groupRepository.findGroupsWithEqualOrFewerStudents(maxStudentCount);
    }

    public Group findById(int id) {
        return jdbcTemplate.queryForObject(SELECT_GROUP_BY_ID, new GroupRowMapper(), id);
    }

    public List<Group> findAll() {
        return jdbcTemplate.query(SELECT_ALL, new GroupRowMapper());
    }

    @Transactional
    public void update(Group group) {
        jdbcTemplate.update(UPDATE_GROUP_QUERY, group.getGroupName(), group.getGroupId());
    }

    @Transactional
    public void delete(int id) {
        jdbcTemplate.update(DELETE_GROUP_QUERY, id);
    }
}
