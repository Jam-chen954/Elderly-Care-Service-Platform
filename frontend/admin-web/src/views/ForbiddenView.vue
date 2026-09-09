<template>
  <main class="standalone-state">
    <el-result
      icon="warning"
      title="当前账号无后台访问权限"
      sub-title="请使用管理员分配的运营账号登录"
      ><template #extra
        ><RouterLink to="/login"
          ><el-button type="primary">返回登录</el-button></RouterLink
        ></template
      ></el-result
    >
  </main>
</template>
