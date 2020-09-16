<template>
  <div style="margin:30px">
    <p class="font">客户报价单管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="客户名称:">
        <el-input v-model="form.shorts" />
      </el-form-item>
      <el-form-item label="全称:">
        <el-input v-model="form.fullName" />
      </el-form-item>
      <!-- <el-form-item label="纸板名称:">
        <el-input v-model="form.paperName" />
      </el-form-item> -->
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <!-- <el-button size="mini" type="primary" @click="add">新增</el-button> -->
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
    <el-table
      ref="table"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <!-- <el-table-column type="selection" width="55" /> -->
      <el-table-column type="index" />
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="shorts" label="客户简称" width="120" />
      <el-table-column prop="fullName" label="客户全称" width="120" />
      <el-table-column prop="paperName" label="纸板名称" width="120" />
      <el-table-column prop="boxType" label="箱型" width="120" />
      <el-table-column prop="squaredPrice" label="销售平方价(元)" width="120" />
      <!-- <template slot-scope="scope">
          <el-input-number v-model="scope.row.squaredPrice" size="mini" :precision="2" :controls="false" :min="0" style="width:90px" />
        </template>
      </el-table-column> -->
      <el-table-column prop="boxPrice" label="箱型计价(元)" width="120" />
      <el-table-column prop="totalPrice" label="总价(元)" width="120" />
      <!-- <template slot-scope="scope">
          <el-input-number v-model="scope.row.BoxPrice" size="mini" :precision="2" :controls="false" :min="0" style="width:90px" />
        </template>
      </el-table-column> -->
      <el-table-column label="操作" width="250">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="add(scope.row.id)">新增报价单</el-button>
          <el-button type="warning" size="mini" @click="updated(scope.row.id)">编辑</el-button>
          <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.count"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <addDialog :id="id" :dialog="addDialog" :flag="flag" @init="initTable" /></div>
</template>

<script>
import addDialog from '@/views/basicdata/customerquotation/add'
import { getCustomerQuotation, delCustomerQuotation } from '@/api/basedata/customerquotation'
import { export2Excel } from '@/utils/common'
export default {
  components: { addDialog },
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
      }

    }
  },
  created() {
    this.initTable()
  },
  methods: {
    handleSizeChange(size) {
      this.size = this.form.count
      this.initTable()
    },
    handleCurrentChange(page) {
      this.page = this.form.page
      this.initTable()
    },
    initTable() {
      getCustomerQuotation(this.form).then(res => {
        this.tableData = res.list
        this.tableData.forEach(x => {
          var res = this.duplicate(x.paperName)
          x.paperName = res.join(',')
        })
        this.total = res.total
      })
    },
    deleted(id) {
      delCustomerQuotation(id).then(res => {
        if (res) {
          this.initTable()
          this.$message.success('删除成功')
        } else {
          this.$message.success('删除失败')
        }
      })
    },
    add(id) {
      this.flag = 'add'
      this.id = id
      this.addDialog.show = true
    },
    updated(id) {
      this.flag = 'updated'
      this.id = id
      this.addDialog.show = true
    },
    query() {
      this.initTable()
    },
    handleSelectionChange() {},
    toExcel() {
      var list = this.tableData
      const th = ['编码', '简称', '全称', '销售平方价', '箱型计价']
      const filterVal = ['code', 'shorts', 'fullName', 'squaredPrice', 'boxPrice']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '客户报价单管理')
    },
    duplicate(arr) {
      var json = {}; var res = []
      for (var i = 0; i < arr.length; i++) {
        if (!json[arr[i]]) {
          json[arr[i]] = true
          res.push(arr[i])
        }
      }
      return res
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
