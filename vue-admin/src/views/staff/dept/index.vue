<template>
  <div style="margin:30px">
    <p class="font">部门管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="部门名称:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="最后操作时间:" label-width="180">
        <el-input v-model="form.lastmodifytime" />
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <el-button size="mini" type="primary" @click="add">新增</el-button>
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="部门名称" width="120" />
      <el-table-column prop="lastmodifytime" label="最后操作时间" width="120" />
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="updated(scope.row.id)">编辑</el-button>
          <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <addDialog :id="id" :dialog="addDialog" />
  </div>
</template>

<script>
import { getDept, delDept } from '@/api/staff/dept'
import { export2Excel } from '@/utils/common'
import addDialog from '@/views/staff/dept/add'
export default {
  name: 'Daily',
  components: { addDialog },
  data() {
    return {
      addDialog: {
        show: false
      },
      id: '',
      tableData: [],
      total: 0,
      form: {},
      queryForm: {
        page: 1,
        count: 10,
        name: ''
      }
    }
  },
  created() {
    this.initTable()
    this.tableData.push({ name: '销售部', lastmodifytime: '2020-9-11' })
  },
  methods: {
    initTable() {
      getDept(this.queryForm).then(res => {

      })
    },
    add() {
      this.addDialog.show = true
    },
    updated(id) {
      this.id = id
      this.addDialog.show = true
    },
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.queryForm.count = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.queryForm.page = page
      this.initTable()
    },
    deleted(id) {
      delDept(id).then(res => {
        if (res) {
          this.$message.success('删除成功')
        } else {
          this.$message.success('删除失败')
        }
      })
    },
    toExcel() {
      var list = this.tableData
      const th = ['部门名称', '最后操作时间']
      const filterVal = ['name', 'lastmodifytime']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '部门管理')
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
