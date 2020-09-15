<template>
  <div style="margin:30px">
    <p class="font">客户资料管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="编码:">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="简称:">
        <el-input v-model="form.shorts" />
      </el-form-item>
      <el-form-item label="电话:">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="手机:">
        <el-input v-model="form.mobilePhone" />
      </el-form-item>
    </el-form>
    <el-button type="primary" size="mini" @click="query">查询</el-button>
    <el-button type="primary" size="mini" @click="add">新增</el-button>
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
    <el-table
      ref="table"
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
    >
      <el-table-column type="index" width="55" />
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="shorts" label="简称" width="120" />
      <el-table-column prop="fullName" label="全称" width="120" />
      <el-table-column prop="phone" label="电话" width="120" />
      <el-table-column prop="mobilePhone" label="手机" width="120" />
      <el-table-column prop="fax" label="传真" width="120" />
      <el-table-column prop="contact" label="联系人" width="120" />
      <el-table-column prop="url" label="网址" width="120" />
      <el-table-column prop="address" label="地址" width="120" />
      <el-table-column prop="bank" label="开户银行" width="120" />
      <el-table-column prop="salesman" label="指定业务员" width="120" />
      <el-table-column prop="account" label="银行账号" width="120" />
      <el-table-column prop="registration" label="税务登记账号" width="120" />
      <el-table-column prop="payment" label="结算方式" width="120" />
      <el-table-column prop="currency" label="币种" width="120" />
      <el-table-column prop="beginReceive" label="期初应收" width="120" />
      <el-table-column prop="credit" label="信用额度" width="120" />
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button size="mini" type="warning" @click="updated(scope.row.id)">编辑</el-button>
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
    <editDialog :id="id" :dialog="editDialog" @init="initTable" />
  </div>
</template>

<script>
import editDialog from '@/views/basicdata/customer/edit'
import { getCustomer, delCustomer } from '@/api/basedata/customer'
import { export2Excel } from '@/utils/common'
export default {
  name: 'Daily',
  components: { editDialog },
  data() {
    return {
      id: '',
      editDialog: {
        show: false
      },
      tableData: [],
      form: {
        page: 1,
        count: 10
      },

      total: 0
    }
  },
  created() {
    this.initTable()
  },
  methods: {
    initTable() {
      getCustomer(this.form).then(res => {
        this.tableData = res.list
        this.total = res.total
      })
    },
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.form.count = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.form.page = page
      this.initTable()
    },
    query() {
      this.initTable()
    },
    add() {
      this.id = ''
      this.editDialog.show = true
    },
    updated(id) {
      if (id === null || id === undefined) {
        return
      }
      this.id = id
      this.editDialog.show = true
    },
    deleted(id) {
      if (id === null || id === undefined) {
        return
      }
      delCustomer(id).then(res => {
        if (res) {
          this.$message.success('删除成功')
        } else {
          this.$message.success('删除失败')
        }
        this.initTable()
      })
    },
    toExcel() {
      var list = this.tableData
      const th = ['编码', '简称', '全称', '电话', '手机', '传真', '联系人', '网址', '地址', '开户银行', '指定业务员', '银行账号', '税务登记账号', '结算方式', '币种', '期初应收', '信用额度']
      const filterVal = ['code', 'shorts', 'fullName', 'phone', 'mobilePhone', 'fax', 'contact', 'url', 'address', 'bank', 'salesman', 'account', 'registration', 'payment', 'currency', 'beginReceive', 'credit']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '客户资料管理')
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
