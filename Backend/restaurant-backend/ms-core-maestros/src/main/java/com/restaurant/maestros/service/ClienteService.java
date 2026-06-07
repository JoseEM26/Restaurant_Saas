package com.restaurant.maestros.service;

import com.restaurant.common.exception.BusinessException;
import com.restaurant.common.exception.ResourceNotFoundException;
import com.restaurant.maestros.dto.request.ClienteRequest;
import com.restaurant.maestros.dto.response.ClienteResponse;
import com.restaurant.maestros.entity.Cliente;
import com.restaurant.maestros.mapper.ClienteMapper;
import com.restaurant.maestros.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Transactional(readOnly = true)
    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        return clienteMapper.toResponse(findById(id));
    }

    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        if (request.getEmail() != null && clienteRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Ya existe un cliente con ese email");
        }
        Cliente cliente = clienteMapper.toEntity(request);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = findById(id);
        clienteMapper.updateEntity(request, cliente);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = findById(id);
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
    }
}
