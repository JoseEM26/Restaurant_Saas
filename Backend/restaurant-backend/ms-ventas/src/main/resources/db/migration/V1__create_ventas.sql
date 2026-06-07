CREATE TABLE IF NOT EXISTS pedidos (
    id              BIGSERIAL       PRIMARY KEY,
    mesa_id         BIGINT          NOT NULL,
    cliente_id      BIGINT,
    estado          VARCHAR(20)     NOT NULL DEFAULT 'ABIERTO',
    total           NUMERIC(10,2)   NOT NULL DEFAULT 0,
    observaciones   VARCHAR(255),
    activo          BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS detalles_pedido (
    id               BIGSERIAL       PRIMARY KEY,
    pedido_id        BIGINT          NOT NULL REFERENCES pedidos(id),
    producto_id      BIGINT          NOT NULL,
    producto_nombre  VARCHAR(150)    NOT NULL,
    cantidad         INTEGER         NOT NULL,
    precio_unitario  NUMERIC(10,2)   NOT NULL,
    subtotal         NUMERIC(10,2)   NOT NULL,
    notas            VARCHAR(255),
    activo           BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMP       DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);
