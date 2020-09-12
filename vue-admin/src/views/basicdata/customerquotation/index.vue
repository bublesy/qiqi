<template>
  <div style="margin:30px">
    <p class="font">客户报价单管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="简称:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="全称:">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="名称:">
        <el-input v-model="form.name" />
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <el-button size="mini" type="primary" @click="add">新增</el-button>
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
      <el-table-column type="selection" width="55" />
      <!-- <el-table-column type="index" /> -->
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="name" label="名称" width="120" />
      <el-table-column label="限定最大纸长" width="120">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.limitPaperLength" />
        </template>
      </el-table-column>
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
import addDialog from '@/views/basicdata/boxclass/add'
import { getBoxClass, delBoxClass } from '@/api/basedata/boxclass'
import { export2Excel } from '@/utils/common'
export default {
  components: { addDialog },
  data() {
    return {
      id: '',
      form: {},
      tableData: [],
      addDialog: {
        show: false
      },
      queryForm: {
        page: 1,
        count: 10,
        code: '',
        name: '',
        limitPaperLength: false
      }

    }
  },
  created() {
    this.initTable()
    this.tableData.push({ code: 1, name: 'aa', limitPaperLength: true })
  },
  methods: {
    handleSizeChange(size) {
      this.size = this.queryForm.count
      this.initTable()
    },
    handleCurrentChange(page) {
      this.page = this.queryForm.page
      this.initTable()
    },
    initTable() {
      getBoxClass().then(res => {

      })
    },
    deleted(id) {
      delBoxClass(id).then(res => {
        if (res) {
          this.$message.success('删除成功')
        } else {
          this.$message.success('删除失败')
        }
      })
    },
    add() {
      this.addDialog.show = true
    },
    updated(id) {
      this.id = id
      this.addDialog.show = true
    },
    query() {

    },
    handleSelectionChange() {},
    toExcel() {
      var list = this.tableData
      const th = ['编码', '名称', '限定最大纸长']
      const filterVal = ['code', 'name', 'limitPaperLength']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '箱类设定')
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
