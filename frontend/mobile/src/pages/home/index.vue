<script setup lang="ts">
import PageFrame from '../../components/PageFrame.vue'
import { usePreferencesStore } from '../../stores/preferences'
const preferences = usePreferencesStore()
function navigate(url: string) {
  uni.switchTab({ url })
}
</script>
<template>
  <PageFrame title="您好，欢迎来到社区养老" subtitle="服务在身边，生活更安心">
    <button class="font-button" @click="preferences.toggleTextSize">
      {{ preferences.largeText ? '使用默认字号' : '使用特大字号' }}
    </button>
    <view class="home-links">
      <button class="feature-button" @click="navigate('/pages/booking/index')">预约上门服务</button>
      <button class="feature-button" @click="navigate('/pages/health/index')">我的健康</button>
      <button class="feature-button" @click="navigate('/pages/profile/index')">我的账号</button>
    </view>
    <view class="mobile-card"
      ><text class="card-title">今天的安排</text
      ><text class="body-copy">预约功能尚未接入，暂无可展示的日程。</text></view
    >
    <view class="notice-card"><text>当前为基础框架预览，线上服务暂未开通。</text></view>
  </PageFrame>
</template>
