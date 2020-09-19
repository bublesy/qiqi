<template>
  <div id="printTest" style="margin:30px">
    <p class="font">出货日报表</p>
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
        <el-button v-print="'#printTest'" type="warning">打印</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button><br>
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
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="客户名称" width="120" />
      <el-table-column prop="" label="出货日期" width="120" />
      <el-table-column prop="" label="出货单号" width="120" />
      <el-table-column prop="name" label="箱型" width="120" />
      <el-table-column prop="" label="出货数量" width="120" />
      <el-table-column prop="perPrice" label="单价" width="120" />
      <el-table-column prop="money" label="金额" width="120" />
      <el-table-column prop="" label="回签状态" width="120" />
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="singlePrint(scope.row)">打印</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.size"
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
      }
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
      this.$router.push('/shipdaily')
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
