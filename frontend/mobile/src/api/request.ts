interface ApiResponse<T> {
  code: string
  message: string
  data: T
  traceId: string
}
let unauthorizedHandler: () => void = () => {}
export function onUnauthorized(handler: () => void) {
  unauthorizedHandler = handler
}

function baseUrl(): string {
  let url = import.meta.env.VITE_API_BASE_URL || ''
  // #ifdef H5
  url = '/api/v1'
  // #endif
  if (!url) throw new Error('请先配置小程序接口地址')
  return url.replace(/\/$/, '')
}

/** 访问令牌只在内存传递；正式微信登录和刷新会话接入前不生成模拟令牌。 */
export function request<T>(path: string, accessToken?: string): Promise<T> {
  return new Promise((resolve, reject) => {
    uni.request({
      url: baseUrl() + path,
      method: 'GET',
      timeout: 15000,
      header: accessToken ? { Authorization: 'Bearer ' + accessToken } : {},
      success(response) {
        if (response.statusCode === 401 || response.statusCode === 403) unauthorizedHandler()
        const body = response.data as ApiResponse<T>
        if (response.statusCode >= 200 && response.statusCode < 300 && body.code === 'OK') {
          resolve(body.data)
        } else {
          reject(new Error(body.message || '请求失败，请稍后重试'))
        }
      },
      fail() {
        reject(new Error('网络连接失败，请检查网络后重试'))
      },
    })
  })
}
