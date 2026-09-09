<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { authApi } from '../api/auth'
import { roleLabels } from '../api/roles'
import type { Community } from '../api/types'
import { featureModules } from '../router/modules'
import { useSessionStore } from '../stores/session'

const session = useSessionStore()
const community = ref<Community | null>(null)
const loading = ref(false)
const errorMessage = ref('')

async function loadCommunity() {
  loading.value = true
  errorMessage.value = ''
  community.value = null
  try {
    community.value = await authApi.community()
  } catch (error) {
    errorMessage.value = error instanceof Error ? error.message : '社区信息读取失败'
  } finally {
    loading.value = false
  }
}
onMounted(loadCommunity)
</script>

<template>
  <section class="page-heading">
    <div>
      <span class="eyebrow">WORKSPACE</span>
      <h1>工作台</h1>
      <p class="muted">欢迎回来，{{ session.account?.displayName }}。从这里进入社区服务管理。</p>
    </div>
    <el-tag effect="plain" size="large">基础框架</el-tag>
  </section>
  <section class="welcome-panel">
    <div>
      <span class="eyebrow">当前服务范围</span>
      <h2>{{ loading ? '正在读取社区…' : (community?.name ?? '暂未获取社区') }}</h2>
      <p>
        账号角色：{{ session.account ? roleLabels[session.account.role] : '—' }} · 社区编号：{{
          community?.id ?? '—'
        }}
      </p>
    </div>
    <span class="scope-note">权限由服务端核验</span>
  </section>
  <el-alert v-if="errorMessage" :title="errorMessage" type="error" :closable="false"
    ><el-button @click="loadCommunity">重新加载</el-button></el-alert
  >
  <section class="section-heading">
    <h2>功能入口</h2>
    <span class="muted">业务功能待后续阶段接入</span>
  </section>
  <div class="module-grid">
    <RouterLink
      v-for="(module, index) in featureModules"
      :key="module.path"
      :to="'/' + module.path"
      class="module-card"
    >
      <span class="module-index">{{ String(index + 1).padStart(2, '0') }}</span>
      <h3>{{ module.title }}</h3>
      <p>{{ module.description }}</p>
      <span class="module-status">页面已预留 <span aria-hidden="true">→</span></span>
    </RouterLink>
  </div>
  <div class="framework-note">
    <strong>当前交付范围</strong>
    <p>
      工程结构、会话登录、角色与社区权限、请求封装和模块导航已接入。业务统计将在业务功能完成后展示。
    </p>
  </div>
</template>
