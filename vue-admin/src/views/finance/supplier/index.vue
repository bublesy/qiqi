<template>
  <el-container>
    <el-main>
      <h1 align="center">供应商对账明细管理</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-date-picker
          v-model="form.time"
          type="month"
          placeholder="选择月"
          value-format="yyyy-MM"
          size="mini"
        />
        <el-tooltip class="item" effect="dark" content="按条件查询后打印" placement="top">
          <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
        </el-tooltip>
        <el-button type="primary" size="mini" @click="printing">生成月份打印单</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%;margin-top:20px"
          border=""
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="inProductDate" label="入仓日期" width="140" />
          <el-table-column v-show="true" prop="documentsNo" label="采购序号" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="paperLength" label="纸长" width="140" />
          <el-table-column v-show="true" prop="paperWidth" label="纸宽" width="140" />
          <el-table-column v-show="true" prop="purchaseQuantity" label="购入数量" width="140" />
          <el-table-column v-show="true" prop="returnNum" label="退货数量" width="140" />
          <el-table-column v-show="true" prop="returnAmount" label="退货金额" width="140" />
          <el-table-column v-show="true" prop="costPrice" label="成本价" width="140" />
          <el-table-column v-show="true" prop="totalAmount" label="总金额" width="140" />
          <el-table-column v-show="true" prop="creditDate" label="账款年月" width="160" />
          <el-table-column v-show="true" prop="alreadyMoney" label="已付" width="80" />
          <el-table-column v-show="true" prop="unPayed" label="欠款" width="80" />
          <el-table-column v-show="true" prop="settlementDate" label="结算日期" width="150">
            <template slot-scope="scope">
              <div v-for="(item,key) in scope.row.array" :key="key">
                {{ item }}
              </div>
            </template>
          </el-table-column>
          <el-table-column v-show="true" prop="carryTo" label="是否过账" width="80" />
          <el-table-column v-show="true" prop="settlementStatus" label="结算状态" width="80" />
          <el-table-column label="操作" width="213 ">
            <template slot-scope="scope">
              <el-button size="mini" @click="post(scope)">过账</el-button>
              <el-button type="success" size="mini" :disabled="scope.row.unPayed <= 0 ? true : false" @click="settlement(scope)">结算</el-button>
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
      <!--过账对话框  -->
      <el-dialog
        title="结算"
        :visible.sync="dialogVisible"
        width="25%"
        :close-on-click-modal="false"
      >
        <el-form ref="form" :rules="purRules" :model="formSet" size="mini" :inline="true">
          <el-form-item label="结算状态:" prop="settlementStatus">
            <el-select v-model="formSet.settlementStatus" placeholder="选择结算状态" style="width:150px">
              <el-option value="未结算" label="未结算" />
              <el-option value="部分结算" label="部分结算" />
              <el-option value="已结算" label="已结算" />
            </el-select>
          </el-form-item>
          <el-form-item label="金额:">
            <el-input-number v-model="formSet.money" :controls="false" disabled />
          </el-form-item>
          <el-form-item label="付款金额:" prop="alreadyMoney">
            <el-input-number v-model="formSet.alreadyMoney" :controls="false" @change="alreadyMoneyChange" />
          </el-form-item>
          <el-form-item label="待付款金额:">
            <el-input-number v-model="formSet.unPayed" :controls="false" disabled />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="setOk('form')">确 定</el-button>
        </span>
      </el-dialog>
      <!--过账对话框 -->
      <el-dialog
        title="编辑过账状态"
        :visible.sync="dialog"
        width="10%"
        :close-on-click-modal="false"
      >
        <el-form ref="form" :model="formCarryTo" size="mini" :inline="true">
          <el-select v-model="formCarryTo.carryTo" placeholder="请选择">
            <el-option value="未过账" label="未过账" />
            <el-option value="已过账" label="已过账" />
          </el-select>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialog = false">取 消</el-button>
          <el-button type="primary" @click="sure">确 定</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { purList, updated } from '@/api/purchase/purchase'
export default {
  name: 'Supplier',
  mixins: [initData],
  data() {
    return {
      // 选择客户
      customer: [],
      formAdd: '',
      // 表单数据
      tableData: [],
      purAddVisible: false,
      purRules: {
        settlementStatus: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        alreadyMoney: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      multipleSelection: [],
      form: {
        time: '',
        carryTo: false
      },
      carryTo: '',
      dialogVisible: false,
      dialog: false,
      options: [{
        value: '选项1',
        label: '过账'
      }, {
        value: '选项2',
        label: '未过账'
      }],
      value: '',
      formCarryTo: {
        carryTo: ''
      },
      formSet: {
        unPayed: ''
      }
    }
  },
  created() {
    this.init()
  },
  methods: {
    setOk(supForm) {
      this.$refs[supForm].validate((valid) => {
        if (valid) {
          updated(this.formSet).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              this.loadData()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
          this.dialogVisible = false
        } else {
          return false
        }
      })
    },
    // 过账
    post(scope) {
      this.dialog = true
      this.formCarryTo.id = scope.row.id
    },
    sure(val) {
      this.dialog = false
      updated(this.formCarryTo).then(res => {
        if (res) {
          this.$message.success(this.titleType + '成功')
          this.loadData()
        } else {
          this.$message.error(this.titleType + '失败')
        }
      })
    },
    alreadyMoneyChange() {
      console.log(this.formSet.unPayed, this.formSet.alreadyMoney)
      if (this.formSet.unPayed === this.formSet.money) {
        if (this.formSet.alreadyMoney > this.formSet.money) {
          this.$message.error('付款金额大于应收款金额！！')
          this.$set(this.formSet, 'alreadyMoney', this.formSet.money - this.formSet.unPayed)
          return
        }
      } else if (this.formSet.alreadyMoney > this.formSet.unPayed) {
        this.$message.error('付款金额大于应收款金额！！')
        this.$set(this.formSet, 'alreadyMoney', this.formSet.unPayed)
        return
      }
    },
    // 结算
    settlement(scope) {
      this.dialogVisible = true
      // 操作接口数据
      this.formSet.money = scope.row.totalAmount
      this.formSet.alreadyMoney = scope.row.alreadyMoney
      this.formSet.id = scope.row.id
      console.log(this.formSet.alreadyMoney)
      this.formSet.unPayed = this.formSet.money - this.formSet.alreadyMoney
      // updated(this.formSet).then(res => {
      //   console.log(res)
      // })
    },
    loadData() {
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      purList(this.queryParams).then(res => {
        this.tableData = res.list
        this.carryTo = this.tableData[0].carryTo
        this.tableData.forEach(a => {
          a.unPayed = a.totalAmount - a.alreadyMoney
          var list = a.settlementDate
          this.$set(a, 'array', list.split('/'))
          a.returnAmount = a.returnNum * a.costPrice
        })
        this.pagination.total = res.total
      })
    },
    // 打印
    printing() {
      // this.$router.push('/finance/supplier_printing')
      this.$router.push({ name: 'Supplier_printing', params: { 'data': this.tableData }})
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 编辑订单
    modifyPur(row) {
      this.purAddVisible = true
      this.titleType = '编辑'
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
  >>>.el-dialog__header {
    border: 1px;
  }
</style>
