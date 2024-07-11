package org.nikita.jdbctask.interfaces;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object interface. Implements common queries
 * @param <T> DTO type for DAO interface
 */
public interface DAO<T> {
    /**
     * Typical find by ID query
     * @param connection Connection to the database
     * @param tableName Name of the table which should be searched
     * @param id ID of the record to be searched
     * @return ResultSet of the query
     */
    default ResultSet defaultFindById(Connection connection, String tableName, Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE id = ?");
            statement.setLong(1, id);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Could not get " + tableName + " record by ID, SQLException: " + e);
            return null;
        }
    }

    /**
     * Typical query to find all records
     * @param connection Connection to the database
     * @param tableName Name of the table which should be searched
     * @return ResultSet of the query
     */
    default ResultSet defaultGetAll(Connection connection, String tableName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName + "\n\"ORDER by oa.id;\"");
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Could not get " + tableName + " records, SQLException: " + e);
            return null;
        }
    }

    /**
     * Typical DELETE query
     * @param connection Connection to the database
     * @param tableName Name of the table which should be searched
     * @param id ID of the record to be deleted
     */
    default void defaultDelete(Connection connection, String tableName, long id) {
        try {
            if (connection.createStatement().executeUpdate(
                    "DELETE FROM " + tableName +
                            " WHERE id=" + id) != 1) throw new SQLException();
        } catch (SQLException e) {
            System.out.println("Could not delete " + tableName + " record by ID, SQLException: " + e);
        }
    }

    /**
     * polymorphic method for inserting only a single record
     * @param item DTO to be inserted into the database
     * @return IDs of the inserted records
     */
    default List<Long> create(T item) {
        List<T> items = new ArrayList<>();
        items.add(item);
        return create(items);
    }

    /**
     * method for inserting a list of DTOs into the database
     * @param items List of DTOs to be inserted into the database
     * @return IDs of the inserted records
     */
    List<Long> create(List<T> items);

    /**
     * method for finding a single record by its ID
     * @param id ID of the record to be found
     * @return DTO of the record
     */
    T findById(Long id);

    /**
     * method for getting all the records from the database
     * @return List of entity DTOs
     */
    List<T> getAll();

    /**
     * default method for updating a record in the database
     * @param newItem DTO of the record to be updated (must have an ID)
     */
    default void update(T newItem) {
        List<T> newItems = new ArrayList<>();
        newItems.add(newItem);
        update(newItems);
    }

    /**
     * method for updating a list of records in the database
     * @param newItems DTOs of records to be updated (must have an IDs)
     */
    void update(List<T> newItems);

    void delete(Long id);
}
