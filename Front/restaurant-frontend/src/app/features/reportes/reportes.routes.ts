import { Routes } from '@angular/router';

export const REPORTES_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./infrastructure/components/reportes/reportes.component')
        .then(m => m.ReportesComponent)
  }
];
