import { reactRouter } from "@react-router/dev/vite";
import tailwindcss from "@tailwindcss/vite";
import { defineConfig } from "vite";
import tsconfigPaths from "vite-tsconfig-paths";

export default defineConfig({
  plugins: [tailwindcss(), reactRouter(), tsconfigPaths()],
  
  // Development server configuration
  server: {
    port: 3000,
    host: true,
    open: true,
  },
  
  // Build configuration
  build: {
    target: 'es2022',
    minify: 'esbuild',
    sourcemap: true,
    rollupOptions: {
      output: {
        manualChunks: {
          vendor: ['react', 'react-dom', 'react-router'],
          utils: ['axios'],
        },
      },
    },
  },
  
  // Environment variables prefix
  envPrefix: 'VITE_',
});
