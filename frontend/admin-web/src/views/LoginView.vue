<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { authApi } from '../api/auth'
import { useSessionStore } from '../stores/session'

const router = useRouter()
const session = useSessionStore()
const username = ref('')
const password = ref('')
const loading = ref(false)
const available = ref(false)
const errorMessage = ref('')

onMounted(async () => {
  try {
    available.value = (await authApi.capabilities()).developmentLogin
    if (!available.value) errorMessage.value = '当前环境尚未接入正式管理员认证，请联系项目管理员。'
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : '无法读取系统状态'
  }
})

async function submit() {
  if (loading.value || !available.value) return
  loading.value = true
  errorMessage.value = ''
  try {
    await session.login(username.value.trim(), password.value)
    await router.replace(session.canAccessAdmin ? '/' : '/forbidden')
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : '登录失败'
  } finally {
    loading.value = false
    password.value = ''
  }
}
</script>

<template>
  <main class="login-page">
    <section class="login-intro">
      <span class="eyebrow">ELDERLY CARE SERVICE PLATFORM</span>
      <h1>让社区服务<br />更有温度。</h1>
      <p>社区养老服务平台 · 运营管理后台</p>
      <div class="intro-note">连接社区、服务人员与每一个家庭</div>
    </section>
    <section class="login-card">
      <div class="brand-mark" aria-hidden="true">安</div>
      <h2>登录管理后台</h2>
      <p class="muted">使用已分配的社区管理账号</p>
      <el-alert
        v-if="available"
        title="开发环境：仅用于基础框架联调"
        type="info"
        :closable="false"
      />
      <el-alert
        v-if="errorMessage"
        :title="errorMessage"
        type="error"
        :closable="false"
        role="alert"
      />
      <form class="login-form" @submit.prevent="submit">
        <label for="username">账号</label>
        <el-input
          id="username"
          v-model="username"
          autocomplete="username"
          placeholder="请输入账号"
          size="large"
          :maxlength="64"
        />
        <label for="password">密码</label>
        <el-input
          id="password"
          v-model="password"
          type="password"
          autocomplete="current-password"
          placeholder="请输入密码"
          size="large"
          show-password
          :maxlength="64"
        />
        <el-button
          type="primary"
          native-type="submit"
          size="large"
          :loading="loading"
          :disabled="!available || !username.trim() || !password"
          >登录</el-button
        >
      </form>
      <p class="login-footnote">账号由管理员分配，所属社区和权限由服务端核验。</p>
    </section>
  </main>
</template>
