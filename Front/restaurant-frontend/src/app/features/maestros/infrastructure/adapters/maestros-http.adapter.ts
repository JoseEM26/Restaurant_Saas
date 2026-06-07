import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { ApiResponse } from '../../../../core/models/api-response.model';
import { Categoria, Producto, Mesa, Cliente } from '../../domain/models/maestros.model';

@Injectable({ providedIn: 'root' })
export class MaestrosHttpAdapter {
  private http = inject(HttpClient);
  private url  = environment.apiMaestros;

  // Categorías
  getCategorias(): Observable<Categoria[]> {
    return this.http.get<ApiResponse<Categoria[]>>(`${this.url}/categorias`).pipe(map(r => r.data));
  }
  createCategoria(data: Categoria): Observable<Categoria> {
    return this.http.post<ApiResponse<Categoria>>(`${this.url}/categorias`, data).pipe(map(r => r.data));
  }
  updateCategoria(id: number, data: Categoria): Observable<Categoria> {
    return this.http.put<ApiResponse<Categoria>>(`${this.url}/categorias/${id}`, data).pipe(map(r => r.data));
  }
  deleteCategoria(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/categorias/${id}`);
  }

  // Productos
  getProductos(): Observable<Producto[]> {
    return this.http.get<ApiResponse<Producto[]>>(`${this.url}/productos`).pipe(map(r => r.data));
  }
  createProducto(data: Producto): Observable<Producto> {
    return this.http.post<ApiResponse<Producto>>(`${this.url}/productos`, data).pipe(map(r => r.data));
  }
  updateProducto(id: number, data: Producto): Observable<Producto> {
    return this.http.put<ApiResponse<Producto>>(`${this.url}/productos/${id}`, data).pipe(map(r => r.data));
  }
  deleteProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/productos/${id}`);
  }

  // Mesas
  getMesas(): Observable<Mesa[]> {
    return this.http.get<ApiResponse<Mesa[]>>(`${this.url}/mesas`).pipe(map(r => r.data));
  }
  createMesa(data: Mesa): Observable<Mesa> {
    return this.http.post<ApiResponse<Mesa>>(`${this.url}/mesas`, data).pipe(map(r => r.data));
  }
  cambiarEstadoMesa(id: number, estado: string): Observable<Mesa> {
    return this.http.patch<ApiResponse<Mesa>>(`${this.url}/mesas/${id}/estado?estado=${estado}`, {}).pipe(map(r => r.data));
  }

  // Clientes
  getClientes(): Observable<Cliente[]> {
    return this.http.get<ApiResponse<Cliente[]>>(`${this.url}/clientes`).pipe(map(r => r.data));
  }
  createCliente(data: Cliente): Observable<Cliente> {
    return this.http.post<ApiResponse<Cliente>>(`${this.url}/clientes`, data).pipe(map(r => r.data));
  }
}
