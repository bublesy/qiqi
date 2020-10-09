<template>
  <div class="app-container">
    <div class="anniu">
      <el-row>
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#printTest'" type="primary">打印</el-button>
        <!-- <el-button type="primary" @click="toExcel">导出</el-button> -->
        <!-- <el-pagination
          style="float:right;"
          :current-page="currentPage4"
          :page-sizes="[10, 20, 30, 50]"
          :page-size="1"
          layout="prev, pager, next, jumper"
          :total="10"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        /> -->
      </el-row>
    </div>
    <div id="printTest" class="ful">
      <!-- <h2>辅料库存表</h2>
      <table>
        <tr>
          <th>编码</th>
          <th>领料人</th>
          <th>品名规格</th>
          <th>单位</th>
          <th>数量</th>
        </tr>
        <tr v-for="item in tableData" :key="item.id">
          <th>{{ item.code }}</th>
          <th>{{ item.collector }}</th>
          <th>{{ item.specifications }}</th>
          <th>{{ item.company }}</th>
          <th>{{ item.number }}</th>

        </tr>
      </table> -->
      <div class="h" />
      <el-table
        :data="tableData"
        border
        highlight-current-row

        style="width:60%,margin-top:20px"
      >
        <el-table-column
          prop="supplierId"
          label="供应商"
        />
        <el-table-column
          prop="pickingPeople"
          label="领料人"
        />
        <el-table-column
          prop="date"
          label="日期"
        />
        <el-table-column
          prop="specifications"
          label="品名规格"
        />
        <el-table-column
          prop="unit"
          label="单位"
        />
        <el-table-column
          prop="number"
          label="数量"
        />
        <el-table-column
          prop="perPrice"
          label="单价"
        />
        <el-table-column
          prop="money"
          label="金额"
        />
      </el-table>
    </div>
  </div>
</template>
<script>
// accessories  means
import { export2Excel } from '@/utils/common'
// import { getUser } from '@/api/accessories/means'

export default {
  name: 'Printing',
  inject: ['closeTag'],
  data() {
    return {
      tableData: [],
      supplierId: '',
      specifications: '',
      company: '',
      number: '',
      data: []
    }
  },
  created() {
    this.tableData = [this.$route.query.data]
    console.log(this.tableData)
  },
  methods: {
    // 返回
    toBack() {
      this.$router.push('/accessories/stock')
      // this.closeTag()
    },

    toExcel() {
      var list = this.tableData
      const th = ['领料人', '领料人', '品名规格', '单位', '数量']
      const filterVal = ['code', 'collector', 'specifications', 'company', 'umber']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '辅料库存表')
    }
  }

}
</script>
<style lang="scss" scoped>
.h{
 margin-bottom: 20px;
}
table{
  width: 100%;
  color: black;
}
tr{
  width: 20%;
  line-height: 25px;
}
td{
  height: 30px;
}
</style>
