export const featureModules = [
  { path: 'elders', title: '老人档案', description: '老人基础资料与社区归属。' },
  { path: 'health', title: '健康记录', description: '健康记录及授权范围。' },
  { path: 'consent', title: '家属授权', description: '亲属关系核验与分项授权。' },
  { path: 'services', title: '服务目录', description: '服务项目与可预约时段。' },
  { path: 'orders', title: '预约与派单', description: '预约、人工调度与订单处理。' },
  { path: 'fulfillment', title: '服务履约', description: '受派任务、服务过程和异常记录。' },
  { path: 'emergencies', title: '求助工作台', description: '值守接警与人工联络记录。' },
  { path: 'audit', title: '安全与审计', description: '访问记录与隐私申请。' },
] as const
