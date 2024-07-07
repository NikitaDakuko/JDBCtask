package org.nikita.jdbctask.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface DAO<T> {
    default ResultSet defaultFindById(Connection connection, String tableName, Long id){
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE id=" + id);
        }
        catch (SQLException e) {
            System.out.println(
                    "Could not find " + tableName + " record with id = " + id + ", SQLException: "+ e.getMessage());
        }
        return null;
    }

    default ResultSet defaultGetAll(Connection connection, String tableName){
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(
                    "SELECT * FROM " + tableName);
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

    ResultSet findById(Long id);

    ResultSet getAll();

    void update(T newItem);

    void delete(Long id);
}
