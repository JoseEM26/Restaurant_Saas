import { Component, inject } from '@angular/core';
import { FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { IonContent, IonCard, IonCardHeader, IonCardTitle, IonCardContent,
         IonItem, IonLabel, IonInput, IonButton, IonSpinner } from '@ionic/angular/standalone';
import { AuthFacade } from '../../../application/facades/auth.facade';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    IonContent, IonCard, IonCardHeader, IonCardTitle, IonCardContent,
    IonItem, IonLabel, IonInput, IonButton, IonSpinner
  ],
  template: `
    <ion-content class="flex items-center justify-center min-h-screen bg-gray-50">
      <div class="w-full max-w-md mx-auto p-4">

        <div class="text-center mb-8">
          <div class="text-6xl mb-2">🍽️</div>
          <h1 class="text-2xl font-bold text-secondary">Restaurant Manager</h1>
          <p class="text-gray-500 text-sm mt-1">Ingresa tus credenciales</p>
        </div>

        <ion-card class="rounded-2xl shadow-card">
          <ion-card-header>
            <ion-card-title class="text-xl text-center">Iniciar Sesión</ion-card-title>
          </ion-card-header>
          <ion-card-content>
            <form [formGroup]="form" (ngSubmit)="onSubmit()">

              <ion-item class="mb-4 rounded-xl" lines="none">
                <ion-label position="stacked">Usuario</ion-label>
                <ion-input formControlName="username" type="text"
                           placeholder="admin" autocomplete="username"></ion-input>
              </ion-item>
              @if (form.get('username')?.invalid && form.get('username')?.touched) {
                <p class="text-red-500 text-xs px-2 -mt-3 mb-2">Usuario requerido</p>
              }

              <ion-item class="mb-4 rounded-xl" lines="none">
                <ion-label position="stacked">Contraseña</ion-label>
                <ion-input formControlName="password" type="password"
                           placeholder="••••••" autocomplete="current-password"></ion-input>
              </ion-item>
              @if (form.get('password')?.invalid && form.get('password')?.touched) {
                <p class="text-red-500 text-xs px-2 -mt-3 mb-2">Contraseña requerida</p>
              }

              @if (error()) {
                <div class="bg-red-50 border border-red-200 rounded-lg p-3 mb-4">
                  <p class="text-red-600 text-sm">{{ error() }}</p>
                </div>
              }

              <ion-button expand="block" type="submit" color="primary"
                          class="mt-2" [disabled]="loading() || form.invalid">
                @if (loading()) {
                  <ion-spinner name="crescent" class="mr-2"></ion-spinner>
                }
                {{ loading() ? 'Ingresando...' : 'Ingresar' }}
              </ion-button>

            </form>
          </ion-card-content>
        </ion-card>
      </div>
    </ion-content>
  `
})
export class LoginComponent {
  private fb     = inject(FormBuilder);
  private facade = inject(AuthFacade);

  loading = this.facade.loading;
  error   = this.facade.error;

  form = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(6)]]
  });

  onSubmit(): void {
    if (this.form.invalid) return;
    const v = this.form.getRawValue();
    this.facade.login({ username: v.username!, password: v.password! });
  }
}
