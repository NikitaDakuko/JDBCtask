package org.nikita.jdbctask.interfaces;

import java.sql.ResultSet;

public interface DAO<T> {

    void create(T item);

    ResultSet findById(Long id);

    ResultSet getAll();

    void update(T newItem);

    void delete(Long id);
}
