<template>
  <div class="app-container">
    <div class="anniu">
      <el-row>
        <el-button type="primary">打印</el-button>
        <el-button type="primary">导出</el-button>
      </el-row>
    </div>
    <div class="ful">
      <h2>辅料库存表</h2>
      <table>
        <tr>
          <th>编码</th>
          <th>品名规格</th>
          <th>单位</th>
          <th>数量</th>
          <th>领料人</th>
          <th>日期</th>
          <th>开单员</th>
          <th>备注</th>
        </tr>
        <tr>
          <td>1</td>
          <td>传真纸</td>
          <td>卷</td>
          <td>24</td>
          <td>管理</td>
          <td>2020年9月10日</td>
          <td>张三</td>
          <td>11</td>
        </tr>
        <tr>
          <td>1</td>
          <td>传真纸</td>
          <td>卷</td>
          <td>24</td>
          <td>管理</td>
          <td>2020年9月10日</td>
          <td>张三</td>
          <td>11</td>
        </tr>
      </table>
    </div>
  </div>
</template>
<script>
// accessories  means
export default {
  name: 'Stock',
  data() {
    return {
    }
  },
  methods: {
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
  width: 5.5%;
  line-height: 25px;
}
td{
  height: 30px;
}
</style>
