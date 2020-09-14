<template>
  <div class="app-container">
    <div class="anniu">
      <el-row>
        <el-button @click="toBack">返回</el-button>
        <el-button v-print="'#printTest'" type="primary">打印</el-button>
        <el-button type="primary" @click="toExcel">导出</el-button>
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
      <h2>辅料库存表</h2>
      <table>
        <tr>
          <th>编码</th>
          <th>品名规格</th>
          <th>单位</th>
          <th>数量</th>
          <th>领料人</th>
          <!-- <th>日期</th>
          <th>开单员</th>
          <th>备注</th> -->
        </tr>
        <tr v-for="item in form" :key="item.id">
          <th>{{ item.code }}</th>
          <th>{{ item.specifications }}</th>
          <th>{{ item.company }}</th>
          <th>{{ item.number }}</th>
          <th>{{ item.collector }}</th>
        </tr>
        <!-- <tr>
          <td>1</td>
          <td>传真纸</td>
          <td>卷</td>
          <td>24</td>
          <td>管理</td>
          <td>2020年9月10日</td>
          <td>张三</td>
          <td>11</td>
        </tr> -->
      </table>
    </div>
  </div>
</template>
<script>
// accessories  means
import { export2Excel } from '@/utils/common'
export default {
  name: 'Printing',
  data() {
    return {
      form: [
        { code: 1,
          specifications: '传真纸',
          company: '卷',
          number: 24,
          collector: '张三' }
      ]
    }
  },
  created() {
    // console.log(document.getElementById('da'))
  },
  methods: {
    toExcel() {
      var list = this.form
      const th = ['编码', '品名规格', '单位', '数量', '领料人']
      const filterVal = ['code', 'specifications', 'company', 'number', 'collector']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '辅料库存表')
    }
  }

}
</script>
<style lang="scss" scoped>
.ful{
  text-align:center;
  width: 100%;
  height: 35px;
  border-bottom:1px solid #717171;
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
