package org.nikita.jdbctask.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T, DTO> {
    public T fromDTO(DTO dto);

    public DTO toDTO(T t);

    public DTO fromResult(ResultSet resultSet) throws SQLException;
}
