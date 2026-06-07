import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthRepository } from '../../domain/ports/auth.repository';
import { AuthResponse, RegisterRequest } from '../../domain/models/auth.model';

@Injectable({ providedIn: 'root' })
export class RegisterUseCase {
  private repo = inject(AuthRepository);

  execute(request: RegisterRequest): Observable<AuthResponse> {
    return this.repo.register(request);
  }
}
