package com.restaurant.maestros.mapper;

import com.restaurant.maestros.dto.request.CategoriaRequest;
import com.restaurant.maestros.dto.response.CategoriaResponse;
import com.restaurant.maestros.entity.Categoria;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaResponse toResponse(Categoria categoria);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "productos", ignore = true)
    Categoria toEntity(CategoriaRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "productos", ignore = true)
    void updateEntity(CategoriaRequest request, @MappingTarget Categoria categoria);
}
