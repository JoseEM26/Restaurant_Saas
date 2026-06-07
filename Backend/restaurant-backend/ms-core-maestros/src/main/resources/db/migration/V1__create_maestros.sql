CREATE TABLE IF NOT EXISTS categorias (
    id          BIGSERIAL    PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS productos (
    id           BIGSERIAL       PRIMARY KEY,
    nombre       VARCHAR(150)    NOT NULL,
    descripcion  VARCHAR(255),
    precio       NUMERIC(10,2)   NOT NULL,
    disponible   BOOLEAN         NOT NULL DEFAULT TRUE,
    categoria_id BIGINT          NOT NULL REFERENCES categorias(id),
    activo       BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS mesas (
    id         BIGSERIAL    PRIMARY KEY,
    numero     INTEGER      NOT NULL UNIQUE,
    capacidad  INTEGER      NOT NULL,
    estado     VARCHAR(20)  NOT NULL DEFAULT 'LIBRE',
    activo     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS clientes (
    id         BIGSERIAL    PRIMARY KEY,
    nombre     VARCHAR(100) NOT NULL,
    apellido   VARCHAR(100),
    telefono   VARCHAR(20),
    email      VARCHAR(150) UNIQUE,
    activo     BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Datos iniciales
INSERT INTO categorias (nombre, descripcion) VALUES
    ('Entradas', 'Platos de entrada y aperitivos'),
    ('Platos Principales', 'Platos de fondo'),
    ('Bebidas', 'Bebidas frías y calientes'),
    ('Postres', 'Dulces y postres')
ON CONFLICT DO NOTHING;

INSERT INTO mesas (numero, capacidad) VALUES
    (1, 2), (2, 4), (3, 4), (4, 6), (5, 8)
ON CONFLICT DO NOTHING;
