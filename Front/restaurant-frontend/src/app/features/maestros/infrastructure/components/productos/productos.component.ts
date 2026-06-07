import { Component, OnInit, inject } from '@angular/core';
import { ColDef, ICellRendererParams } from 'ag-grid-community';
import { IonContent, IonHeader, IonToolbar, IonTitle } from '@ionic/angular/standalone';
import { MaestrosFacade } from '../../../application/facades/maestros.facade';
import { Producto } from '../../../domain/models/maestros.model';
import { AppTableComponent } from '../../../../../shared/components/ui/app-table/app-table.component';

@Component({
  selector: 'app-productos',
  standalone: true,
  imports: [IonContent, IonHeader, IonToolbar, IonTitle, AppTableComponent],
  template: `
    <ion-header>
      <ion-toolbar>
        <ion-title>Catálogo de productos</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <div class="p-4">
        <app-table
          title="Productos del menú"
          subtitle="Gestiona los productos disponibles para los pedidos"
          addLabel="Nuevo producto"
          [rowData]="facade.productos()"
          [columnDefs]="cols"
          [loading]="facade.loading()"
          height="500px"
          (refresh)="facade.loadProductos()"
          (add)="nuevo()" />
      </div>
    </ion-content>
  `
})
export class ProductosComponent implements OnInit {
  facade = inject(MaestrosFacade);

  cols: ColDef<Producto>[] = [
    { field: 'id', headerName: '#', width: 70 },
    { field: 'nombre', headerName: 'Producto', flex: 2,
      cellRenderer: (p: ICellRendererParams) =>
        `<span class="font-medium text-slate-800">${p.value}</span>` },
    { field: 'categoriaNombre', headerName: 'Categoría', flex: 1,
      cellRenderer: (p: ICellRendererParams) =>
        `<span class="badge badge-info">${p.value ?? '—'}</span>` },
    { field: 'precio', headerName: 'Precio', width: 110,
      cellRenderer: (p: ICellRendererParams) =>
        `<span class="font-bold text-orange-600">S/. ${(p.value ?? 0).toFixed(2)}</span>` },
    { field: 'disponible', headerName: 'Disponible', width: 120,
      cellRenderer: (p: ICellRendererParams) =>
        p.value
          ? '<span class="badge badge-success">✓ Sí</span>'
          : '<span class="badge badge-danger">✗ No</span>' },
    { field: 'descripcion', headerName: 'Descripción', flex: 2,
      cellRenderer: (p: ICellRendererParams) =>
        p.value
          ? `<span class="text-xs text-slate-500">${p.value}</span>`
          : '<span class="text-slate-300 text-xs">Sin descripción</span>' }
  ];

  ngOnInit(): void { this.facade.loadProductos(); }

  nuevo(): void { console.log('Modal nuevo producto'); }
}
