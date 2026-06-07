import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthStoreService } from '../../infrastructure/stores/auth.store';
import { LoginUseCase } from '../usecases/login.usecase';
import { RegisterUseCase } from '../usecases/register.usecase';
import { LoginRequest, RegisterRequest } from '../../domain/models/auth.model';

// Facade — simplifica la interacción de los componentes con la capa de aplicación
@Injectable({ providedIn: 'root' })
export class AuthFacade {
  private authStore     = inject(AuthStoreService);
  private loginUseCase  = inject(LoginUseCase);
  private registerUC    = inject(RegisterUseCase);
  private router        = inject(Router);

  // Expone signals del store directamente
  readonly currentUser     = this.authStore.currentUser;
  readonly isAuthenticated = this.authStore.isAuthenticated;
  readonly loading         = this.authStore.loading;
  readonly error           = this.authStore.error;

  login(request: LoginRequest): void {
    this.authStore.setLoading(true);
    this.authStore.setError(null);

    this.loginUseCase.execute(request).subscribe({
      next: res => {
        this.authStore.setUser({ username: res.username, rol: res.rol, token: res.token });
        this.authStore.setLoading(false);
        this.router.navigate(['/ventas/dashboard']);
      },
      error: err => {
        const msg = err.error?.error ?? 'Credenciales inválidas';
        this.authStore.setError(msg);
        this.authStore.setLoading(false);
      }
    });
  }

  register(request: RegisterRequest): void {
    this.authStore.setLoading(true);
    this.registerUC.execute(request).subscribe({
      next: res => {
        this.authStore.setUser({ username: res.username, rol: res.rol, token: res.token });
        this.authStore.setLoading(false);
        this.router.navigate(['/ventas/dashboard']);
      },
      error: err => {
        this.authStore.setError(err.error?.error ?? 'Error al registrar');
        this.authStore.setLoading(false);
      }
    });
  }

  logout(): void {
    this.authStore.logout();
  }
}
