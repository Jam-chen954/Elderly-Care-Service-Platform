import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { ElAlert, ElButton, ElEmpty, ElInput, ElResult, ElTag } from 'element-plus'
import 'element-plus/es/components/alert/style/css'
import 'element-plus/es/components/button/style/css'
import 'element-plus/es/components/empty/style/css'
import 'element-plus/es/components/input/style/css'
import 'element-plus/es/components/result/style/css'
import 'element-plus/es/components/tag/style/css'
import 'element-plus/es/components/message/style/css'
import './styles/main.css'
import App from './App.vue'
import { router } from './router'

const app = createApp(App)
app.use(createPinia())
app.use(router)
for (const component of [ElAlert, ElButton, ElEmpty, ElInput, ElResult, ElTag]) {
  app.use(component)
}
app.mount('#app')
