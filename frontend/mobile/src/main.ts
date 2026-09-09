import { createSSRApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import { onUnauthorized } from './api/request'
import { useSessionStore } from './stores/session'

export function createApp() {
  const app = createSSRApp(App)
  const pinia = createPinia()
  app.use(pinia)
  onUnauthorized(() => useSessionStore(pinia).clear())
  return { app }
}
