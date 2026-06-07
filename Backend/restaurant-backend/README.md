# Restaurant Backend - Microservicios Spring Cloud

## Arquitectura

```
                    ┌─────────────────┐
  Cliente HTTP ───► │   api-gateway   │ :8080  (JWT + Rate Limiting Redis)
                    └────────┬────────┘
                             │ lb://  (Eureka)
              ┌──────────────┼──────────────┐
              ▼              ▼              ▼
        ┌──────────┐  ┌───────────┐  ┌──────────────┐
        │ ms-auth  │  │ ms-maest  │  │  ms-ventas   │
        │  :8081   │  │  :8082   │  │    :8083     │
        └────┬─────┘  └─────┬─────┘  └──────┬───────┘
             │ db_auth      │ db_maestros    │ db_ventas
             │              │ (Feign ◄───────┘)
             └──────────────┴──────────────────┐
                                               │ RabbitMQ
                                         ┌─────▼────────────┐
                                         │ ms-notificaciones│
                                         │     :8084        │
                                         └──────────────────┘
```

## Stack
- Java 21 + Spring Boot 3.3.5
- Spring Cloud 2023.0.3 (Eureka, Gateway, OpenFeign, Resilience4j)
- PostgreSQL (local) + Flyway (migraciones)
- RabbitMQ (Docker) + Redis (Docker)
- MapStruct + Lombok
- SpringDoc OpenAPI (Swagger)

## Arranque rápido

### 1. Crear las bases de datos en PostgreSQL local
```sql
CREATE DATABASE db_auth;
CREATE DATABASE db_maestros;
CREATE DATABASE db_ventas;
```

### 2. Levantar RabbitMQ y Redis
```bash
docker-compose up -d
```

### 3. Arrancar microservicios en este orden
```
eureka-server  → ms-auth-security → ms-core-maestros → ms-ventas → ms-notificaciones → api-gateway
```

## URLs de acceso
| Servicio | URL |
|---------|-----|
| Eureka Dashboard | http://localhost:8761 |
| Swagger Auth | http://localhost:8081/swagger-ui.html |
| Swagger Maestros | http://localhost:8082/swagger-ui.html |
| Swagger Ventas | http://localhost:8083/swagger-ui.html |
| Gateway (unificado) | http://localhost:8080/swagger-ui.html |
| RabbitMQ Management | http://localhost:15672 (guest/guest) |

## Flujo principal
1. `POST /api/auth/login` → obtener JWT
2. `POST /api/maestros/productos` → crear productos en el menú
3. `POST /api/ventas/pedidos` → crear pedido (verifica mesa via Feign, publica en RabbitMQ)
4. `PATCH /api/ventas/pedidos/{id}/estado?estado=CERRADO` → cerrar pedido (libera mesa)
