import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideIonicAngular } from '@ionic/angular/standalone';
import { provideCharts, withDefaultRegisterables } from 'ng2-charts';
import { ModuleRegistry, AllCommunityModule } from 'ag-grid-community';
import { routes } from './app.routes';
import { authInterceptor } from './core/interceptors/auth.interceptor';
import { AuthRepository } from './features/auth/domain/ports/auth.repository';
import { AuthHttpAdapter } from './features/auth/infrastructure/adapters/auth-http.adapter';

// Registrar AG Grid Community
ModuleRegistry.registerModules([AllCommunityModule]);

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withInterceptors([authInterceptor])),
    provideIonicAngular({ mode: 'md' }),
    provideCharts(withDefaultRegisterables()),
    // DI: enlaza el puerto con el adaptador HTTP
    { provide: AuthRepository, useClass: AuthHttpAdapter }
  ]
};
