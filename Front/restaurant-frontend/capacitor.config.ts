import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'pe.restaurant.app',
  appName: 'Restaurant Manager',
  webDir: 'dist/restaurant-frontend/browser',
  server: {
    androidScheme: 'https'
  },
  plugins: {
    SplashScreen: {
      launchShowDuration: 2000,
      backgroundColor: '#1e293b',
      androidSplashResourceName: 'splash',
      showSpinner: false
    }
  }
};

export default config;
