package org.nikita.jdbctask.interfaces;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface DAO<T> {
    default ResultSet defaultFindById(Connection connection, String tableName, Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setLong(1, id);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Could not get " + tableName + " record by ID, SQLException: " + e);
            return null;
        }
    }

    default ResultSet defaultGetAll(Connection connection, String tableName) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Could not get " + tableName + " records, SQLException: " + e);
            return null;
        }
    }

    default void defaultDelete(Connection connection, String tableName, long id) {
        try {
            if (connection.createStatement().executeUpdate(
                    "DELETE FROM " + tableName +
                            " WHERE id=" + id) != 1) throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Could not delete " + tableName + " record by ID, SQLException: " + e);
        }
    }

    default void defaultDelete(Connection connection, String tableName, List<Long> ids) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM " + tableName +
                            " WHERE id IN ?");
            Array idArray = connection.createArrayOf("integer", ids.toArray());
            statement.setArray(1, idArray);

            if (statement.executeUpdate()!= 1) throw new SQLException();

        } catch (SQLException e) {
            System.out.println("Could not delete " + tableName + " records with IDs: " + ids + ", SQLException: " + e);
        }
    }

    default List<Long> returnId(ResultSet resultSet) {
        try {
            List<Long> insertedIds = new ArrayList<>();
            while (resultSet.next()) {
                insertedIds.add(resultSet.getLong(1));
            }
            return insertedIds;
        } catch (SQLException e) {
            System.out.println("Could not get return IDs. SQLException: " + e);
            return null;
        }
    }

    default List<Long> create(T item) {
        List<T> items = new ArrayList<>();
        items.add(item);
        return create(items);
    }

    List<Long> create(List<T> items);

    T findById(Long id);

    List<T> getAll();

    default void update(T newItem) {
        List<T> newItems = new ArrayList<>();
        newItems.add(newItem);
        update(newItems);
    }

    void update(List<T> newItems);

    void delete(Long id);
}
