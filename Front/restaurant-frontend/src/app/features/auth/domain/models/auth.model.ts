export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  password: string;
  email: string;
  rol: 'ADMIN' | 'CAJERO' | 'MESERO' | 'COCINERO';
}

export interface AuthResponse {
  token: string;
  tipo: string;
  username: string;
  rol: string;
  expiresIn: number;
}

export interface CurrentUser {
  username: string;
  rol: string;
  token: string;
}
