package com.restaurant.maestros;

import com.restaurant.common.exception.BusinessException;
import com.restaurant.maestros.dto.request.CategoriaRequest;
import com.restaurant.maestros.dto.response.CategoriaResponse;
import com.restaurant.maestros.entity.Categoria;
import com.restaurant.maestros.mapper.CategoriaMapper;
import com.restaurant.maestros.repository.CategoriaRepository;
import com.restaurant.maestros.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoriaService - Tests unitarios")
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    private CategoriaRequest request;
    private Categoria categoria;
    private CategoriaResponse response;

    @BeforeEach
    void setUp() {
        request = new CategoriaRequest();
        request.setNombre("Entradas");
        request.setDescripcion("Platos de entrada");

        categoria = Categoria.builder()
                .nombre("Entradas")
                .descripcion("Platos de entrada")
                .build();

        response = CategoriaResponse.builder()
                .id(1L)
                .nombre("Entradas")
                .descripcion("Platos de entrada")
                .activo(true)
                .build();
    }

    @Test
    @DisplayName("Crear categoría exitosamente")
    void crear_exitoso() {
        when(categoriaRepository.existsByNombreIgnoreCase("Entradas")).thenReturn(false);
        when(categoriaMapper.toEntity(request)).thenReturn(categoria);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        when(categoriaMapper.toResponse(categoria)).thenReturn(response);

        CategoriaResponse resultado = categoriaService.crear(request);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Entradas");
        verify(categoriaRepository).save(any(Categoria.class));
    }

    @Test
    @DisplayName("Crear categoría duplicada lanza BusinessException")
    void crear_nombreDuplicado_lanzaExcepcion() {
        when(categoriaRepository.existsByNombreIgnoreCase("Entradas")).thenReturn(true);

        assertThatThrownBy(() -> categoriaService.crear(request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Ya existe una categoría");

        verify(categoriaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Buscar por ID existente devuelve categoría")
    void buscarPorId_existente_devuelveCategoria() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponse(categoria)).thenReturn(response);

        CategoriaResponse resultado = categoriaService.buscarPorId(1L);

        assertThat(resultado.getId()).isEqualTo(1L);
    }
}
