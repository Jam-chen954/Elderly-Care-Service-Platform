<script setup lang="ts">
import { useRoute } from 'vue-router'
const route = useRoute()
</script>
<template>
  <section class="page-heading">
    <div>
      <span class="eyebrow">MODULE</span>
      <h1>{{ route.meta.title }}</h1>
      <p class="muted">{{ route.meta.description }}</p>
    </div>
    <el-tag type="info">待开发</el-tag>
  </section>
  <section class="empty-panel">
    <el-empty description="模块入口已预留，业务功能尚未接入"
      ><RouterLink to="/"><el-button type="primary">返回工作台</el-button></RouterLink></el-empty
    >
  </section>
</template>
