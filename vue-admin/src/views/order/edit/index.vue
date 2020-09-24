<template>
  <div style="padding:10px">
    <p class="font">客户订单</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="任务编号:">
        <el-input v-model="form.no" />
      </el-form-item>
      <el-form-item label="客户名称:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="客户单号:">
        <el-input v-model="form.customerNo" />
      </el-form-item>
      <el-form-item label="下单日期:">
        <el-date-picker
          v-model="form.orderDate"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item><br>
      <el-form-item label="交货日期:">
        <el-date-picker
          v-model="form.deliveryDate"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="仓库状态:">
        <el-select v-model="form.wosState" placeholder="请选择" clearable>
          <el-option label="已送货" value="已送货" />
          <el-option label="纸板已入仓" value="纸板已入仓" />
          <el-option label="入仓未出货" value="入仓未出货" />
          <el-option label="新订单" value="新订单" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否成品:">
        <el-select v-model="form.isProduct" placeholder="请选择" clearable>
          <el-option label="全部" value="" />
          <el-option label="成品" value="成品" />
          <el-option label="非成品" value="非成品" />
        </el-select>
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <el-button size="mini" type="primary" @click="add">新增</el-button>
    <el-button size="mini" type="warning" @click="print">批量打印</el-button>
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
    <el-card>
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
        <el-table-column prop="no" label="任务编号" width="120" />
        <el-table-column prop="name" label="客户名称" width="120" />
        <el-table-column prop="customerNo" label="客户单号" width="120" />
        <el-table-column prop="modelNo" label="款号" width="120" />
        <el-table-column prop="boxType" label="箱型" width="120" />
        <el-table-column prop="material" label="材质" width="120" />
        <el-table-column prop="cartonSize" label="纸箱尺寸(mm)" width="180">
          <template slot-scope="scope">
            {{ scope.row.length+' X '+scope.row.width+' X '+scope.row.height }}
          </template>
        </el-table-column>
        <el-table-column prop="orderNum" label="订单数量" width="120" />
        <el-table-column prop="money" label="金额" width="120" />
        <el-table-column prop="incomeNum" label="纸板到货数量" width="120" />
        <el-table-column prop="space" label="仓位" width="120" />
        <el-table-column prop="productNum" label="已产数量" width="120" />
        <el-table-column prop="productSpace" label="成品仓位" width="120" />
        <el-table-column prop="sendNum" label="已送数量" width="120" />
        <el-table-column prop="refundNum" label="退货数量" width="120" />
        <!-- <el-table-column prop="lossNum" label="损耗数量" width="120" /> -->
        <el-table-column prop="orderDate" label="下单日期" width="120" />
        <el-table-column prop="deliveryDate" label="交货日期" width="120" />
        <el-table-column prop="wosState" label="仓库状态" width="120" />
        <!-- <el-table-column prop="usedBox" label="常用箱" width="120" /> -->
        <!-- <el-table-column prop="money" label="金额" width="120" />
        <el-table-column prop="supplier" label="供方" width="120" /> -->
        <!-- <el-table-column prop="createdTime" label="制单时间" width="120" />
        <el-table-column prop="making" label="制单人" width="120" />
        <el-table-column prop="auditTime" label="审核时间" width="120" />
        <el-table-column prop="audit" label="审核人" width="120" /> -->
        <el-table-column label="操作" width="650">
          <template slot-scope="scope">
            <el-button type="info" size="mini" @click="view(scope.row.id)">查看</el-button>
            <el-button type="warning" size="mini" :disabled="scope.row.audit==='审核'?true:false" @click="updated(scope.row.id)">编辑</el-button>
            <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
              <el-button slot="reference" type="danger" size="mini" :disabled="scope.row.audit==='审核'?true:false">删除</el-button>
            </el-popconfirm>
            <el-button type="success" size="mini" @click="singlePrint(scope.row)">打印</el-button>
            <el-button type="warning" size="mini" @click="orderAgain(scope.row.id)">再次下单</el-button>
            <el-button type="primary" size="mini" @click="generate(scope.row)">生成施工单</el-button>
            <el-popconfirm title="审核后将不可更改!" @onConfirm="audit(scope.row.id)">
              <el-button slot="reference" type="primary" size="mini" :disabled="scope.row.audit==='审核'?true:false">审核</el-button>
            </el-popconfirm>
            <el-button type="primary" size="mini" @click="refund(scope.row)" @init="initTable">退货</el-button>
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
    </el-card>
    <editDialog :id="id" :dialog="editDialog" :flag="flag" :status="status" :is-view="isView" @init="initTable" />
    <refundDialog :dialog="refundDialog" :refund-id="refundId" :send-num="sendNum" :refund-num2="refundNum2" @init="initTable" />
  </div>
