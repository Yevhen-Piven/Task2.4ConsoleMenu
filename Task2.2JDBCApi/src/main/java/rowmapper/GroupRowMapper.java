package rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import entity.Group;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Group group = new Group();
        group.setGroupId(resultSet.getInt("group_id"));
        group.setGroupName(resultSet.getString("group_name"));
        return group;
    }
}
