import { defineStore } from 'pinia'
import { ref } from 'vue'

interface MobileAccount {
  id: string
  displayName: string
  role: 'ELDER' | 'FAMILY' | 'STAFF'
}

export const useSessionStore = defineStore('session', () => {
  const account = ref<MobileAccount | null>(null)
  const accessToken = ref<string | null>(null)
  function clear() {
    account.value = null
    accessToken.value = null
  }
  // 角色只能来自服务端认证结果，不提供客户端自由切换角色的功能。
  return { account, accessToken, clear }
})
