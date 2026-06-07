import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthStoreService } from '../../features/auth/infrastructure/stores/auth.store';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authStore = inject(AuthStoreService);
  const router    = inject(Router);
  const token     = authStore.token();

  const authReq = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        authStore.logout();
        router.navigate(['/auth/login']);
      }
      return throwError(() => error);
    })
  );
};
