import { Component, inject, signal } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { IonApp, IonRouterOutlet } from '@ionic/angular/standalone';
import { AuthFacade } from './features/auth/application/facades/auth.facade';

interface NavItem {
  path: string;
  label: string;
  icon: string;
  exact?: boolean;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, IonApp, IonRouterOutlet],
  styles: [`
    :host { display: flex; height: 100vh; overflow: hidden; }

    .sidebar {
      width: 240px;
      min-width: 240px;
      background: #0f172a;
      display: flex;
      flex-direction: column;
      height: 100vh;
      overflow: hidden;
      transition: width .2s ease;
      z-index: 100;
    }

    .sidebar-logo {
      padding: 20px 20px 16px;
      border-bottom: 1px solid rgba(255,255,255,.06);
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .logo-icon {
      width: 36px;
      height: 36px;
      border-radius: 10px;
      background: linear-gradient(135deg,#f97316,#ea580c);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 18px;
      flex-shrink: 0;
    }

    .logo-text strong { color: #fff; font-size: 15px; font-weight: 700; display: block; line-height: 1; }
    .logo-text span   { color: #64748b; font-size: 10px; text-transform: uppercase; letter-spacing: .06em; }

    .nav-section {
      flex: 1;
      overflow-y: auto;
      padding: 12px 10px;
    }

    .nav-label {
      color: #334155;
      font-size: 9px;
      font-weight: 700;
      letter-spacing: .1em;
      text-transform: uppercase;
      padding: 0 10px;
      margin: 16px 0 6px;
    }

    .nav-item {
      display: flex;
      align-items: center;
      gap: 10px;
      padding: 9px 12px;
      border-radius: 8px;
      color: #64748b;
      font-size: 13.5px;
      font-weight: 500;
      text-decoration: none;
      transition: all .15s ease;
      cursor: pointer;
      margin-bottom: 2px;
    }

    .nav-item:hover { background: rgba(255,255,255,.05); color: #cbd5e1; }

    .nav-item.active {
      background: rgba(249,115,22,.12);
      color: #f97316;
    }

    .nav-item .nav-icon {
      width: 18px;
      text-align: center;
      flex-shrink: 0;
    }

    .sidebar-footer {
      padding: 12px 10px;
      border-top: 1px solid rgba(255,255,255,.06);
    }

    .user-card {
      display: flex;
      align-items: center;
      gap: 10px;
      padding: 10px 12px;
      border-radius: 8px;
      background: rgba(255,255,255,.04);
    }

    .avatar {
      width: 32px;
      height: 32px;
      border-radius: 50%;
      background: linear-gradient(135deg,#f97316,#ea580c);
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-size: 13px;
      font-weight: 700;
      flex-shrink: 0;
    }

    .user-info strong { color: #e2e8f0; font-size: 13px; font-weight: 600; display: block; line-height: 1.2; }
    .user-info span   { color: #475569; font-size: 10px; }

    .logout-btn {
      display: flex;
      align-items: center;
      gap: 8px;
      width: 100%;
      padding: 8px 12px;
      border-radius: 8px;
      color: #64748b;
      font-size: 12px;
      background: none;
      border: none;
      cursor: pointer;
      margin-top: 6px;
      transition: all .15s ease;
    }
    .logout-btn:hover { background: rgba(239,68,68,.1); color: #ef4444; }

    .main-area {
      flex: 1;
      display: flex;
      flex-direction: column;
      overflow: hidden;
      background: #f1f5f9;
    }

    .top-bar {
      background: #fff;
      border-bottom: 1px solid #e2e8f0;
      padding: 0 24px;
      height: 56px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex-shrink: 0;
      box-shadow: 0 1px 3px rgba(0,0,0,.04);
    }

    .page-title { font-size: 15px; font-weight: 600; color: #1e293b; }

    .top-actions { display: flex; align-items: center; gap: 12px; }

    .status-dot {
      width: 8px;
      height: 8px;
      background: #22c55e;
      border-radius: 50%;
      box-shadow: 0 0 0 2px #dcfce7;
    }

    .content-area {
      flex: 1;
      overflow-y: auto;
    }

    /* Solo auth no tiene sidebar */
    .auth-layout { width: 100%; height: 100vh; overflow: auto; background: #f1f5f9; }
  `],
  template: `
    @if (auth.isAuthenticated()) {
      <aside class="sidebar">

        <!-- Logo -->
        <div class="sidebar-logo">
          <div class="logo-icon">🍽️</div>
          <div class="logo-text">
            <strong>Restaurant</strong>
            <span>Manager v1.0</span>
          </div>
        </div>

        <!-- Nav -->
        <nav class="nav-section">
          <div class="nav-label">Principal</div>

          @for (item of mainNav; track item.path) {
            <a class="nav-item"
               [routerLink]="item.path"
               routerLinkActive="active"
               [routerLinkActiveOptions]="{ exact: item.exact ?? false }">
              <span class="nav-icon">{{ item.icon }}</span>
              {{ item.label }}
            </a>
          }

          <div class="nav-label">Catálogos</div>

          @for (item of catalogNav; track item.path) {
            <a class="nav-item"
               [routerLink]="item.path"
               routerLinkActive="active">
              <span class="nav-icon">{{ item.icon }}</span>
              {{ item.label }}
            </a>
          }

          <div class="nav-label">Reportes</div>

          @for (item of reportNav; track item.path) {
            <a class="nav-item"
               [routerLink]="item.path"
               routerLinkActive="active">
              <span class="nav-icon">{{ item.icon }}</span>
              {{ item.label }}
            </a>
          }
        </nav>

        <!-- Footer usuario -->
        <div class="sidebar-footer">
          <div class="user-card">
            <div class="avatar">{{ initials() }}</div>
            <div class="user-info">
              <strong>{{ auth.currentUser()?.username }}</strong>
              <span>{{ auth.currentUser()?.rol }}</span>
            </div>
          </div>
          <button class="logout-btn" (click)="auth.logout()">
            <svg width="14" height="14" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
            </svg>
            Cerrar sesión
          </button>
        </div>

      </aside>

      <!-- Área principal -->
      <div class="main-area">

        <!-- Top bar -->
        <header class="top-bar">
          <span class="page-title">Restaurant Manager</span>
          <div class="top-actions">
            <div class="status-dot" title="Sistema activo"></div>
            <span style="font-size:12px;color:#64748b">Sistema activo</span>
          </div>
        </header>

        <!-- Contenido -->
        <div class="content-area">
          <router-outlet />
        </div>

      </div>

    } @else {
      <!-- Sin sidebar para auth -->
      <div class="auth-layout">
        <router-outlet />
      </div>
    }
  `
})
export class AppComponent {
  auth = inject(AuthFacade);

  initials = () => {
    const u = this.auth.currentUser()?.username ?? '?';
    return u.slice(0, 2).toUpperCase();
  };

  mainNav: NavItem[] = [
    { path: '/ventas/dashboard', label: 'Dashboard', icon: '🏠', exact: true },
    { path: '/ventas/pedidos',   label: 'Pedidos',   icon: '🧾' },
  ];

  catalogNav: NavItem[] = [
    { path: '/maestros/mesas',    label: 'Mesas',      icon: '🪑' },
    { path: '/maestros/productos',label: 'Productos',  icon: '🍽️' },
  ];

  reportNav: NavItem[] = [
    { path: '/reportes', label: 'Reportes PDF', icon: '📊' },
  ];
}
