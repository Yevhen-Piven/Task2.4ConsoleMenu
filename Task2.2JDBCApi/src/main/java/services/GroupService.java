package services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.GroupDao;
import dao.GroupRepository;
import entity.Group;
import exception.DAOException;
import exception.ServiceException;

@Service
public class GroupService implements EntityService<Group> {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Autowired
    public GroupDao groupDao;

    @Override
    public void save(List<Group> data) throws SQLException, DAOException, ServiceException {
        for (Group group : data) {
            groupDao.save(group);
        }
    }

    public List<Group> findGroupsWithEqualOrFewerStudents(int maxStudentCount) {
        return groupRepository.findGroupsWithEqualOrFewerStudents(maxStudentCount);
    }
}
