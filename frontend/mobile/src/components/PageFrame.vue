<script setup lang="ts">
import { usePreferencesStore } from '../stores/preferences'
import EmergencyActions from './EmergencyActions.vue'
defineProps<{ title: string; subtitle: string }>()
const preferences = usePreferencesStore()
</script>
<template>
  <view class="mobile-page" :class="{ 'large-text': preferences.largeText }">
    <view class="mobile-heading"
      ><text class="mobile-title">{{ title }}</text
      ><text class="mobile-subtitle">{{ subtitle }}</text></view
    >
    <slot />
    <EmergencyActions />
  </view>
</template>
