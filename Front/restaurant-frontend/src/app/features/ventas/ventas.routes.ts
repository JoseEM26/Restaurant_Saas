import { Routes } from '@angular/router';

export const VENTAS_ROUTES: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('./infrastructure/components/dashboard/dashboard.component')
        .then(m => m.DashboardComponent)
  },
  {
    path: 'pedidos',
    loadComponent: () =>
      import('./infrastructure/components/pedidos/pedidos.component')
        .then(m => m.PedidosComponent)
  }
];
