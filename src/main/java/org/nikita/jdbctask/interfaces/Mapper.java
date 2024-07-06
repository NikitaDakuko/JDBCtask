package org.nikita.jdbctask.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface Mapper<T, DTO> {
    T fromDTO(DTO dto);

    DTO toDTO(T t);

    DTO fromResult(ResultSet resultSet);

    default List<DTO> listFromResult(ResultSet resultSet){
        List<DTO> dtos = new ArrayList<>();
        try {
            while (resultSet.next()){
                dtos.add(fromResult(resultSet));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException: "+ e.getMessage());
        }
        return dtos;
    }
}
