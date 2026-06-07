export interface Categoria {
  id?: number;
  nombre: string;
  descripcion?: string;
  activo?: boolean;
}

export interface Producto {
  id?: number;
  nombre: string;
  descripcion?: string;
  precio: number;
  disponible: boolean;
  categoriaId: number;
  categoriaNombre?: string;
}

export interface Mesa {
  id?: number;
  numero: number;
  capacidad: number;
  estado: 'LIBRE' | 'OCUPADA' | 'RESERVADA';
}

export interface Cliente {
  id?: number;
  nombre: string;
  apellido?: string;
  telefono?: string;
  email?: string;
}
