package org.nikita.jdbctask.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DTOmapper<DTO> {
    DTO fromResult(ResultSet resultSet);

    default DTO singleFromResult(ResultSet resultSet){
        try {
            if (resultSet.next())
                return fromResult(resultSet);
        } catch (SQLException e) {
            System.out.println("SQLException while parsing resultSet: "+ e.getMessage());
        }
        return null;
    }

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
