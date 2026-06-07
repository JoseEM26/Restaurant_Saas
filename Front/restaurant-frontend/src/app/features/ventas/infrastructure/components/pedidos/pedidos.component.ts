import { Component, OnInit, inject, computed } from '@angular/core';
import { ColDef, ICellRendererParams } from 'ag-grid-community';
import { IonContent, IonHeader, IonToolbar, IonTitle } from '@ionic/angular/standalone';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { VentasFacade } from '../../../application/facades/ventas.facade';
import { Pedido } from '../../../domain/models/ventas.model';
import { AppTableComponent } from '../../../../../shared/components/ui/app-table/app-table.component';

const ESTADO_BADGE: Record<string, string> = {
  ABIERTO:    '<span class="badge badge-info">Abierto</span>',
  EN_PROCESO: '<span class="badge badge-warning">En proceso</span>',
  LISTO:      '<span class="badge badge-success">Listo</span>',
  CERRADO:    '<span class="badge badge-gray">Cerrado</span>',
  CANCELADO:  '<span class="badge badge-danger">Cancelado</span>'
};

@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [IonContent, IonHeader, IonToolbar, IonTitle, AppTableComponent],
  template: `
    <ion-header>
      <ion-toolbar>
        <ion-title>Pedidos</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="p-4">
      <div class="p-4">
        <app-table
          title="Pedidos"
          [subtitle]="subtitulo()"
          addLabel="Nuevo pedido"
          [rowData]="facade.pedidos()"
          [columnDefs]="cols"
          [loading]="facade.loading()"
          height="520px"
          (refresh)="facade.loadPedidos()"
          (add)="nuevoPedido()" />
      </div>
    </ion-content>
  `
})
export class PedidosComponent implements OnInit {
  facade = inject(VentasFacade);

  subtitulo = computed(() =>
    `${this.facade.pedidos().length} total · ${this.facade.pedidosAbiertos().length} abiertos`
  );

  cols: ColDef<Pedido>[] = [
    { field: 'id',    headerName: '#',     width: 70, pinned: 'left' },
    { field: 'mesaId', headerName: 'Mesa', width: 90,
      cellRenderer: (p: ICellRendererParams) => `<span class="font-semibold text-orange-600">Mesa ${p.value}</span>` },
    { field: 'estado', headerName: 'Estado', width: 140,
      cellRenderer: (p: ICellRendererParams) => ESTADO_BADGE[p.value] ?? p.value },
    { field: 'total', headerName: 'Total', width: 120,
      cellRenderer: (p: ICellRendererParams) =>
        `<span class="font-semibold text-slate-700">S/. ${(p.value ?? 0).toFixed(2)}</span>` },
    { field: 'fechaCreacion', headerName: 'Fecha', flex: 1,
      valueFormatter: p => p.value
        ? format(new Date(p.value), 'dd/MM/yyyy HH:mm', { locale: es }) : '' },
    { field: 'observaciones', headerName: 'Observaciones', flex: 1,
      cellRenderer: (p: ICellRendererParams) =>
        p.value ? `<span class="text-slate-500 text-xs">${p.value}</span>` : '<span class="text-slate-300">—</span>' },
    { headerName: 'Acciones', width: 190, sortable: false, filter: false,
      cellRenderer: (p: ICellRendererParams<Pedido>) => {
        const e = p.data?.estado;
        if (e === 'ABIERTO') return `
          <div class="flex gap-1 items-center h-full">
            <button data-id="${p.data?.id}" data-action="procesar"
              class="badge badge-info cursor-pointer hover:opacity-80">▶ Procesar</button>
            <button data-id="${p.data?.id}" data-action="cancelar"
              class="badge badge-danger cursor-pointer hover:opacity-80">✕ Cancelar</button>
          </div>`;
        if (e === 'EN_PROCESO' || e === 'LISTO') return `
          <div class="flex gap-1 items-center h-full">
            <button data-id="${p.data?.id}" data-action="cerrar"
              class="badge badge-success cursor-pointer hover:opacity-80">✔ Cerrar</button>
          </div>`;
        return '';
      },
      onCellClicked: (p) => {
        const btn = (p.event?.target as HTMLElement)?.closest('[data-action]') as HTMLElement;
        if (!btn) return;
        const id = Number(btn.dataset['id']);
        const action = btn.dataset['action'];
        if (action === 'procesar') this.facade.cambiarEstado(id, 'EN_PROCESO');
        if (action === 'cancelar') this.facade.cambiarEstado(id, 'CANCELADO');
        if (action === 'cerrar')   this.facade.cambiarEstado(id, 'CERRADO');
      }
    }
  ];

  ngOnInit(): void { this.facade.loadPedidos(); }

  nuevoPedido(): void { console.log('Modal nuevo pedido'); }
}
