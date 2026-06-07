package com.restaurant.maestros.mapper;

import com.restaurant.maestros.dto.request.ClienteRequest;
import com.restaurant.maestros.dto.response.ClienteResponse;
import com.restaurant.maestros.entity.Cliente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteResponse toResponse(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Cliente toEntity(ClienteRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateEntity(ClienteRequest request, @MappingTarget Cliente cliente);
}
