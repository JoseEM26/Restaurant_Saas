import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IonContent, IonHeader, IonToolbar, IonTitle,
         IonButton, IonIcon, IonSpinner, IonCard, IonCardContent } from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { documentText, download, barChart } from 'ionicons/icons';
import { environment } from '../../../../../../environments/environment';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [IonContent, IonHeader, IonToolbar, IonTitle,
            IonButton, IonIcon, IonSpinner, IonCard, IonCardContent],
  template: `
    <ion-header>
      <ion-toolbar color="secondary">
        <ion-title>Reportes</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="p-6">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

        <!-- Reporte Ventas -->
        <ion-card class="rounded-2xl shadow-card">
          <ion-card-content>
            <div class="text-4xl mb-3">📊</div>
            <h3 class="font-bold text-lg text-secondary mb-1">Reporte de Ventas</h3>
            <p class="text-sm text-gray-500 mb-4">Exporta el resumen de ventas del mes en PDF</p>
            <ion-button expand="block" color="primary" (click)="descargarVentas()" [disabled]="loading">
              @if (loading) { <ion-spinner name="crescent" class="mr-2" /> }
              <ion-icon name="download" slot="start" />
              Descargar PDF
            </ion-button>
          </ion-card-content>
        </ion-card>

        <!-- Reporte Pedidos -->
        <ion-card class="rounded-2xl shadow-card">
          <ion-card-content>
            <div class="text-4xl mb-3">🧾</div>
            <h3 class="font-bold text-lg text-secondary mb-1">Reporte de Pedidos</h3>
            <p class="text-sm text-gray-500 mb-4">Exporta los pedidos de hoy en PDF</p>
            <ion-button expand="block" color="secondary" (click)="descargarPedidos()" [disabled]="loading">
              @if (loading) { <ion-spinner name="crescent" class="mr-2" /> }
              <ion-icon name="download" slot="start" />
              Descargar PDF
            </ion-button>
          </ion-card-content>
        </ion-card>

        <!-- Exportar Excel -->
        <ion-card class="rounded-2xl shadow-card">
          <ion-card-content>
            <div class="text-4xl mb-3">📗</div>
            <h3 class="font-bold text-lg text-secondary mb-1">Exportar a Excel</h3>
            <p class="text-sm text-gray-500 mb-4">Exporta datos de ventas en formato xlsx</p>
            <ion-button expand="block" color="success" (click)="exportarExcel()">
              <ion-icon name="bar-chart" slot="start" />
              Exportar XLSX
            </ion-button>
          </ion-card-content>
        </ion-card>

      </div>
    </ion-content>
  `
})
export class ReportesComponent {
  private http = inject(HttpClient);
  loading = false;

  constructor() { addIcons({ documentText, download, barChart }); }

  descargarVentas(): void {
    this.loading = true;
    this.http.get(`${environment.apiReportes}/ventas/pdf`, { responseType: 'blob' })
      .subscribe({
        next: blob => { this.descargarBlob(blob, 'reporte-ventas.pdf'); this.loading = false; },
        error: ()  => this.loading = false
      });
  }

  descargarPedidos(): void {
    this.loading = true;
    this.http.get(`${environment.apiReportes}/pedidos/pdf`, { responseType: 'blob' })
      .subscribe({
        next: blob => { this.descargarBlob(blob, 'reporte-pedidos.pdf'); this.loading = false; },
        error: ()  => this.loading = false
      });
  }

  exportarExcel(): void {
    // xlsx — exportación client-side
    import('xlsx').then(XLSX => {
      const data = [{ Producto: 'Ceviche', Cantidad: 10, Total: 'S/. 350.00' }];
      const ws   = XLSX.utils.json_to_sheet(data);
      const wb   = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, 'Ventas');
      XLSX.writeFile(wb, 'ventas.xlsx');
    });
  }

  private descargarBlob(blob: Blob, filename: string): void {
    const url = URL.createObjectURL(blob);
    const a   = document.createElement('a');
    a.href = url; a.download = filename; a.click();
    URL.revokeObjectURL(url);
  }
}
