import { createRouter, createWebHistory } from 'vue-router'
import { useSessionStore } from '../stores/session'
import { featureModules } from './modules'

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: '/login', name: 'login', component: () => import('../views/LoginView.vue') },
    { path: '/forbidden', component: () => import('../views/ForbiddenView.vue') },
    {
      path: '/',
      component: () => import('../components/AdminLayout.vue'),
      children: [
        {
          path: '',
          name: 'dashboard',
          component: () => import('../views/DashboardView.vue'),
          meta: { title: '工作台' },
        },
        ...featureModules.map((module) => ({
          path: module.path,
          component: () => import('../views/ModuleView.vue'),
          meta: { title: module.title, description: module.description },
        })),
      ],
    },
    { path: '/:pathMatch(.*)*', component: () => import('../views/NotFoundView.vue') },
  ],
})

router.beforeEach(async (to) => {
  const session = useSessionStore()
  if (!session.initialized) {
    try {
      await session.restore()
    } catch {
      return to.path === '/login' ? true : '/login'
    }
  }
  if (to.path === '/login') return session.canAccessAdmin ? '/' : true
  if (!session.account) return '/login'
  if (!session.canAccessAdmin && to.path !== '/forbidden') return '/forbidden'
  return true
})

// 权限或会话失效时卸载页面，防止继续展示上一会话的数据。
window.addEventListener('session-invalidated', () => {
  useSessionStore().clear()
  if (router.currentRoute.value.path !== '/login') void router.replace('/login')
})
