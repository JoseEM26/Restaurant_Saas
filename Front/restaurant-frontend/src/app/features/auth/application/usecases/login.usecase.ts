import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthRepository } from '../../domain/ports/auth.repository';
import { AuthResponse, LoginRequest } from '../../domain/models/auth.model';

@Injectable({ providedIn: 'root' })
export class LoginUseCase {
  private repo = inject(AuthRepository);

  execute(request: LoginRequest): Observable<AuthResponse> {
    return this.repo.login(request);
  }
}
