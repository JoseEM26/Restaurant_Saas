import { Injectable, signal, computed } from '@angular/core';
import { Pedido } from '../../domain/models/ventas.model';

@Injectable({ providedIn: 'root' })
export class VentasStoreService {
  private _pedidos  = signal<Pedido[]>([]);
  private _loading  = signal(false);
  private _error    = signal<string | null>(null);

  readonly pedidos          = this._pedidos.asReadonly();
  readonly loading          = this._loading.asReadonly();
  readonly error            = this._error.asReadonly();
  readonly pedidosAbiertos  = computed(() => this._pedidos().filter(p => p.estado === 'ABIERTO'));
  readonly totalIngresos    = computed(() =>
    this._pedidos()
      .filter(p => p.estado === 'CERRADO')
      .reduce((acc, p) => acc + p.total, 0)
  );

  setPedidos(list: Pedido[])  { this._pedidos.set(list); }
  setLoading(v: boolean)      { this._loading.set(v); }
  setError(e: string | null)  { this._error.set(e); }

  actualizarEstado(id: number, estado: Pedido['estado']): void {
    this._pedidos.update(list =>
      list.map(p => p.id === id ? { ...p, estado } : p)
    );
  }
}
