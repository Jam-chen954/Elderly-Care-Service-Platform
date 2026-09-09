import { request } from './http'
import type { AuthCapabilities, Community, CurrentAccount } from './types'

export const authApi = {
  capabilities: () => request<AuthCapabilities>('/auth/capabilities'),
  me: () => request<CurrentAccount>('/auth/me'),
  login: (username: string, password: string) =>
    request<CurrentAccount>('/auth/admin/login', {
      method: 'POST',
      body: new URLSearchParams({ username, password }),
    }),
  logout: () => request<null>('/auth/logout', { method: 'POST' }),
  community: () => request<Community>('/system/community'),
}
