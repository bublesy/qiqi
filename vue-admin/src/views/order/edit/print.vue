<template>
  <div id="print" style="padding:10px">
    <p class="font">客户订单</p>
    <el-button v-print="'#print'" size="mini" type="success">打印</el-button>
    <el-button size="mini" type="info" @click="back">返回</el-button>
    <el-card>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="no" label="任务编号" width="120" />
        <el-table-column prop="name" label="客户名称" width="120" />
        <el-table-column prop="customerNo" label="客户单号" width="120" />
        <el-table-column prop="modelNo" label="款号" width="120" />
        <el-table-column prop="boxType" label="箱型" width="120" />
        <el-table-column prop="material" label="材质" width="120" />
        <el-table-column prop="cartonSize" label="纸箱尺寸(mm)" width="180">
          <template slot-scope="scope">
            {{ scope.row.length+' X '+scope.row.width+' X '+scope.row.height }}
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="订单数量" width="120" />
        <el-table-column prop="incomeNum" label="纸板到货数量" width="120" />
        <!-- <el-table-column prop="space" label="仓位" width="120" /> -->
        <el-table-column prop="productNum" label="已产数量" width="120" />
        <el-table-column prop="productSpace" label="成品仓位" width="120" />
        <el-table-column prop="sendNum" label="已送数量" width="120" />
        <!-- <el-table-column prop="lossNum" label="损耗数量" width="120" /> -->
        <el-table-column prop="orderDate" label="下单日期" width="120" />
        <el-table-column prop="deliveryDate" label="交货日期" width="120" />
        <el-table-column prop="wosState" label="仓库状态" width="120" />
        <!-- <el-table-column prop="money" label="金额" width="120" />
        <el-table-column prop="supplier" label="供方" width="120" /> -->
        <!-- <el-table-column prop="createdTime" label="制单时间" width="120" />
        <el-table-column prop="making" label="制单人" width="120" />
        <el-table-column prop="auditTime" label="审核时间" width="120" />
        <el-table-column prop="audit" label="审核人" width="120" /> -->
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
    </el-card>
  </div>
</template>

<script>
// import { export2Excel } from '@/utils/common'
export default {
  name: 'Edit',
  data() {
    return {
      id: '测试施工单id',
      total: 0,
      form: {
        page: 1,
        count: 10
        // no: '',
        // modelNo: '',
        // boxType: '',
        // supplier: ''
      },
      tableData: [],
      value: '',
      options: [],
      boxTypeOptions: [],
      supplierOptions: []

    }
  },
  created() {
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
      this.$router.push('/edit')
    }

    // toExcel() {
    //   var list = this.tableData
    //   const th = ['部门名称', '最后操作时间']
    //   const filterVal = ['name', 'lastmodifytime']
    //   const data = list.map(v => filterVal.map(k => v[k]))
    //   export2Excel(th, data, '部门管理')
    // }
  }

}
</script>

<style scoped>
.el-link{
  color: rgb(0, 162, 255);
  font: 1em sans-serif;
}
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
