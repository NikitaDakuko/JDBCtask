package org.nikita.jdbctask.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface EntityMapper<T, DTO> {
    T fromDTO(DTO dto);

    DTO toDTO(T t);


    default List<DTO> toDTO(List<T> ts){
        List<DTO> dtos = new ArrayList<>();

        for(T t:ts)
            dtos.add(toDTO(t));

        return dtos;
    }

    default List<T> fromDTO(List<DTO> dtos){
        List<T> tList = new ArrayList<>();

        for(DTO dto:dtos)
            tList.add(fromDTO(dto));

        return tList;
    }
}
