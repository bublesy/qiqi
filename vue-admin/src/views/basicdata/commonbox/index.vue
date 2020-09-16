<template>
  <div style="margin:30px">
    <p class="font">常用箱资料管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="简称:">
        <el-input v-model="form.shorts" />
      </el-form-item>
      <el-form-item label="全称:">
        <el-input v-model="form.fullName" />
      </el-form-item>
      <el-form-item label="常用箱:">
        <el-input v-model="form.commonBoxManagement" />
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
    >
      <el-table-column type="index" />
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="shorts" label="简称" width="120" />
      <el-table-column prop="fullName" label="全称" width="120" />
      <el-table-column prop="commonBoxManagement" label="常用箱" width="120" />
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
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.count"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <addDialog :id="id" :dialog="addDialog" @init="initTable" />
  </div>
</template>

<script>
import addDialog from '@/views/basicdata/commonbox/add'
import { getCommonBox, delCommonBox } from '@/api/basedata/commonbox'
import { export2Excel } from '@/utils/common'
export default {
  components: { addDialog },
  data() {
    return {
      id: '',
      tableData: [],
      addDialog: {
        show: false
      },
      total: 0,
      form: {
        page: 1,
        count: 10
      }

    }
  },
  created() {
    this.initTable()
  },
  methods: {
    handleSizeChange(size) {
      this.form.count = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.form.count = page
      this.initTable()
    },
    initTable() {
      getCommonBox(this.form).then(res => {
        this.tableData = res.list
        this.total = res.total
      })
    },
    deleted(id) {
      delCommonBox(id).then(res => {
        if (res) {
          this.$message.success('删除成功')
          this.initTable()
        } else {
          this.$message.success('删除失败')
        }
      })
    },
    add() {
      this.id = ''
      this.addDialog.show = true
    },
    updated(id) {
      this.id = id
      this.addDialog.show = true
    },
    query() {
      this.initTable()
    },
    handleSelectionChange() {},
    toExcel() {
      var list = this.tableData
      const th = ['编码', '简称', '全称', '常用箱']
      const filterVal = ['code', 'shorts', 'fullName', 'commonBoxManagement']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '常用箱资料管理')
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
