package com.restaurant.maestros.mapper;

import com.restaurant.maestros.dto.request.ProductoRequest;
import com.restaurant.maestros.dto.response.ProductoResponse;
import com.restaurant.maestros.entity.Producto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "categoriaNombre", source = "categoria.nombre")
    ProductoResponse toResponse(Producto producto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Producto toEntity(ProductoRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntity(ProductoRequest request, @MappingTarget Producto producto);
}
