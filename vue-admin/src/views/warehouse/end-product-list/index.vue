<template>
  <el-container>
    <el-main>
      <h1 align="center">成品仓库</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
        <el-form-item label="开单人">
          <el-input v-model="form.drawer" />
        </el-form-item>

        <el-form-item label="成品送货:">
          <el-select v-model="form.carryTo" :clearable="true">
            <el-option label="已送货" value="已送货" />
            <el-option label="未送货" value="未送货" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.time"
            align="right"
            type="date"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-button type="primary" size="mini">查询</el-button>
        <el-button type="primary" size="mini" @click="purAdd">新增</el-button>
        <el-button type="warning" size="mini" @click="selectPrinting">选择打印</el-button>
        <el-button type="warning" size="mini" @click="wholePrinting">整页打印</el-button>
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="tableData"
          highlight-current-row
          style="width: 100%"
          align="center"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="documentsNo" label="入仓单号" width="140" />
          <el-table-column v-show="true" prop="taskNumber" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="customerName" label="客户名称" width="140" />
          <el-table-column v-show="true" prop="serialName" label="物品名称" width="140" />
          <el-table-column v-show="true" prop="customerNo" label="客户单号" width="140" />
          <el-table-column v-show="true" prop="orderQuantity" label="订单数量" width="140" />
          <el-table-column v-show="true" prop="deliveryQuantity" label="送货数量" width="140" />
          <el-table-column v-show="true" prop="typeNo" label="款号" width="140" />
          <el-table-column v-show="true" prop="boxType" label="箱型" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="length" label="长" width="140" />
          <el-table-column v-show="true" prop="width" label="宽" width="140" />
          <el-table-column v-show="true" prop="height" label="高" width="140" />
          <el-table-column v-show="true" prop="unitPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="EndProductPos" label="成品仓位" width="140" />
          <el-table-column v-show="true" prop="warehousingTime" label="入仓时间" width="140" />
          <el-table-column label="操作" width="180">
            <template slot-scope="scope">
              <el-link type="danger" size="small" @click="drop(scope.row.id)">删除</el-link>
              <el-link type="primary" size="small" @click="modifyPur(scope.row.id)">编辑</el-link>
              <el-link type="warning" size="small" @click="printing">生成送货单</el-link>
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
      <!-- 新增/编辑纸板入仓单 -->
      <el-dialog :title="titleType+'成品入仓单'" :visible.sync="purAddVisible">

        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="80px">
          <el-form-item label="供方" prop="supplier">
            <el-select v-model="formAdd.supplier">
              <el-option
                v-for="item in supplierFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="入仓单号" prop="documentsNo">
            <el-input v-model="formAdd.documentsNo" disabled />
          </el-form-item>
          <el-form-item label="开单人">
            <el-select v-model="formAdd.drawer">
              <el-option
                v-for="item in drawerFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="开单日期" prop="billingTime">
            <el-date-picker
              v-model="formAdd.billingTime"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>

          <el-form-item label="客户名称">
            <el-select v-model="formAdd.customerName" size="mini">
              <el-option
                v-for="item in customerFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="交货日期">
            <el-date-picker
              v-model="formAdd.deliveryDate"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>

          <el-form-item label="客户单号">
            <el-select v-model="formAdd.customerNo" size="mini">
              <el-option
                v-for="item in customerNoFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="物品名称">
            <el-select v-model="formAdd.serialName" size="mini">
              <el-option
                v-for="item in serialNameFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="规格">
            <el-select v-model="formAdd.specifications" size="mini">
              <el-option
                v-for="item in specificationsFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="款号">
            <el-input v-model="formAdd.typeNo" />
          </el-form-item>

          <el-form-item label="箱型">
            <el-input v-model="formAdd.boxType" />
          </el-form-item>

          <el-form-item label="材质">
            <el-input v-model="formAdd.material" />
          </el-form-item>

          <el-form-item label="订单数量">
            <el-input v-model="formAdd.orderQuantity" />
          </el-form-item>

          <el-form-item label="送货数量">
            <el-input v-model="formAdd.deliveryQuantity" />
          </el-form-item>

          <el-form-item label="长">
            <el-input v-model="formAdd.length" />
          </el-form-item>

          <el-form-item label="宽">
            <el-input v-model="formAdd.width" />
          </el-form-item>

          <el-form-item label="高">
            <el-input v-model="formAdd.height" />
          </el-form-item>

          <el-form-item label="单价">
            <el-input v-model="formAdd.unitPrice" />
          </el-form-item>

          <el-form-item label="成品仓位">
            <el-input v-model="formAdd.EndProductPos" />
          </el-form-item>

          <el-form-item label="入仓时间" prop="warehousingTime">
            <el-date-picker
              v-model="formAdd.warehousingTime"
              align="right"
              type="date"
              placeholder="选择日期"
            />
          </el-form-item>

          <el-form-item label="备注:">
            <el-input v-model="formAdd.remark" />
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
export default {
  name: 'EndProductList',
  mixins: [initData],
  data() {
    return {
      form: {},
      formAdd: { },
      tableData: [],
      addTableData: [],
      customerFor: [],
      purAddVisible: false,
      purRules: {
        supplier: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        billingTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        deliveryTime: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      multipleSelection: [],
      serialNameFor: [],
      customerNoFor: [],
      specificationsFor: [],
      drawerFor: [],
      supplierFor: []

    }
  },
  methods: {
    // 生成送货单
    printing() {
      this.$router.push('/end_product_list_delivery_note')
    },
    // 整页打印
    wholePrinting() {
      if (this.form.carryTo === '已送货') {
        this.$router.push('/end_product_list_delivery_note')
      } else if (this.form.carryTo === '未送货') {
        this.$router.push('/end_product_list_not_delivery_note')
      } else {
        this.$router.push('/end_product_list_delivery_note')
      }
    },
    // 选择打印
    selectPrinting() {
      if (this.form.carryTo === '已送货') {
        this.$router.push('/end_product_list_delivery_note')
      } else if (this.form.carryTo === '未送货') {
        this.$router.push('/end_product_list_not_delivery_note')
      } else {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择打印的内容！！！')
          return
        } else {
          this.$router.push('/end_product_list_delivery_note')
        }
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.consignment) {
        return 'green-row'
      }
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    },
    // 删除
    drop() {
      if (this.dropRow.id == null) {
        this.$message.error('请选择一条进行操作')
      } else {
        this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {

        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
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
    border: 0;
  }
</style>
