# 基础框架验证记录

验证日期：2026-09-09。环境：Windows 11、Java 21.0.10、Maven 3.9.12、Node.js 24.14.0、npm 11.11.0。浏览器使用本机 Edge，通过 Playwright CLI 验证。

## 自动检查

| 检查 | 结果 |
|---|---|
| 后端 `mvn -B -ntp verify` | 通过；9 项测试，0 失败，0 错误，0 跳过；生成可运行 JAR |
| 管理端 `npm run lint` | 通过；最终无错误和警告 |
| 管理端 `npm run format:check` | 通过 |
| 管理端 `npm run build` | TypeScript 与 Vite 生产构建通过 |
| 移动端 `npm run format:check` | 通过 |
| 移动端 `npm run type-check` | 通过 |
| 移动端 `npm run build:h5` | 通过 |
| 移动端 `npm run build:mp-weixin` | 通过，产物位于 `frontend/mobile/dist/build/mp-weixin` |
| `docker compose -f infra/compose.yml config --quiet` | 通过；仅配置解析，不代表容器联调通过 |
| 真实 HTTP `/actuator/health` | `UP`，dev 模式 |
| 真实 HTTP `/v3/api-docs` | 返回 OpenAPI 3.1.0，包含 4 个 Controller 路径 |
| 源码与配置品牌残留检查 | 未发现旧品牌或旧包名 |

后端测试覆盖未登录响应与 traceId、CSRF、错误密码、真实 CSRF 凭证、登录后的会话 ID 轮换、会话注销、跨社区参数无效、角色降权、账号停用、平台管理员无社区时拒绝访问、非 dev 环境关闭开发登录与接口文档，以及分页默认值、上限和偏移量溢出边界。

管理端按需引入组件后，主 JavaScript 产物约 190 KB，gzip 约 72 KB；CSS 约 61 KB，gzip 约 9 KB。此处为构建产物大小，不是性能压测结果。

## 浏览器检查

- 开发账号通过真实后端登录，工作台读取数据库中的开发示例社区。
- 进入老人档案预留页面，刷新后通过服务端会话恢复身份。
- 点击退出登录成功；随后直接访问 `/elders` 自动返回登录页。
- 390×844 视口下检查移动端首页、固定求助区域及底部导航。
- 点击特大字号，按钮文字和字号偏好更新；点击预约上门服务进入预约预留页。
- 页面未展示虚构业务统计、订单或健康数据。未触发真实紧急电话拨号。

页面检查截图保存在本地 `output/playwright/`，该目录不进入 Git：`admin-dashboard.png`、`mobile-home.png`、`mobile-large-text.png`。

## 尚未验证

- 本机 Docker 引擎未启动，未执行真实 MySQL、Redis 会话和 RabbitMQ 联调。后端集成测试使用 H2 的 MySQL 兼容模式。
- 微信开发者工具、真实 AppID、合法域名、手机拨号、读屏和 200% 缩放尚未进行真机验收。
- 生产管理员认证、多因素认证、微信登录与刷新令牌、支付、短信、健康、预约、求助投递等业务未实现，不属于本轮验收。
- GitHub Actions 工作流已编写，尚未推送或运行远程 CI。
- 未进行压测、生产部署、容灾演练或完整安全审计。

开发过程中遇到 Vite 子进程在受限沙箱中的 `spawn EPERM`，在允许启动子进程的执行环境重新构建后通过。uni-app 上游依赖有弃用提示；不能将编译通过等同于生产依赖安全验收。
