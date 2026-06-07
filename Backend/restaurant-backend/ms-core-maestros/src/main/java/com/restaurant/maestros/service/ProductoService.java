package com.restaurant.maestros.service;

import com.restaurant.common.exception.ResourceNotFoundException;
import com.restaurant.maestros.dto.request.ProductoRequest;
import com.restaurant.maestros.dto.response.ProductoResponse;
import com.restaurant.maestros.entity.Categoria;
import com.restaurant.maestros.entity.Producto;
import com.restaurant.maestros.mapper.ProductoMapper;
import com.restaurant.maestros.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final CategoriaService categoriaService;

    @Transactional(readOnly = true)
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listarDisponibles() {
        return productoRepository.findByDisponibleTrue().stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId).stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse buscarPorId(Long id) {
        return productoMapper.toResponse(findById(id));
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        Categoria categoria = categoriaService.findById(request.getCategoriaId());
        Producto producto = productoMapper.toEntity(request);
        producto.setCategoria(categoria);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = findById(id);
        Categoria categoria = categoriaService.findById(request.getCategoriaId());
        productoMapper.updateEntity(request, producto);
        producto.setCategoria(categoria);
        return productoMapper.toResponse(productoRepository.save(producto));
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = findById(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }
}
