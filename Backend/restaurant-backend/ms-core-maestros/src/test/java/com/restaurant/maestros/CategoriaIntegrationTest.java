package com.restaurant.maestros;

import com.restaurant.maestros.entity.Categoria;
import com.restaurant.maestros.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@DisplayName("CategoriaRepository - Tests de integración con Testcontainers")
class CategoriaIntegrationTest {

    // Levanta un PostgreSQL real en Docker para el test
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("db_maestros_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configurarDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.flyway.enabled", () -> "false");
    }

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    @DisplayName("Guardar y recuperar categoría de la BD real")
    void guardarYRecuperar() {
        Categoria categoria = Categoria.builder()
                .nombre("Test Categoria")
                .descripcion("Para test de integración")
                .build();

        Categoria saved = categoriaRepository.save(categoria);

        assertThat(saved.getId()).isNotNull();
        assertThat(categoriaRepository.findById(saved.getId())).isPresent();
    }
}
