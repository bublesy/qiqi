<template>
  <div class="app-container">
    <h2 style="margin-left:10%">预警管理</h2>
    <el-tabs v-model="activeName" style="width:30%" @tab-click="handleClick">
      <el-tab-pane label="仓库" name="first">
        <el-input v-model="warehouse" placeholder="请输入内容" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini" @click="modifyWare">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="成品仓库" name="second">
        <el-input v-model="turnoff" placeholder="数量少于多少预警" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini" @click="modifyWare">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="成品送货天数" name="third">
        <el-input v-model="endWareSendDays" placeholder="天数" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini" @click="modifyWare">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="客户结算" name="fourth">
        <el-input v-model="customer" placeholder="请输入内容" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini">修改</el-button>
      </el-tab-pane>
      <el-tab-pane label="供应商结算" name="five">
        <el-input v-model="supplier" placeholder="提前几天结算(天)" size="mini" style="width: 150px" />
        <el-button type="primary" size="mini" @click="modifyWare">修改</el-button>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { modify, list } from '@/api/warning/warning'

export default {
  name: 'Warning',
  data() {
    return {
      activeName: 'first',
      warehouse: '',
      turnoff: '',
      customer: '',
      supplier: '',
      endWareSendDays: '',
      id: ''

    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      list().then(res => {
        var warnList = res.list
        warnList.forEach(a => {
          this.warehouse = a.wareNum
          this.turnoff = a.endWareNum
          this.endWareSendDays = a.endWareSendDays
          this.supplier = a.supplierDay
          this.id = a.id
        })
      })
    },
    handleClick(tab, event) {
    },
    modifyWare() {
      var form = {}
      form.wareNum = this.warehouse
      form.endWareNum = this.turnoff
      form.supplierDay = this.supplier
      form.endWareSendDays = this.endWareSendDays
      form.id = this.id
      modify(form).then(res => {
        if (res) {
          this.$message.success('成功')
        } else {
          this.$message.error('失败')
        }
      })
    }
  }
}
</script>
<style scoped>

</style>
