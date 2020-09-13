<template>
  <div id="printTest" style="margin:30px">
    <p class="font">订单未产总表</p>
    <el-button type="info" style="margin-left:84%" @click="back">返回</el-button>
    <el-button v-print="'#printTest'" type="success">打印</el-button>
    <!-- <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="出货日期:">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="">
        <el-button size="mini" type="primary">查询</el-button>
      </el-form-item>
    </el-form> -->
    <span style="margin-left:25%">制表:</span>
    <span style="margin-left:400px">打印日期:{{ now }}</span><hr>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
    >
      <el-table-column type="index" width="55" />
      <el-table-column prop="name" label="客户名称" width="120" />
      <el-table-column prop="name" label="任务编号" width="120" />
      <el-table-column prop="name" label="客户单号" width="120" />
      <el-table-column prop="name" label="款号" width="120" />
      <el-table-column prop="name" label="箱型" width="120" />
      <el-table-column prop="name" label="纸质" width="120" />
      <el-table-column prop="name" label="供方" width="120" />
      <el-table-column prop="name" label="来料数量" width="120" />
      <el-table-column prop="name" label="尺寸" width="120" />
      <el-table-column prop="name" label="订单数量" width="120" />
      <el-table-column prop="name" label="已产数量" width="120" />
      <el-table-column prop="name" label="未产数量" width="120" />
      <el-table-column prop="name" label="交货日期" width="120" />
    </el-table>
    <!-- <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.count"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    /> -->
  </div>
</template>

<script>
// import { export2Excel } from '@/utils/common'
export default {
  name: 'ProDaily',
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        page: 1,
        size: 10
      },
      now: null
    }
  },
  created() {
    var date = new Date()
    this.now = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    var list = []
    var object = this.$route.query
    console.log(object)
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key]
        list.push(element)
      }
    }
    this.tableData = list
  },
  methods: {
    back() {
      this.$router.push('/ordernotproduct')
    }
  }
}
</script>

<style scoped>
.font{
  font-weight: bold;
  width:400px;
  height: 100px;
  line-height: 100px;
  font-size: 30px;
  font-family: 'Courier New', Courier, monospace;
  /* text-align: center; */
  margin-left: 40%;
  margin-bottom: 0%;
}
</style>
