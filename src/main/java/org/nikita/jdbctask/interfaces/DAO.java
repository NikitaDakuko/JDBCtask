package org.nikita.jdbctask.interfaces;

import java.util.List;

public interface DAO<T> {

    void create(T item);

    T findById(Long id);

    List<T> getAll();

    void update(T newItem);

    void delete(Long id);
}
