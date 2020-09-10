<template>
  <div style="margin:30px">
    <p class="font">客户资料管理</p>
    <el-button type="primary" size="mini" @click="editDialog.show = true">新增</el-button>
    <el-table
      ref="multipleTable"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" label="编码" width="120" />
      <el-table-column prop="name" label="简称" width="120" />
      <el-table-column prop="name" label="全称" width="120" />
      <el-table-column prop="name" label="电话" width="120" />
      <el-table-column prop="name" label="手机" width="120" />
      <el-table-column prop="name" label="传真" width="120" />
      <el-table-column prop="name" label="联系人" width="120" />
      <el-table-column prop="name" label="网址" width="120" />
      <el-table-column prop="name" label="地址" width="120" />
      <el-table-column prop="name" label="开户银行" width="120" />
      <el-table-column prop="name" label="指定业务员" width="120" />
      <el-table-column prop="name" label="银行账号" width="120" />
      <el-table-column prop="name" label="税务登记账号" width="120" />
      <el-table-column prop="name" label="结算方式" width="120" />
      <el-table-column prop="name" label="币种" width="120" />
      <el-table-column prop="name" label="期初应收" width="120" />
      <el-table-column prop="name" label="信用额度" width="120" />
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
    <editDialog :dialog="editDialog" />
  </div>
</template>

<script>
import editDialog from '@/views/basicdata/customer/edit'
export default {
  name: 'Daily',
  components: { editDialog },
  data() {
    return {
      editDialog: {
        show: false
      },
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
