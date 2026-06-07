import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthStoreService } from '../../features/auth/infrastructure/stores/auth.store';

export const authGuard: CanActivateFn = () => {
  const authStore = inject(AuthStoreService);
  const router    = inject(Router);

  if (authStore.isAuthenticated()) return true;

  router.navigate(['/auth/login']);
  return false;
};
