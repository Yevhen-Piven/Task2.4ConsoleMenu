package services;

import java.sql.SQLException;
import java.util.List;
import exception.DAOException;
import exception.ServiceException;

public interface EntityService<T> {
    public void save(List<T> data) throws SQLException, DAOException, ServiceException;
}
