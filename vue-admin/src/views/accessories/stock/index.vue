<template>
  <el-container>
    <el-main>
      <h1 align="center">辅料库存表</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="领料人:">
          <el-input v-model="form.search" clearable @clear="getList" />
        </el-form-item>
        <el-button type="primary" size="mini" @click="toQuery">查询</el-button>
        <!-- <el-button type="primary" size="mini" @click="purAdd">新增</el-button> -->
        <!-- <el-button type="warning" size="mini" @click="selectPrinting">选择打印</el-button>
        <el-button type="warning" size="mini" @click="wholePrinting">整页打印</el-button>
        <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button> -->
      </el-form>
      <div>
        <el-table
          ref="singleTable"
          :data="form"
          highlight-current-row
          style="width: 80%;margin-top:20px"
          border=""
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column v-show="true" prop="code" label="编码" />
          <el-table-column v-show="true" prop="specifications" label="品名规格" />
          <el-table-column v-show="true" prop="company" label="单位" />
          <el-table-column v-show="true" prop="number" label="数量" />
          <el-table-column v-show="true" prop="collector" label="领料人" />
          <el-table-column label="操作" width="213 ">
            <template slot-scope="scope">
              <!-- <el-link type="danger" size="small" @click="drop(scope.row.id)">删除</el-link> -->
              <el-link type="primary" size="small" @click="modifyPur(scope.row.id)">编辑</el-link>
              <el-link type="warning" size="small" @click="printing">生成打印单</el-link>
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
          <el-form-item label="编码" prop="code">
            <el-input v-model="formAdd.customerName" disabled>/>
            </el-input></el-form-item>
          <el-form-item label="品名规格" prop="specifications">
            <el-select v-model="formAdd.pricing">
              <el-option
                v-for="item in pricingFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="单位" prop="company">
            <el-select v-model="formAdd.company">
              <el-option
                v-for="item in pricingFor"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item
            label="数量"
            prop="number"
            :rules="[
              { required: true, message: '数量不能为空'},
              { type: 'number', message: '数量必须为数字值'}
            ]"
          >
            <el-input v-model.number="formAdd.number" />
          </el-form-item>

          <el-form-item label="领料人" prop="collector">
            <el-input v-model="formAdd.collector" />
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
  name: 'Stock',
  mixins: [initData],
  data() {
    return {
      form: [
        { code: 1,
          specifications: '传真纸',
          company: '卷',
          number: 24,
          collector: '张三',
          search: ''
        },
        { code: 2,
          specifications: '打印纸',
          company: '卷',
          number: 38,
          collector: '李四' }
      ],
      formAdd: {
        number: ''
      },
      tableData: [{
        documentsNo: '1',
        taskNumber: '1',
        customerName: '迪迦'
      }],
      addTableData: [],
      customerFor: [{
        id: '1',
        name: '迪迦'
      }],
      purAddVisible: false,
      purRules: {
        collector: [{ required: true, message: '该输入为必填项', trigger: 'blur' }]
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
      selectedTableData: [],
      currentRowTow: {},
      modifyTaskVisible: false,
      modifyTaskTable: [{
        customerName: '张三',
        taskNumber: '2'
      }],
      multipleSelection: [],
      indexId: {}

    }
  },
  methods: {
    toQuery() {
      console.log(11)
    },
    // 导出
    toExcel() {
      var list = this.form
      const th = ['编码', '品名规格', '单位', '数量', '领料人']
      const filterVal = ['code', 'specifications', 'company', 'number', 'collector']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '辅料库存表')
    },
    // 选择打印
    selectPrinting() {
      if (this.multipleSelection.length === 0) {
        this.$message.error('请选择打印的内容！！！')
        return
      } else {
        console.log(this.multipleSelection)
      }
    },
    // 打印
    printing() {
      this.$router.push('/accessories/printing')
    },
    handleCurrentChange(row) {
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
    delRule(val) {
      if (this.addTableData.length !== 1) {
        this.addTableData.splice(val.$index, 1)
        return
      }
      if (this.addTableData.length === 1) {
        var a = {
          id: null,
          less: 0,
          thanEqual: 0,
          discount: 0
        }
        this.addTableData = []
        this.addTableData.push(a)
      }
    },
    // 确定客户成品信息 回调
    modifyTaskConfirm() {
      this.$set(this.addTableData, this.indexId, this.currentRowTow)
      this.modifyTaskVisible = false
    },
    /**
     * 选中数据改变事件
     */
    modifyTaskChange(val) {
      this.currentRowTow = val
    },
    /**
     * 选中数据改变事件
     */
    selectionChange(val) {
      this.selectedTableData = val
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.consignment) {
        return 'green-row'
      }
    },
    /**
     * 记忆选中
     */
    getRowKeys(row) {
      return row.id
    },
    // 确定任务编号 回调
    selectedConfirm() {
      this.selectedTableData.forEach(x => {
        var status = true
        if (this.addTableData.length > 0) {
          this.addTableData.forEach(y => {
            if (x.taskNumber === y.taskNumber) {
              status = false
              this.$alert('任务编号【' + x.taskNumber + '】已存在，不能重复添加！', '提示', {
                confirmButtonText: '确定',
                callback: action => {}
              })
            }
          })
        }
        if (status) {
          this.addTableData.push(x)
        }
      })
      this.taskNumberVisible = false
    },

    // 添加任务编号
    addTaskNumber() {
      this.taskNumberVisible = true
    },
    // 点击任务编号弹出弹窗
    modifyTask(scope) {
      this.indexId = scope.$index
      this.modifyTaskVisible = true
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
  .el-table::before {
    left: 0;
    bottom: 0;
    width: 100%;
    height: 0px;
}
.fu{
  margin-left: 30%;
}
</style>
