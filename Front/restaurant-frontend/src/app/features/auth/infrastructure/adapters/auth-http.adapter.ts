import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../../../environments/environment';
import { AuthRepository } from '../../domain/ports/auth.repository';
import { AuthResponse, LoginRequest, RegisterRequest } from '../../domain/models/auth.model';
import { ApiResponse } from '../../../../core/models/api-response.model';

// Adaptador — implementa el puerto usando HTTP (infraestructura)
@Injectable({ providedIn: 'root' })
export class AuthHttpAdapter implements AuthRepository {
  private http = inject(HttpClient);
  private url  = environment.apiAuth;

  login(request: LoginRequest): Observable<AuthResponse> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.url}/login`, request)
      .pipe(map(res => res.data));
  }

  register(request: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.url}/register`, request)
      .pipe(map(res => res.data));
  }
}
