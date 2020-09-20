<template>
  <el-container>
    <el-main>
      <h1 align="center">应收款管理</h1>
      <el-form :inline="true" :model="form" size="mini">
        <!-- <el-select v-model="value" placeholder="请选择月份" size="mini">
          <el-option
            v-for="item in month"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select> -->
        <el-select v-model="valu" placeholder="请选择客户" size="mini">
          <el-option
            v-for="item in customer"
            :key="item.value"
            :label="item.label"
            :value="item.valu"
          />
        </el-select>
        <el-date-picker
          v-model="value2"
          size="mini"
          type="monthrange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          :picker-options="pickerOptions"
        />
        <el-button type="primary" size="mini" @click="toQuery">查询</el-button>
        <!-- <el-button type="primary" size="mini" @click="purAdd">新增</el-button> -->
        <!-- <el-button type="warning" size="mini" @click="selectPrinting">选择打印</el-button>
        <el-button type="warning" size="mini" @click="wholePrinting">整页打印</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button> -->
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
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="customer" label="客户" width="140" />
          <el-table-column v-show="true" prop="early" label="前期" width="140" />
          <el-table-column v-show="true" prop="data1" :label="ac" width="140" />
          <el-table-column v-show="true" prop="data2" label="2012年-05月" width="140" />
          <el-table-column v-show="true" prop="data3" label="2012年-06月" width="140" />
          <el-table-column v-show="true" prop="data4" label="2012年-07月" width="140" />
          <el-table-column v-show="true" prop="data5" label="2012年-08月" width="140" />
          <el-table-column v-show="true" prop="data6" label="2012年-09月" width="140" />
          <el-table-column v-show="true" prop="total" label="合计" width="140" />
          <!-- <el-table-column v-show="true" prop="money" label="金额" width="140" /> -->
          <!-- <el-table-column v-show="true" prop="purchaseQuantity" label="采购数量" width="143" /> -->
          <!-- <el-table-column v-show="true" prop="batching" label="配料面积" width="140" />
          <el-table-column v-show="true" prop="squarePrice" label="平方价" width="140" />
          <el-table-column v-show="true" prop="unitPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="amount" label="金额" width="140" />
          <el-table-column v-show="true" prop="unit" label="单位" width="140" /> -->
          <el-table-column label="操作" width="354">
            <template slot-scope="scope">
              <!-- <el-link type="danger" size="small" @click="drop(scope.row.id)">删除</el-link> -->
              <!-- <el-link type="primary" size="small" @click="modifyPur(scope.row.id)">编辑</el-link> -->
              <el-link type="warning" size="small" @click="printing(scope.row.id)">生成打印单</el-link>
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
      <el-dialog :title="titleType+'对账明细表'" :visible.sync="purAddVisible" :close-on-click-modal="false">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="120px">
          <el-form-item label="客户" prop="supplier">
            <el-input v-model="formAdd.customerName" disabled>/>
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

          <el-form-item label="数量">
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
      </el-dialog>
    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { export2Excel } from '@/utils/common'

export default {
  name: 'Verify',
  mixins: [initData],
  data() {
    return {
      // 选择月份
      month: [{
        value: '选项1',
        label: '2020-02-11'
      }, {
        value: '选项2',
        label: '2018-06-11'
      }, {
        value: '选项3',
        label: '2014-09-11'
      }, {
        value: '选项4',
        label: '2012-01-11'
      }, {
        value: '选项5',
        label: '2013-02-11'
      }],
      ac: '',
      // 选择客户
      value: '',
      customer: [{
        value: '选项1',
        label: '平安'
      }, {
        value: '选项2',
        label: '吉安'
      }, {
        value: '选项3',
        label: '上海'
      }, {
        value: '选项4',
        label: '沈阳'
      }, {
        value: '选项5',
        label: '湖南'
      }],
      valu: '',
      form: {},
      formAdd: { },
      // 表单数据
      tableData: [{
        customer: 12,
        early: 202,
        data1: 12,
        data2: 780,
        data3: 90,
        data4: 13.09,
        data5: 7,
        data6: 7,
        total: 856

      }],
      addTableData: [],
      customerFor: [{
        id: '1',
        name: '迪迦'
      }],
      purAddVisible: false,
      purRules: {
        supplier: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        pricing: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        billingTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      supplierFor: [{
        id: '1',
        name: '腾讯'
      }, {
        id: '2',
        name: '阿里'
      }],
      pricingFor: [{
        id: '1',
        name: '净边'
      }, {
        id: '2',
        name: '净宽'
      }],
      titleType: '',
      taskNumberVisible: false,
      taskNumberTable: [{
        taskNumber: '1',
        taskName: '就这?'
      }, {
        taskNumber: '2',
        taskName: '就这a ?'
      }],
      modifyTaskTable: [{
        customerName: '张三',
        taskNumber: '2'
      }],
      multipleSelection: [],
      indexId: {},
      pickerOptions: {
        shortcuts: [{
          text: '本月',
          onClick(picker) {
            picker.$emit('pick', [new Date(), new Date()])
          }
        }, {
          text: '今年至今',
          onClick(picker) {
            const end = new Date()
            const start = new Date(new Date().getFullYear(), 0)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近六个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setMonth(start.getMonth() - 6)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      value1: '',
      value2: ''
    }
  },
  created() {
    this.ac = '2012年-05月'
  },
  methods: {
    toQuery() {

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
      this.$router.push('/finance/receivables_printing')
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
  >>>.el-dialog__header {
    border: 1px;
  }
</style>
