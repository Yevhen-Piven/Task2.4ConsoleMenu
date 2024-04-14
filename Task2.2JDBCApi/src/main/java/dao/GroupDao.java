package dao;

import java.util.List;

import entity.Group;

public interface GroupDao {
    void save(Group group);

    Group findById(long id);

    List<Group> findAll();

    void update(Group group);

    void delete(int id);
}
