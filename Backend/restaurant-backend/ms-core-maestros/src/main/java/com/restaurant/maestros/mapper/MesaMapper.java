package com.restaurant.maestros.mapper;

import com.restaurant.maestros.dto.request.MesaRequest;
import com.restaurant.maestros.dto.response.MesaResponse;
import com.restaurant.maestros.entity.Mesa;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MesaMapper {

    @Mapping(target = "estado", expression = "java(mesa.getEstado().name())")
    MesaResponse toResponse(Mesa mesa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "estado", ignore = true)
    Mesa toEntity(MesaRequest request);
}
