package org.nikita.jdbctask.interfaces;

import java.sql.*;
import java.util.List;

public interface DAO<T> {
    default ResultSet defaultFindById(Connection connection, String tableName, Long id){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setLong(1, id);
            return statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not find " + tableName + " record with id = " + id + ", SQLException: "+ e.getMessage());
        }
        return null;
    }

    default ResultSet defaultGetAll(Connection connection, String tableName){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName);
            return statement.executeQuery();
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not find " + tableName + " record, SQLException: "+ e.getMessage());
        }
        return null;
    }

    default void defaultDelete(Connection connection, String tableName, long id){
        try {
            if(connection.createStatement().executeUpdate(
                    "DELETE FROM " + tableName +
                            " WHERE id=" + id) != 1) throw new SQLException();
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not delete " + tableName + " record with id = " + id + ", SQLException: "+ e.getMessage());
        }
    }

    void create(T item);

    T findById(Long id);

    List<T> getAll();

    void update(T newItem);

    void delete(Long id);
}
