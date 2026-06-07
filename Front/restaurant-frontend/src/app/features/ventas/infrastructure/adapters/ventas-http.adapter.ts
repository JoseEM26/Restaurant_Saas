import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { ApiResponse } from '../../../../core/models/api-response.model';
import { Pedido, PedidoRequest } from '../../domain/models/ventas.model';

@Injectable({ providedIn: 'root' })
export class VentasHttpAdapter {
  private http = inject(HttpClient);
  private url  = environment.apiVentas;

  getPedidos(): Observable<Pedido[]> {
    return this.http.get<ApiResponse<Pedido[]>>(`${this.url}/pedidos`).pipe(map(r => r.data));
  }

  getPedidosAbiertos(): Observable<Pedido[]> {
    return this.http.get<ApiResponse<Pedido[]>>(`${this.url}/pedidos/abiertos`).pipe(map(r => r.data));
  }

  createPedido(data: PedidoRequest): Observable<Pedido> {
    return this.http.post<ApiResponse<Pedido>>(`${this.url}/pedidos`, data).pipe(map(r => r.data));
  }

  cambiarEstado(id: number, estado: string): Observable<Pedido> {
    return this.http.patch<ApiResponse<Pedido>>(
      `${this.url}/pedidos/${id}/estado?estado=${estado}`, {}
    ).pipe(map(r => r.data));
  }
}
