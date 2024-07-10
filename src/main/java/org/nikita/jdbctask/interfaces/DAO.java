package org.nikita.jdbctask.interfaces;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface DAO<T> {
    default ResultSet defaultFindById(Connection connection, String tableName, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
        statement.setLong(1, id);
        return statement.executeQuery();
    }

    default ResultSet defaultGetAll(Connection connection, String tableName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName);
        return statement.executeQuery();
    }

    default void defaultDelete(Connection connection, String tableName, long id) throws SQLException {
        if (connection.createStatement().executeUpdate(
                "DELETE FROM " + tableName +
                        " WHERE id=" + id) != 1) throw new SQLException();
    }

    default List<Long> returnId(ResultSet resultSet) throws SQLException {
        List<Long> insertedIds = new ArrayList<>();
        while (resultSet.next()) {
            insertedIds.add(resultSet.getLong(1));
        }
        return insertedIds;
    }

    default List<Long> create(T item) throws SQLException {
        List<T> items = new ArrayList<>();
        items.add(item);
        return create(items);
    };

    List<Long> create(List<T> items) throws SQLException;

    T findById(Long id) throws SQLException;

    List<T> getAll() throws SQLException;

    default void update(T newItem) throws SQLException {
        List<T> newItems = new ArrayList<>();
        newItems.add(newItem);
        update(newItems);
    };

    void update(List<T> newItems) throws SQLException;

    void delete(Long id) throws SQLException;
}
