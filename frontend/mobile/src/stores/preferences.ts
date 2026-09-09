import { defineStore } from 'pinia'
import { ref } from 'vue'

export const usePreferencesStore = defineStore('preferences', () => {
  const largeText = ref(uni.getStorageSync('large-text') === true)
  function toggleTextSize() {
    largeText.value = !largeText.value
    uni.setStorageSync('large-text', largeText.value)
  }
  return { largeText, toggleTextSize }
})
