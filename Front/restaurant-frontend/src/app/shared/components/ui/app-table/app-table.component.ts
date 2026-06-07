import { Component, input, output } from '@angular/core';
import { AgGridAngular } from 'ag-grid-angular';
import { ColDef, GridReadyEvent, GridSizeChangedEvent, themeQuartz } from 'ag-grid-community';
import { FormsModule } from '@angular/forms';
import { NgClass } from '@angular/common';

// Tema AG Grid v34 — Theming API (sin CSS legacy)
export const restaurantTheme = themeQuartz.withParams({
  headerBackgroundColor:      '#1e293b',
  headerTextColor:            '#ffffff',
  headerColumnResizeHandleColor: '#475569',
  rowHoverColor:              '#fff7ed',
  selectedRowBackgroundColor: '#fed7aa',
  oddRowBackgroundColor:      '#fafafa',
  rowBorder:                  { style: 'solid', width: 1, color: '#f1f5f9' },
  fontFamily:                 'Inter, system-ui, sans-serif',
  fontSize:                   13,
  cellHorizontalPaddingScale: 1.2,
  rowHeight:                  44,
  headerHeight:               44,
  borderRadius:               12,
  wrapperBorderRadius:        12,
  borderColor:                '#e2e8f0',
  backgroundColor:            '#ffffff',
  foregroundColor:            '#1e293b',
  accentColor:                '#f97316',
});

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [AgGridAngular, FormsModule, NgClass],
  template: `
    <div class="rounded-2xl overflow-hidden border border-slate-200 bg-white shadow-sm">

      <!-- Toolbar -->
      <div class="flex flex-col sm:flex-row sm:items-center justify-between gap-3 px-5 py-3.5 border-b border-slate-100 bg-white">
        <div>
          <span class="font-semibold text-slate-800 text-sm">{{ title() }}</span>
          @if (subtitle()) {
            <span class="text-xs text-slate-400 ml-2">— {{ subtitle() }}</span>
          }
        </div>
        <div class="flex items-center gap-2">
          <input [(ngModel)]="quickSearch"
            placeholder="🔍 Buscar..."
            class="text-xs border border-slate-200 rounded-lg px-3 py-1.5 w-36 focus:outline-none focus:ring-2 focus:ring-orange-300 bg-slate-50" />
          <button class="p-1.5 rounded-lg text-slate-500 hover:bg-slate-100 transition-colors" (click)="refresh.emit()" title="Recargar">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"/>
            </svg>
          </button>
          @if (addLabel()) {
            <button class="flex items-center gap-1.5 bg-orange-500 hover:bg-orange-600 text-white text-xs font-medium px-3 py-1.5 rounded-lg transition-colors shadow-sm" (click)="add.emit()">
              <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2.5" d="M12 4v16m8-8H4"/>
              </svg>
              {{ addLabel() }}
            </button>
          }
        </div>
      </div>

      <!-- Grid -->
      @if (loading()) {
        <div class="flex items-center justify-center gap-3 py-16 bg-white">
          <div class="w-5 h-5 border-2 border-orange-500 border-t-transparent rounded-full animate-spin"></div>
          <span class="text-sm text-slate-400">Cargando...</span>
        </div>
      } @else {
        <ag-grid-angular
          [theme]="theme"
          [style.height]="height()"
          class="w-full"
          [rowData]="rowData()"
          [columnDefs]="columnDefs()"
          [defaultColDef]="defaultCol"
          [pagination]="true"
          [paginationPageSize]="10"
          [paginationPageSizeSelector]="[10,20,50]"
          [animateRows]="true"
          [suppressCellFocus]="true"
          [quickFilterText]="quickSearch"
          (gridReady)="onGridReady($event)"
          (gridSizeChanged)="onGridSize($event)" />
      }
    </div>
  `
})
export class AppTableComponent {
  title      = input<string>('');
  subtitle   = input<string>('');
  addLabel   = input<string>('');
  rowData    = input<any[]>([]);
  columnDefs = input<ColDef[]>([]);
  loading    = input<boolean>(false);
  height     = input<string>('460px');

  add     = output<void>();
  refresh = output<void>();

  quickSearch = '';
  theme = restaurantTheme;

  defaultCol: ColDef = { sortable: true, resizable: true, filter: true, minWidth: 80 };

  onGridReady(e: GridReadyEvent): void  { e.api.sizeColumnsToFit(); }
  onGridSize(e: GridSizeChangedEvent): void { e.api.sizeColumnsToFit(); }
}
