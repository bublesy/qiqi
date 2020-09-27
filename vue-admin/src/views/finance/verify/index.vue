<template>
  <el-container>
    <el-main>
      <h1 align="center">对账明细表管理</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="交货日期:">
          <el-date-picker
            v-model="form.deliveryDate"
            type="month"
            placeholder="选择月"
            value-format="yyyy-MM"
          />
        </el-form-item>
        <el-form-item label="客户:" prop="name">
          <el-input v-model="form.name" clearable />
        </el-form-item>
        <el-button type="primary" size="mini" @click="selectData">查询</el-button>
        <el-button type="primary" size="mini" :disabled="disabled" @click="printing">生成月份打印单</el-button>
        <!-- <el-button type="primary" size="mini" @click="purAdd">新增</el-button> -->
        <!-- <el-button type="warning" size="mini" @click="selectPrinting">选择打印</el-button>
        <el-button type="warning" size="mini" @click="wholePrinting">整页打印</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button> -->
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData1"
          highlight-current-row
          style="width: 100%;margin-top:20px"
          border
          @selection-change="handleSelectionChange"
        >

          <el-table-column v-show="true" prop="name" label="客户名称" width="120" />
          <el-table-column v-show="true" prop="outNo" label="出货单号" width="120" />
          <el-table-column v-show="true" prop="shipDate" label="出货日期" width="120" />
          <el-table-column v-show="true" prop="deliveryDate" label="交货日期" width="120" />
          <el-table-column v-show="true" prop="modelNo" label="物品单号/款号" width="120" />
          <el-table-column v-show="true" prop="boxType" label="箱型" width="80" />
          <el-table-column v-show="true" label="长x宽x高" width="140">
            <template slot-scope="scope">
              {{ scope.row.length+' X '+scope.row.width+' X '+scope.row.height }}
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="orderNum" label="数量" width="80" />
          <el-table-column v-show="true" prop="perPrice" label="单价" width="80" />
          <el-table-column v-show="true" prop="money" label="金额" width="80" />
          <el-table-column v-show="true" prop="beginReceive" label="期初" width="80" />

          <el-table-column v-show="true" prop="payed" label="已付" width="80" />
          <el-table-column v-show="true" prop="unPayed" label="欠款" width="80" />

          <el-table-column v-show="true" prop="post" label="是否过账" width="80" />
          <el-table-column v-show="true" prop="settlement" label="结算状态" width="80" />
          <el-table-column label="操作" width="213 ">
            <template slot-scope="scope">
              <el-button size="mini" @click="post(scope.row)">过账</el-button>
              <el-button type="success" size="mini" @click="settlement(scope.row)">结算</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
          background
          layout="total, sizes, prev, pager, next"
          :total="pagination.total"
          :current-page="pagination.page"
          :page-size="pagination.size"
          align="center"
          @size-change="sizeChange"
          @current-change="pageChange"
        />
      </div>
      <!-- 新增/编辑对账明细单 -->
      <!-- <el-dialog :title="titleType+'对账明细表'" :visible.sync="purAddVisible" :close-on-click-modal="false">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
          <el-form-item label="客户名称" prop="name">
            <el-input v-model="formAdd.name" disabled>/>
            </el-input></el-form-item>
          <el-form-item label="客户电话" prop="pricing">
            <el-select v-model="formAdd.pricing">
              <el-option
                v-for="item in pricingFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="出货日期" prop="billingTime">
            <el-date-picker
              v-model="formAdd.billingTime"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>
          <el-form-item label="出货单号">
            <el-select v-model="formAdd.customerName" size="mini">
              <el-option
                v-for="item in customerFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="物品单号/款号">
            <el-input v-model="formAdd.ridgeType" />
          </el-form-item>

          <el-form-item label="箱型">
            <el-input v-model="formAdd.parPreSpe" />
          </el-form-item>

          <el-form-item label="长x宽x高">
            <el-input v-model="formAdd.material" />
          </el-form-item>

          <el-form-item
            label="数量"
            :rules="[
              { required: true, message: '数量不能为空'},
              { type: 'number', message: '数量必须为数字值'}
            ]"
          >
            <el-input v-model="formAdd.paperLength" />
          </el-form-item>

          <el-form-item label="单价">
            <el-input v-model="formAdd.paperWidth" />
          </el-form-item>

          <el-form-item label="金额">
            <el-input v-model="formAdd.orderQuantity" />
          </el-form-item>

        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="purAddNo">取 消</el-button>
          <el-button size="small" type="primary" @click="purAddOk('purForm')">确 定</el-button>
        </span>
      </el-dialog> -->

    </el-main>
    <postDialog :id="id" :dialog="postDialog" @init="selectData" />
    <settlementDialog :id="id2" :dialog="settlementDialog" @init="selectData" />
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { export2Excel } from '@/utils/common'
import { record, customer } from '@/api/accessories/means'
import postDialog from '@/views/finance/verify/post'
import settlementDialog from '@/views/finance/verify/settlement'

