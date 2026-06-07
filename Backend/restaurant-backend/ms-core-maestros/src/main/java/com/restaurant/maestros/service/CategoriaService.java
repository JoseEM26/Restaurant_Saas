package com.restaurant.maestros.service;

import com.restaurant.common.exception.BusinessException;
import com.restaurant.common.exception.ResourceNotFoundException;
import com.restaurant.maestros.dto.request.CategoriaRequest;
import com.restaurant.maestros.dto.response.CategoriaResponse;
import com.restaurant.maestros.entity.Categoria;
import com.restaurant.maestros.mapper.CategoriaMapper;
import com.restaurant.maestros.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Long id) {
        return categoriaMapper.toResponse(findById(id));
    }

    @Transactional
    public CategoriaResponse crear(CategoriaRequest request) {
        if (categoriaRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new BusinessException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = categoriaMapper.toEntity(request);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = findById(id);
        categoriaMapper.updateEntity(request, categoria);
        return categoriaMapper.toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = findById(id);
        categoria.setActivo(false);
        categoriaRepository.save(categoria);
    }

    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
    }
}
