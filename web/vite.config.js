import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { resolve } from "path";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": resolve(__dirname, "src"),
    },
  },

  server: {
    port: 8000,
  },

  build: {
    rollupOptions: {
      output: {
        manualChunks(id) {
          // if (id.includes("components")) {
          //   // 把 components 文件里面的文件都打包到 components.js 中
          //   return "components";
          // }

          // if (id.includes("views")) {
          //   return "views";
          // }
          if (id.includes("src")) {
            // 把 components 文件里面的文件都打包到 components.js 中
            return "index";
          }
        },
      },
    },
  },
});
