<template>
  <div style="margin:30px">
    <p class="font">职位管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="部门名称:">
        <el-input v-model="form.name" />
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
      <el-table-column prop="name" label="职位名称" width="120" />
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
      :current-page="queryForm.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="queryForm.count"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <positionDialog :id="id" :dialog="positionDialog" />
  </div>
</template>

<script>
import positionDialog from '@/views/staff/position/add'
import { export2Excel } from '@/utils/common'
import { getPosition, delPosition } from '@/api/staff/position'
export default {
  name: 'Position',
  components: { positionDialog },
  data() {
    return {
      positionDialog: {
        show: false
      },
      tableData: [],
      total: 0,
      form: {},
      queryForm: {
        page: 1,
        count: 10,
        name: '',
        lastmodifytime: null
      },
      id: ''
    }
  },
  created() {
    this.initTable()
    this.tableData.push({ name: '销售员' })
  },
  methods: {
    query() {
      this.initTable()
    },
    initTable() {
      getPosition().then(res => {
        if (res) {
          this.tableData = res.list
          this.total = res.total
        }
      })
    },
    add() {
      this.positionDialog.show = true
    },
    updated(id) {
      this.id = id
      this.positionDialog.show = true
    },
    deleted(id) {
      delPosition(id).then(res => {
        if (res) {
          this.$message.success('删除成功')
        } else {
          this.$message.success('删除失败')
        }
      })
    },
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.size = this.queryForm.page
      this.initTable()
    },
    handleCurrentChange(page) {
      this.page = this.queryForm.count
      this.initTable()
    },
    toExcel() {
      console.log('aa')
      var list = this.tableData
      const th = ['职位名称']
      const filterVal = ['name']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '职位管理')
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
