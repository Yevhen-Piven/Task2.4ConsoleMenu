package dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import entity.Group;

@Repository
public class GroupDaoJdbc implements GroupDao {
    private final JdbcTemplate jdbcTemplate;

    public GroupDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Group student) {
        // TODO Auto-generated method stub

    }

    @Override
    public Group findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Group student) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub

    }

}
