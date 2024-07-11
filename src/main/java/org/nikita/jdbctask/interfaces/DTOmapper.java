package org.nikita.jdbctask.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for creating mappers for parsing DTOs from ResultSet
 * @param <DTO> Data Transfer Object type to be parsed
 */
public interface DTOmapper<DTO> {
    /**
     * Method for parsing a single DTO from a ResultSet
     * @param resultSet ResultSet of the query
     * @param connection Connection to the database
     * @return DTO, parsed from the ResultSet
     */
    DTO fromResult(ResultSet resultSet, Connection connection);

    /**
     * Method for parsing a List of DTOs from a ResultSet
     * @param resultSet ResultSet of the query
     * @param connection Connection to the database
     * @return DTOs, parsed from the ResultSet
     */
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