</template>

<script>
import editDialog from '@/views/order/edit/edit'
import refundDialog from '@/views/order/edit/refund'
import { export2Excel } from '@/utils/common'
import { getOrder, delOrder, getUser, addOrUpdateOrder, getSingleOrder } from '@/api/order/customerOrder'
export default {
  name: 'Edit',
  components: { editDialog, refundDialog },
  data() {
    return {
      flag: false,
      id: '测试施工单id',
      total: 0,
      refundId: '',
      sendNum: 0,
      refundNum2: '',
      form: {
        page: 1,
        count: 10,
        // no: '',
        // modelNo: '',
        // boxType: '',
        // supplier: ''
        isProduct: ''
      },
      editDialog: {
        show: false
      },
      refundDialog: {
        show: false
      },
      tableData: [],
      value: '',
      options: [],
      boxTypeOptions: [],
      supplierOptions: [],
      select: [],
      status: false,
      isView: false

    }
  },
  created() {
    this.initTable()
  },
  methods: {
    view(id) {
      this.id = id
      this.isView = true
      this.editDialog.show = true
    },
    refund(row) {
      this.sendNum = row.sendNum
      this.refundNum2 = row.refundNum
      console.log(this.sendNum)
      console.log(this.refundNum)
      this.refundId = row.id
      this.refundDialog.show = true
    },
    audit(id) {
      getSingleOrder(id).then(res => {
        var data = res
        getUser().then(res => {
          data.auditBy = res.nickname
          data.audit = '审核'
          addOrUpdateOrder(data).then(res => {
            if (res) {
              this.$message.success('审核成功')
              this.initTable()
            }
          })
        })
      })
    },
    query() {
      this.initTable()
    },
    generate(row) {
      this.$router.push({
        path: '/index',
        query: { row: row }
      })
    },
    orderAgain(id) {
      this.flag = true
      this.id = id
      this.isView = false
      this.status = true
      this.editDialog.show = true
    },
    add() {
      this.flag = true
      this.id = ''
      this.editDialog.show = true
    },
    updated(id) {
      this.flag = false
      this.isView = false
      this.id = id
      this.editDialog.show = true
    },
    initTable() {
      getOrder(this.form).then(res => {
        this.tableData = res.list
        this.tableData.forEach(x => {
          x.cartonSize = x.length + 'X' + x.width + 'X' + x.height
        })
        this.total = res.total
      })
    },
    handleSizeChange(size) {
      this.form.count = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.form.page = page
      this.initTable()
    },
    deleted(id) {
      delOrder(id).then(res => {
        if (res) {
          this.initTable()
          this.$message.success('删除成功')
        } else {
          this.$message.error('删除失败')
        }
      })
    },
    toExcel() {
      var list = this.tableData
      const th = ['任务编号', '客户名称', '客户单号', '款号', '箱型', '材质', '纸箱尺寸(mm)', '订单数量', '金额', '纸板到货数量', '已产数量', '成品仓位', '已送数量', '退货数量', '下单日期', '交货日期', '仓库状态']
      const filterVal = ['no', 'name', 'customerNo', 'modelNo', 'boxType', 'material', 'cartonSize', 'orderNum', 'money', 'incomeNum', 'productNum', 'productSpace', 'sendNum', 'refundNum', 'orderDate', 'deliveryDate', 'wosState']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '客户订单')
    },
    print() {
      if (this.select.length === 0) {
        this.select = this.tableData
      }
      this.$router.push({
        path: '/customerOrder',
        query: this.select
      })
    },
    handleSelectionChange(select) {
      this.select = select
    },
    singlePrint(row) {
      var list = []
      list.push(row)
      this.$router.push({
        path: '/customerOrder',
        query: list
      })
    }
  }

}
</script>

<style scoped>
.el-link{
  color: rgb(0, 162, 255);
  font: 1em sans-serif;
}
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
