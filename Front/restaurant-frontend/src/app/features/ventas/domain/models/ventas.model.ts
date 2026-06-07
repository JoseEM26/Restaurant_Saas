export interface DetallePedidoRequest {
  productoId: number;
  cantidad: number;
  notas?: string;
}

export interface PedidoRequest {
  mesaId: number;
  clienteId?: number;
  observaciones?: string;
  detalles: DetallePedidoRequest[];
}

export interface DetallePedido {
  id: number;
  productoId: number;
  productoNombre: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
  notas?: string;
}

export interface Pedido {
  id: number;
  mesaId: number;
  clienteId?: number;
  estado: 'ABIERTO' | 'EN_PROCESO' | 'LISTO' | 'CERRADO' | 'CANCELADO';
  total: number;
  observaciones?: string;
  fechaCreacion: string;
  detalles: DetallePedido[];
}

export interface DashboardStats {
  totalPedidosHoy: number;
  ingresosDiarios: number;
  mesasOcupadas: number;
  pedidosAbiertos: number;
}
