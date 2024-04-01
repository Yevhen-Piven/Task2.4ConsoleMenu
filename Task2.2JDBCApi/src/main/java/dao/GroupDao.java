package dao;

import java.util.List;
import entity.Group;

public interface GroupDao {
    void save(Group student);

    Group findById(int id);

    List<Group> findAll();

    void update(Group student);

    void delete(int id);
}
