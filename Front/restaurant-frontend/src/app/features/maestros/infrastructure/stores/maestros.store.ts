import { Injectable, signal, computed } from '@angular/core';
import { Categoria, Producto, Mesa, Cliente } from '../../domain/models/maestros.model';

@Injectable({ providedIn: 'root' })
export class MaestrosStoreService {
  // Signals por entidad
  private _categorias = signal<Categoria[]>([]);
  private _productos  = signal<Producto[]>([]);
  private _mesas      = signal<Mesa[]>([]);
  private _clientes   = signal<Cliente[]>([]);
  private _loading    = signal(false);
  private _error      = signal<string | null>(null);

  // API pública
  readonly categorias    = this._categorias.asReadonly();
  readonly productos     = this._productos.asReadonly();
  readonly mesas         = this._mesas.asReadonly();
  readonly clientes      = this._clientes.asReadonly();
  readonly loading       = this._loading.asReadonly();
  readonly error         = this._error.asReadonly();
  readonly mesasLibres   = computed(() => this._mesas().filter(m => m.estado === 'LIBRE'));
  readonly productosDisponibles = computed(() => this._productos().filter(p => p.disponible));

  setCategorias(list: Categoria[]) { this._categorias.set(list); }
  setProductos(list: Producto[])   { this._productos.set(list); }
  setMesas(list: Mesa[])           { this._mesas.set(list); }
  setClientes(list: Cliente[])     { this._clientes.set(list); }
  setLoading(v: boolean)           { this._loading.set(v); }
  setError(e: string | null)       { this._error.set(e); }

  // Effects sobre mesas
  actualizarEstadoMesa(id: number, estado: Mesa['estado']): void {
    this._mesas.update(mesas =>
      mesas.map(m => m.id === id ? { ...m, estado } : m)
    );
  }
}
