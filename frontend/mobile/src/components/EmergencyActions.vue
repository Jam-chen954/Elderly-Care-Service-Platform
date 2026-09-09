<script setup lang="ts">
function showUnavailable() {
  uni.showModal({
    title: '线上求助尚未开通',
    content: '当前为功能框架，无法向社区发送求助。需要急救时请拨打 120。',
    showCancel: false,
    confirmText: '我知道了',
  })
}
function callEmergency() {
  uni.makePhoneCall({
    phoneNumber: '120',
    fail() {
      uni.showToast({ title: '如未拨出，请使用手机拨打 120', icon: 'none' })
    },
  })
}
</script>
<template>
  <view class="emergency-actions">
    <button class="help-button" @click="showUnavailable">紧急求助（未开通）</button>
    <button class="phone-button" @click="callEmergency">拨打 120</button>
  </view>
</template>
