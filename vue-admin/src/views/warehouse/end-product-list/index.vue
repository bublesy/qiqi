<template>
  <el-container>
    <el-main>
      <h1 align="center">成品仓库</h1>
      <el-form :inline="true" :model="form" size="mini" align="center">
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
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
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
          border
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="warehouseNo" label="入仓单号" width="140" />
          <el-table-column v-show="true" prop="no" label="任务编号" width="140" />
          <el-table-column v-show="true" prop="name" label="客户名称" width="140" />
          <el-table-column v-show="true" prop="orderNum" label="订单数量" width="140" />
          <el-table-column v-show="true" prop="deliveryQuantity" label="本次送货数量" width="140" />
          <el-table-column v-show="true" prop="alreadyDeliveryQuantity" label="已送数量" width="140" />
          <el-table-column v-show="true" prop="stayDeliveryQuantity" label="待送数量" width="140" />
          <el-table-column v-show="true" prop="carryTo" label="发货状态" width="140" />
          <el-table-column v-show="true" prop="posting" label="过账状态" width="140" />
          <el-table-column v-show="true" prop="boxType" label="箱型" width="140" />
          <el-table-column v-show="true" prop="material" label="材质" width="140" />
          <el-table-column v-show="true" prop="length" label="长" width="140" />
          <el-table-column v-show="true" prop="width" label="宽" width="140" />
          <el-table-column v-show="true" prop="height" label="高" width="140" />
          <el-table-column v-show="true" prop="perPrice" label="单价" width="140" />
          <el-table-column v-show="true" prop="endProductPos" label="成品仓位" width="140" />
          <el-table-column v-show="true" prop="productNum" label="已产数量" width="140" />
          <el-table-column v-show="true" prop="createdTime" label="入仓时间" width="160" />
          <el-table-column v-show="true" prop="outNo" label="送货单号" width="160" />
          <el-table-column v-show="true" prop="outDate" label="送货日期" width="160" />
          <el-table-column label="操作" width="500px">
            <template slot-scope="scope">
              <el-button type="primary" size="small" :disabled="parseInt(scope.row.alreadyDeliveryQuantity)===parseInt(scope.row.orderNum) ?true : false" @click="purAdd(scope)">生成送货单</el-button>
              <el-button type="primary" size="small" @click="getDeliveryNote(scope)">查看送货单</el-button>
              <el-button type="warning" size="small" :disabled="scope.row.alreadyDeliveryQuantity===parseInt(scope.row.orderQuantity) ?true : false" @click="printing(scope)">打印送货单</el-button>
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
      <!-- 新增/编辑送货数量 -->
      <el-dialog :title="titleType+'送货数量'" :visible.sync="purAddVisible">
        <el-form ref="purForm" :rules="purRules" :inline="true" :model="formAdd" size="mini" label-width="100px">

          <el-form-item label="入仓单号:" prop="warehouseNo">
            <el-input v-model="formAdd.warehouseNo" disabled />
          </el-form-item>
          <el-form-item label="订单数量:" prop="orderQuantity">
            <el-input v-model="formAdd.orderQuantity" disabled />
          </el-form-item>
          <el-form-item label="成品仓位:" prop="endProductPos">
            <el-input v-model="formAdd.endProductPos" disabled />
          </el-form-item>
          <el-form-item label="送货数量:" prop="deliveryQuantity">
            <el-input v-model="formAdd.deliveryQuantity" @change="deliveryChange" />
          </el-form-item>

        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="purAddNo">取 消</el-button>
          <el-button size="small" type="primary" @click="purAddOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 新增/编辑送货单 -->
      <el-dialog title="送货单" :visible.sync="deliveryNoteVisible">
        <el-table
          ref="singleTable"
          :data="deliveryNoteData"
          highlight-current-row
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column v-show="true" prop="outNo" label="送货单号" />
          <el-table-column v-show="true" prop="sendNum" label="送货数量" />
          <el-table-column v-show="true" prop="createdTime" label="送货日期" />
        </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button size="small" @click="noteNo">取 消</el-button>
          <el-button size="small" type="primary" @click="noteOk('purForm')">确 定</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { list } from '@/api/end-product/product'
import { add } from '@/api/end-product/product'
import { getById } from '@/api/end-product/product'
import { getDeliveryList } from '@/api/end-product/product'

