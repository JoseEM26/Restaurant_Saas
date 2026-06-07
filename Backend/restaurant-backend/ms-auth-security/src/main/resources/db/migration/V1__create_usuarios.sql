CREATE TABLE IF NOT EXISTS usuarios (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    rol         VARCHAR(20)  NOT NULL,
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Usuario admin por defecto (password: admin123)
INSERT INTO usuarios (username, password, email, rol)
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@restaurant.pe', 'ADMIN')
ON CONFLICT DO NOTHING;
