package org.nikita.jdbctask.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DTOmapper<DTO> {
    DTO fromResult(ResultSet resultSet);

    default List<DTO> listFromResult(ResultSet resultSet){
        List<DTO> dtos = new ArrayList<>();
        try {
            while (resultSet.next()){
                dtos.add(fromResult(resultSet));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException while parsing resultSet: "+ e.getMessage());
        }
        return dtos;
    }
}
