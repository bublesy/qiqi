<template>
  <div style="margin:30px">
    <p class="font">常用箱资料管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="简称:">
        <el-input v-model="form.as" />
      </el-form-item>
      <el-form-item label="全称:">
        <el-input v-model="form.fullName" />
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <el-button size="mini" type="primary" @click="add">新增</el-button>
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
      <el-table-column prop="as" label="简称" width="120" />
      <el-table-column prop="fullName" label="全称" width="120" />
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="updated(scope.row.id)">编辑</el-button>
          <el-button type="danger" size="mini" @click="deleted(scope.row.id)">删除</el-button>
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
        as: '',
        fullName: ''
      }

    }
  },
  created() {
    this.initTable()
    this.tableData.push({ code: 1, as: 'aa', fullName: 'ss' })
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
      delBoxClass(id).then(res => {})
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
    handleSelectionChange() {}
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
