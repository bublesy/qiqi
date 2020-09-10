<template>
  <div id="print" style="margin:30px">
    <p class="font">人事档案管理</p>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="工号" width="120" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="name" label="性别" width="120" />
      <el-table-column prop="name" label="是否在职" width="120" />
      <el-table-column prop="name" label="身份证号" width="120" />
      <el-table-column prop="name" label="民族" width="120" />
      <el-table-column prop="name" label="出生日期" width="120" />
      <el-table-column prop="name" label="入职日期" width="120" />
      <el-table-column prop="name" label="学历" width="120" />
      <el-table-column prop="name" label="血型" width="120" />
      <el-table-column prop="name" label="身高" width="120" />

      <el-table-column prop="name" label="部门" width="120" />
      <el-table-column prop="name" label="职位" width="120" />
      <el-table-column prop="name" label="体重" width="120" />
      <el-table-column prop="name" label="政治面貌" width="120" />
      <el-table-column prop="name" label="联系电话" width="120" />
      <el-table-column prop="name" label="户口地址" width="120" />

      <el-table-column prop="name" label="日工资" width="120" />

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
  </div>
</template>

<script>
export default {
  name: 'Personnel',
  data() {
    return {
      tableData: [],
      page: 1,
      size: 10,
      total: 0
    }
  },
  methods: {
    initTable() {},
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.size = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.page = page
      this.initTable()
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