// import { record } from '@/finance/verify'
export default {
  name: 'Verify',
  components: { postDialog, settlementDialog },
  mixins: [initData],
  data() {
    return {
      id: '',
      id2: '',
      postDialog: {
        show: false
      },
      settlementDialog: {
        show: false
      },
      // 选择月份
      month: [],
      // 选择客户
      value: '',
      customer: [],
      valu: '',
      form: {
        count: 10,
        customerId: '',
        deliveryDate: '',
        page: 1,
        fullName: '',
        date: '',
        id: '',
        time: '',
        name: ''
      },
      formAdd: {
        page: 1,
        count: 10
      },
      // 表单数据
      tableData: [{
        customerName: '',
        phone: '',
        data: '2020--11',
        shipment: '',
        goods: '',
        type: '',
        long: '',
        number: '',
        price: '',
        money: ''

      }],
      tableData1: [],
      purAddVisible: false,
      purRules: {
        supplier: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        pricing: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        billingTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      taskNumberVisible: false,
      multipleSelection: [],
      indexId: {},
      fullNames: [],
      disabled: true

    }
  },
  created() {
    this.init()
  },
  methods: {
    post(data) {
      this.id = data.id
      this.postDialog.show = true
    },
    settlement(data) {
      this.id2 = data.id
      this.settlementDialog.show = true
    },
    supplierList() {},
    // 获取列表数据
    loadData() {
      record(this.form).then(res => {
        this.tableData1 = res.list
        this.tableData1.forEach(data => {
          data.unPayed = data.money - data.beginReceive - data.payed
          if (data.post === null) {
            data.post = '未过账'
          }
          if (data.settlement === null) {
            data.settlement = '未结算'
          }
        })
          .this.pagination.total = res.total
      })
      // 获取用户数据
      customer().then(res => {
        this.tableData = res
      })
    },
    selectData() {
      this.loadData()
      this.disabled = false
    },
    // 导出
    toExcel() {
      var list = this.tableData
      const th = ['编码', '名称', '限定最大纸长']
      const filterVal = ['code', 'name', 'limitPaperLength']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '采购单导出')
    },
    // 选择打印
    selectPrinting() {
      if (this.form.carryTo === '已过期') {
        this.$router.push('/purchase_not_included_overdue')
      } else if (this.form.carryTo === '未过期') {
        this.$router.push('/purchase_not_included')
      } else {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择打印的内容！！！')
          return
        } else {
          this.$router.push('/purchase_order_printing')
        }
      }
    },
    // 整页打印
    wholePrinting() {
      if (this.form.carryTo === '已过期') {
        this.$router.push('/purchase_not_included_overdue')
      } else if (this.form.carryTo === '未过期') {
        this.$router.push('/purchase_not_included')
      } else {
        this.$router.push('/purchase_order_printing')
      }
    },
    // 打印
    printing() {
      // tableData1
      this.$router.push({ name: 'Verify_printing', params: { 'data': this.tableData1 }})
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 删除
    drop() {
      this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 编辑订单
    modifyPur(row) {
      this.purAddVisible = true
      this.titleType = '编辑'
    },
    // 新增订单
    purAdd() {
      this.purAddVisible = true
      this.titleType = '新增'
      // 新增初始化数据
      this.formAdd = {}
    },
    // 取消
    purAddNo() {
      this.purAddVisible = false
      this.addTableData = []
    },
    purAddOk(purForm) {
      this.$refs[purForm].validate((valid) => {
        if (valid) {
          this.purAddVisible = false
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style scoped lang="scss">
.el-dialog__header {
    border: 1px;
  }
</style>
