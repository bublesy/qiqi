<template>
  <div class="app-container">
    <h2 style="margin-left:10%">预警管理</h2>
    <el-tabs v-model="activeName" style="width:30%" @tab-click="handleClick">
      <el-tab-pane label="仓库" name="first">
        <el-input v-model="warehouse" placeholder="请输入内容" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="成品仓库" name="second">
        <el-input v-model="turnoff" placeholder="请输入内容" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="成品发货预警" name="third">
        <el-input v-model="delivery" placeholder="请输入内容" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="客户结算" name="fourth">
        <el-input v-model="customer" placeholder="请输入内容" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="供应商结算" name="five">
        <el-input v-model="supplier" placeholder="请输入内容" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini">修改</el-button>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>

export default {
  name: 'Warning',
  data() {
    return {
      activeName: 'first',
      warehouse: 0,
      turnoff: 0,
      delivery: 0,
      customer: 0,
      supplier: 0

    }
  },
  created() {
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event)
    }
  }
}
</script>
<style scoped>

</style>
