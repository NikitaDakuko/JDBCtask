package org.nikita.jdbctask.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DTOmapper<DTO> {
    DTO fromResult(ResultSet resultSet, Connection connection);

    default List<DTO> listFromResult(ResultSet resultSet, Connection connection){
        List<DTO> dtos = new ArrayList<>();
        try {
            while (resultSet.next()){
                dtos.add(fromResult(resultSet, connection));
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException while parsing resultSet: "+ e.getMessage());
        }
        return dtos;
    }
}
