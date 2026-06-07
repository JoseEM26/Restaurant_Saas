import { Component, OnInit, inject } from '@angular/core';
import { IonContent, IonHeader, IonToolbar, IonTitle } from '@ionic/angular/standalone';
import { MaestrosFacade } from '../../../application/facades/maestros.facade';
import { Mesa } from '../../../domain/models/maestros.model';
import { KpiCardComponent } from '../../../../../shared/components/ui/kpi-card/kpi-card.component';

@Component({
  selector: 'app-mesas',
  standalone: true,
  imports: [IonContent, IonHeader, IonToolbar, IonTitle, KpiCardComponent],
  template: `
    <ion-header>
      <ion-toolbar>
        <ion-title>Mesas del restaurante</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <div class="p-4 space-y-6">

        <!-- KPI resumen -->
        <div class="grid grid-cols-3 gap-4">
          <app-kpi-card icon="✅" label="Mesas libres"
            [value]="contar('LIBRE')"    bg="#f0fdf4" />
          <app-kpi-card icon="🔴" label="Mesas ocupadas"
            [value]="contar('OCUPADA')"  bg="#fef2f2" />
          <app-kpi-card icon="🟡" label="Reservadas"
            [value]="contar('RESERVADA')" bg="#fefce8" />
        </div>

        <!-- Grid de mesas -->
        <div class="card">
          <h3 class="font-semibold text-slate-700 mb-4">Distribución de mesas</h3>
          <div class="grid grid-cols-3 sm:grid-cols-4 md:grid-cols-5 lg:grid-cols-6 gap-3">
            @for (mesa of facade.mesas(); track mesa.id) {
              <div class="relative group flex flex-col items-center p-4 rounded-xl border-2 cursor-pointer transition-all hover:scale-105 hover:shadow-md"
                   [class]="cardClass(mesa.estado)">

                <!-- Estado badge -->
                <span class="absolute -top-2 left-1/2 -translate-x-1/2 text-[10px] font-bold px-2 py-0.5 rounded-full"
                      [class]="badgeClass(mesa.estado)">
                  {{ mesa.estado }}
                </span>

                <div class="text-3xl mt-1">🪑</div>
                <div class="font-bold text-lg mt-1">{{ mesa.numero }}</div>
                <div class="text-xs text-slate-500">{{ mesa.capacidad }} pers.</div>

                <!-- Acción liberar (solo si ocupada) -->
                @if (mesa.estado === 'OCUPADA') {
                  <button
                    (click)="liberar(mesa)"
                    class="mt-2 text-[10px] bg-green-500 text-white px-2 py-0.5 rounded-full hover:bg-green-600 opacity-0 group-hover:opacity-100 transition-opacity">
                    Liberar
                  </button>
                }
              </div>
            }
          </div>
        </div>

      </div>
    </ion-content>
  `
})
export class MesasComponent implements OnInit {
  facade = inject(MaestrosFacade);

  ngOnInit(): void { this.facade.loadMesas(); }

  contar(estado: string): number {
    return this.facade.mesas().filter(m => m.estado === estado).length;
  }

  liberar(mesa: Mesa): void {
    this.facade.cambiarEstadoMesa(mesa.id!, 'LIBRE');
  }

  cardClass(estado: string): string {
    return {
      LIBRE:     'border-green-300 bg-green-50',
      OCUPADA:   'border-red-300   bg-red-50',
      RESERVADA: 'border-amber-300 bg-amber-50'
    }[estado] ?? 'border-gray-200 bg-gray-50';
  }

  badgeClass(estado: string): string {
    return {
      LIBRE:     'bg-green-100 text-green-700',
      OCUPADA:   'bg-red-100   text-red-700',
      RESERVADA: 'bg-amber-100 text-amber-700'
    }[estado] ?? 'bg-gray-100 text-gray-600';
  }
}