export default {
  name: 'EndProductList',
  mixins: [initData],
  data() {
    return {
      formAdd: { },
      tableData: [],
      customerFor: [],
      purAddVisible: false,
      purRules: {
        deliveryQuantity: [{ required: true, message: '该输入为必填项', trigger: 'change' }],
        checkNum: [{ required: true, message: '该输入为必填项', trigger: 'change' }]
      },
      titleType: '',
      multipleSelection: [],
      form: {
        carryTo: '',
        time: ''
      },
      formAddCheck: {},
      deliveryNoteData: [],
      deliveryNoteVisible: false

    }
  },
  created() {
    this.init()
  },
  methods: {
    noteNo() { this.deliveryNoteVisible = false },
    noteOk() { this.deliveryNoteVisible = false },
    // 查看送货单
    getDeliveryNote(scope) {
      this.deliveryNoteVisible = true
      getDeliveryList(scope.row.id).then(res => {
        this.deliveryNoteData = res
      })
    },
    deliveryChange() {
      var a = parseInt(this.formAdd.deliveryQuantity)
      var b = parseInt(this.formAdd.endProductPos)
      if (a > b) {
        this.$message.error('送货数量不能大于仓位数量！！')
        if (b === 0) {
          this.formAdd.deliveryQuantity = ''
        } else {
          this.formAdd.deliveryQuantity = b
        }
        return
      }
    },
    purAddNo() {
      this.purAddVisible = false
    },
    modifyPur(scope) {
      this.purAddVisible = true
      this.titleType = '编辑'
      getById(scope.row.id).then(res => {
        this.formAdd = res
      })
    },
    purAddOk(purForm) {
      this.$refs[purForm].validate((valid) => {
        if (valid) {
          console.log(this.formAdd)
          add(this.formAdd).then(res => {
            if (res) {
              this.$message.success(this.titleType + '成功')
              location.reload()
            } else {
              this.$message.error(this.titleType + '失败')
            }
          })
          this.purAddVisible = false
        } else {
          return false
        }
      })
    },
    purAdd(scope) {
      this.purAddVisible = true
      this.titleType = '新增'
      // 新增初始化数据
      this.formAdd = {}
      this.$set(this.formAdd, 'id', scope.row.id)
      this.$set(this.formAdd, 'warehouseNo', scope.row.warehouseNo)
      this.$set(this.formAdd, 'deliveryQuantity', scope.row.orderQuantity)
      this.$set(this.formAdd, 'orderQuantity', scope.row.orderNum)
      this.$set(this.formAdd, 'endProductPos', scope.row.endProductPos)
      this.$set(this.formAdd, 'id', scope.row.eid)
      this.$set(this.formAdd, 'orderId', scope.row.id)
    },
    loadData() {
      this.queryParams.carryTo = this.form.carryTo
      this.queryParams.time = this.form.time
      if (this.queryParams.time === null) {
        this.$set(this.queryParams, 'time', '')
      }
      list(this.queryParams).then(res => {
        console.log(res)
        this.tableData = res.list
        this.pagination.total = res.total
        this.tableData.forEach(a => {
          a.stayDeliveryQuantity = a.orderNum - a.alreadyDeliveryQuantity
        })
      })
    },
    // 打印
    printing(scope) {
      if (this.form.carryTo === '已送货') {
        this.multipleSelection.push(scope.row)
        let flag = true
        this.multipleSelection.forEach(a => {
          if (a.deliveryQuantity === null) {
            this.$message.error('请先填写送货数量！！')
            flag = false
          }
        })
        if (flag) {
          this.$router.push({
            path: '/end_product_list_delivery_note',
            query: { 'data': this.multipleSelection }
          })
        }
      } else if (this.form.carryTo === '未送货') {
        this.multipleSelection.push(scope.row)
        let flag = true
        this.multipleSelection.forEach(a => {
          if (a.deliveryQuantity === null) {
            this.$message.error('请先填写送货数量！！')
            flag = false
          }
        })
        if (flag) {
          this.$router.push({
            path: '/end_product_list_not_delivery_note',
            query: { 'data': this.multipleSelection }
          })
        }
      } else {
        this.multipleSelection.push(scope.row)
        let flag = true
        this.multipleSelection.forEach(a => {
          if (a.deliveryQuantity === null) {
            this.$message.error('请先填写送货数量！！')
            flag = false
          }
        })
        if (flag) {
          this.$router.push({
            path: '/end_product_list_delivery_note',
            query: { 'data': this.multipleSelection }
          })
        }
      }
    },
    // 整页打印
    wholePrinting() {
      if (this.form.carryTo === '已送货') {
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.tableData }
        })
      } else if (this.form.carryTo === '未送货') {
        this.$router.push({
          path: '/end_product_list_not_delivery_note',
          query: { 'data': this.tableData }
        })
      } else {
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.tableData }
        })
      }
    },
    // 选择打印
    selectPrinting() {
      if (this.form.carryTo === '已送货') {
        this.$router.push({
          path: '/end_product_list_delivery_note',
          query: { 'data': this.multipleSelection }
        })
      } else if (this.form.carryTo === '未送货') {
        this.$router.push({
          path: '/end_product_list_not_delivery_note',
          query: { 'data': this.multipleSelection }
        })
      } else {
        if (this.multipleSelection.length === 0) {
          this.$message.error('请选择打印的内容！！！')
          return
        } else {
          this.$router.push({
            path: '/end_product_list_delivery_note',
            query: { 'data': this.multipleSelection }
          })
        }
      }
    },
    handleSelectionChange(row) {
      this.multipleSelection = row
    }

  }
}

</script>
<style scoped lang="scss">
  >>>.el-dialog__header {
    border: 0;
  }
</style>
