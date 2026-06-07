import { Component, OnInit, ElementRef, ViewChild, input } from '@angular/core';
import lottie from 'lottie-web';

@Component({
  selector: 'app-lottie-loader',
  standalone: true,
  template: `
    <div class="flex flex-col items-center justify-center p-8">
      <div #lottieContainer style="width:120px;height:120px"></div>
      @if (message()) {
        <p class="text-gray-500 text-sm mt-3">{{ message() }}</p>
      }
    </div>
  `
})
export class LottieLoaderComponent implements OnInit {
  @ViewChild('lottieContainer', { static: true }) container!: ElementRef;

  message = input<string>('Cargando...');

  ngOnInit(): void {
    lottie.loadAnimation({
      container: this.container.nativeElement,
      renderer: 'svg',
      loop: true,
      autoplay: true,
      // Animación de plato/comida inline (SVG data URI)
      animationData: {
        v: '5.5.7', fr: 30, ip: 0, op: 60, w: 100, h: 100,
        layers: [{
          ty: 4, nm: 'spinner', ind: 0, ks: {
            r: { a: 1, k: [{ t: 0, s: [0], e: [360] }, { t: 60, s: [360] }] },
            o: { a: 0, k: 100 }, p: { a: 0, k: [50, 50] }, s: { a: 0, k: [100, 100] }
          },
          shapes: [{
            ty: 'el', p: { a: 0, k: [0, 0] }, s: { a: 0, k: [60, 60] }
          }, {
            ty: 'st', c: { a: 0, k: [0.976, 0.451, 0.086, 1] }, w: { a: 0, k: 6 },
            lc: 2, lj: 2, d: [{ n: 'd', v: { a: 0, k: 150 } }, { n: 'o', v: { a: 0, k: 0 } }]
          }]
        }]
      }
    });
  }
}
