<script setup lang="ts">
import PageFrame from '../../components/PageFrame.vue'
import { usePreferencesStore } from '../../stores/preferences'
const preferences = usePreferencesStore()
</script>
<template>
  <PageFrame title="我的" subtitle="账号与使用设置">
    <view class="mobile-card"
      ><text class="card-title">尚未登录</text
      ><text class="body-copy"
        >微信登录尚未接入。开通后，系统将根据账号权限展示老人、家属或服务人员功能。</text
      ></view
    >
    <button class="font-button" @click="preferences.toggleTextSize">
      {{ preferences.largeText ? '恢复默认字号' : '切换特大字号' }}
    </button>
  </PageFrame>
</template>
