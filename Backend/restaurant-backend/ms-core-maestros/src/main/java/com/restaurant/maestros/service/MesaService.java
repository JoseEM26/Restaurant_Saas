package com.restaurant.maestros.service;

import com.restaurant.common.exception.BusinessException;
import com.restaurant.common.exception.ResourceNotFoundException;
import com.restaurant.maestros.dto.request.MesaRequest;
import com.restaurant.maestros.dto.response.MesaResponse;
import com.restaurant.maestros.entity.Mesa;
import com.restaurant.maestros.enums.EstadoMesa;
import com.restaurant.maestros.mapper.MesaMapper;
import com.restaurant.maestros.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    @Transactional(readOnly = true)
    public List<MesaResponse> listarTodas() {
        return mesaRepository.findAll().stream()
                .map(mesaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MesaResponse> listarLibres() {
        return mesaRepository.findByEstado(EstadoMesa.LIBRE).stream()
                .map(mesaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MesaResponse buscarPorId(Long id) {
        return mesaMapper.toResponse(findById(id));
    }

    @Transactional
    public MesaResponse crear(MesaRequest request) {
        if (mesaRepository.existsByNumero(request.getNumero())) {
            throw new BusinessException("Ya existe una mesa con ese número");
        }
        Mesa mesa = mesaMapper.toEntity(request);
        return mesaMapper.toResponse(mesaRepository.save(mesa));
    }

    @Transactional
    public MesaResponse cambiarEstado(Long id, EstadoMesa nuevoEstado) {
        Mesa mesa = findById(id);
        mesa.setEstado(nuevoEstado);
        return mesaMapper.toResponse(mesaRepository.save(mesa));
    }

    @Transactional
    public void eliminar(Long id) {
        Mesa mesa = findById(id);
        mesa.setActivo(false);
        mesaRepository.save(mesa);
    }

    public Mesa findById(Long id) {
        return mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa", id));
    }
}
