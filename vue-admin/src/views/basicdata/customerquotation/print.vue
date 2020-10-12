<template>
  <div style="margin:30px">
    <el-button type="info" style="margin-left:84%" @click="back">返回</el-button>
    <el-button v-print="'#printTest'" type="success">打印</el-button>
    <el-card id="printTest">
      <p class="font">客户报价单管理</p>
      <el-table
        ref="table"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 55%"
        border
        stripe
      >
        <el-table-column type="index" />
        <el-table-column prop="code" label="编码" width="120" />
        <el-table-column prop="shorts" label="客户简称" width="120" />
        <el-table-column prop="fullName" label="客户名称" width="120" />
        <el-table-column prop="paperName" label="纸板名称" />
        <el-table-column prop="boxType" label="箱型" width="120" />
        <el-table-column prop="squaredPrice" label="销售平方价(元)" width="120" />
        <el-table-column prop="boxPrice" label="箱型计价" width="120" />
        <el-table-column prop="totalPrice" label="总价(元)" width="120" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      flag: '',
      id: '',
      tableData: [],
      addDialog: {
        show: false
      },
      total: 0,
      form: {
        page: 1,
        count: 10,
        limitPaperLength: false
      },
      disable: false

    }
  },
  created() {
    var list2 = []
    var object = this.$route.query
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key]
        list2.push(element)
      }
    }
    this.tableData = list2
  },
  methods: {
    back() {
      this.$router.push('/customerquotation')
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
