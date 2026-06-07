import { Routes } from '@angular/router';

export const MAESTROS_ROUTES: Routes = [
  { path: '', redirectTo: 'productos', pathMatch: 'full' },
  {
    path: 'productos',
    loadComponent: () =>
      import('./infrastructure/components/productos/productos.component')
        .then(m => m.ProductosComponent)
  },
  {
    path: 'mesas',
    loadComponent: () =>
      import('./infrastructure/components/mesas/mesas.component')
        .then(m => m.MesasComponent)
  }
];
