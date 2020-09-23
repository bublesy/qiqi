<template>
  <div class="app-container">
    <el-button type="primary" @click="search">主要按钮</el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column v-for="(item, index) in headers" :key="index" :label="item.label" :prop="item.prop" />
    </el-table>
  </div>
</template>

<script>

export default {
  data() {
    return {
      headers: [{ label: '1月', prop: 'a' }, { label: '2月', prop: 'b' }, { label: '3月', prop: 'c' }],
      tableData: [{
        a: '2016-05-02',
        b: '王小虎',
        c: '上海市普陀区金沙江路 1518 弄'
      }, {
        a: '2016-05-04',
        b: '王小虎',
        c: '上海市普陀区金沙江路 1517 弄'
      }, {
        a: '2016-05-01',
        b: '王小虎',
        c: '上海市普陀区金沙江路 1519 弄'
      }]
    }
  },
  created() {
  },
  methods: {
    search() {
      const abc = {
        title: [{ label: '1月', prop: 'a' }, { label: '2月', prop: 'b' }, { label: '3月', prop: 'c' }, { label: '4月', prop: 'd' }],
        data: [{
          a: '2016-05-02',
          b: '王小虎',
          c: '上海市普陀区金沙江路 1518 弄',
          d: 'asd'
        }, {
          a: '2016-05-04',
          b: '王小虎',
          c: '上海市普陀区金沙江路 1517 弄',
          d: 'asd2'
        }, {
          a: '2016-05-01',
          b: '王小虎',
          c: '上海市普陀区金沙江路 1518 弄',
          d: 'asd3'
        }]
      }
      this.headers = abc.title
      this.tableData = abc.data
    }

  }
}
</script>
<style scoped>
.dh{
  display: flex;
  justify-content: space-between;
  padding: 0 10px;
  width: 1200px;
  margin: 0 auto;
}
table,tr,td{
  border:1px solid black;
}
table{
width: 1200px;
  border-collapse:collapse;
  height: auto;
  margin: 0 auto;
}
tr{
  height: 35px;
}
table td{
  /* width: 40px ; */
   padding:15px;
  height: 35px;

  /* text-align: center; */
}
.center{
  text-align: center;

}
.img{
   width: 1100px;
   height: 300px;
  margin: 20px auto;
  background: pink;
  /* overflow: hidden; */
}
.img img{
  width: 100%;
  height:100%;
}
</style>
