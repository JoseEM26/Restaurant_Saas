import { Observable } from 'rxjs';
import { AuthResponse, LoginRequest, RegisterRequest } from '../models/auth.model';

// Puerto (interfaz) — Clean Architecture: el dominio no sabe del HTTP
export abstract class AuthRepository {
  abstract login(request: LoginRequest): Observable<AuthResponse>;
  abstract register(request: RegisterRequest): Observable<AuthResponse>;
}
