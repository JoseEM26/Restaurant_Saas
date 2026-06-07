import { Component, input } from '@angular/core';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-kpi-card',
  standalone: true,
  imports: [DecimalPipe],
  template: `
    <div class="card flex items-center gap-4 hover:shadow-md transition-shadow">
      <div class="w-12 h-12 rounded-xl flex items-center justify-center text-2xl flex-shrink-0"
           [style.background]="bg()">
        {{ icon() }}
      </div>
      <div class="min-w-0">
        <div class="text-2xl font-bold text-slate-800 leading-none">
          @if (prefix()) { <span class="text-base font-semibold text-slate-500">{{ prefix() }}</span> }
          {{ value() }}
        </div>
        <div class="text-xs text-slate-500 mt-1 truncate">{{ label() }}</div>
      </div>
    </div>
  `
})
export class KpiCardComponent {
  icon   = input<string>('📊');
  label  = input<string>('');
  value  = input<string | number>('0');
  prefix = input<string>('');
  bg     = input<string>('#fff7ed');
}
