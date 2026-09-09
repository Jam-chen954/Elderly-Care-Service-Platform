<template>
  <main class="standalone-state">
    <el-result icon="info" title="页面不存在" sub-title="请检查地址或返回工作台"
      ><template #extra
        ><RouterLink to="/"><el-button type="primary">返回工作台</el-button></RouterLink></template
      ></el-result
    >
  </main>
</template>
