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
        style="width: 100%,margin-top:20px"
      >
        <el-table-column
          prop="date"
          label="编码"
        />
        <el-table-column
          prop="specifications"
          label="领料人"
        />
        <el-table-column
          prop="username"
          label="单位"
        />
        <el-table-column
          prop="nickname"
          label="数量"
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
  data() {
    return {
      tableData: [],
      specifications: '',
      company: '',
      number: '',
      data: []
    }
  },
  created() {
    // console.log(document.getElementById('da'))
    this.data = this.$route.query.tableData
    this.getList()
    // getUser().then(res => {
    //   console.log(res)
    //   this.tableData = res
    // })
    this.tableData = this.data
    this.tableData.forEach(a => {
      this.specifications = a.specifications
      this.company = a.company
      this.number = a.number
    })
    this.pagination.total = this.tableData.length
  },
  methods: {
    // 返回
    toBack() {
      this.$router.push('/accessories/stock')
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
