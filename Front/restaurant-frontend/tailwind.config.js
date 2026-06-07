/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{html,ts,scss}'],
  theme: {
    extend: {
      colors: {
        primary:   { DEFAULT: '#f97316', light: '#fb923c', dark: '#ea580c' },
        secondary: { DEFAULT: '#1e293b', light: '#334155', dark: '#0f172a' },
        accent:    { DEFAULT: '#06b6d4', dark: '#0891b2' },
        success:   '#22c55e',
        warning:   '#eab308',
        danger:    '#ef4444',
        info:      '#3b82f6'
      },
      fontFamily: { sans: ['Inter', 'system-ui', 'sans-serif'] },
      boxShadow: {
        card: '0 2px 12px 0 rgba(0,0,0,0.08)'
      }
    }
  },
  plugins: []
};
