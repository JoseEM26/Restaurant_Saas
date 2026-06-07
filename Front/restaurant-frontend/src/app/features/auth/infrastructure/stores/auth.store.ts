import { Injectable, computed, signal } from '@angular/core';
import { Router } from '@angular/router';
import { CurrentUser } from '../../domain/models/auth.model';
import AES from 'crypto-js/aes';
import Utf8 from 'crypto-js/enc-utf8';

const SECRET = 'r3st@ur@nt-k3y-2024';

// Signal Store con crypto-js para cifrar el token en localStorage
@Injectable({ providedIn: 'root' })
export class AuthStoreService {
  private readonly TOKEN_KEY = 'r_token';
  private readonly USER_KEY  = 'r_user';

  private _user    = signal<CurrentUser | null>(this.hydrate());
  private _loading = signal(false);
  private _error   = signal<string | null>(null);

  readonly currentUser     = this._user.asReadonly();
  readonly isAuthenticated = computed(() => !!this._user());
  readonly token           = computed(() => this._user()?.token ?? null);
  readonly rol             = computed(() => this._user()?.rol ?? null);
  readonly loading         = this._loading.asReadonly();
  readonly error           = this._error.asReadonly();

  constructor(private router: Router) {}

  setUser(user: CurrentUser): void {
    // Cifra el token antes de guardarlo en localStorage
    const encToken = AES.encrypt(user.token, SECRET).toString();
    localStorage.setItem(this.TOKEN_KEY, encToken);
    localStorage.setItem(this.USER_KEY, JSON.stringify({ ...user, token: encToken }));
    this._user.set(user);
    this._error.set(null);
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this._user.set(null);
    this.router.navigate(['/auth/login']);
  }

  setLoading(v: boolean)       { this._loading.set(v); }
  setError(msg: string | null) { this._error.set(msg); }

  private hydrate(): CurrentUser | null {
    try {
      const raw = localStorage.getItem(this.USER_KEY);
      if (!raw) return null;
      const stored = JSON.parse(raw) as CurrentUser;
      // Descifra el token al hidratar
      const bytes = AES.decrypt(stored.token, SECRET);
      stored.token = bytes.toString(Utf8);
      return stored.token ? stored : null;
    } catch { return null; }
  }
}
