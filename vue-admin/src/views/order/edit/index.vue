<template>
  <div id="print" style="padding:10px">
    <p class="font">客户订单</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="客户单号:">
        <el-input v-model="form.no" />
      </el-form-item>
      <el-form-item label="款号:">
        <el-input v-model="form.modelNo" />
      </el-form-item>
      <!-- xlk -->
      <el-form-item label="箱型:">
        <el-select v-model="form.boxType" placeholder="请选择">
          <el-option
            v-for="item in boxTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <!-- xlk -->
      <el-form-item label="供方:">
        <el-select v-model="form.supplier" placeholder="请选择">
          <el-option
            v-for="item in supplierOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
<<<<<<< HEAD
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <el-button size="mini" type="primary" @click="add">新增</el-button>
    <el-button v-print="'#print'" size="mini" type="warning">打印</el-button>
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
=======
    <el-button type="primary" size="mini">查询</el-button>
    <el-button type="primary" size="mini" @click="add">新增</el-button>
>>>>>>> master
    <el-card>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
      >
        <el-table-column type="index" width="55" />
        <el-table-column prop="no" label="客户单号" width="120" />
        <el-table-column prop="modelNo" label="款号" width="120" />
        <el-table-column prop="boxType" label="箱型" width="120" />
        <el-table-column prop="unit" label="单位" width="120" />
        <el-table-column prop="material" label="材质" width="120" />
        <el-table-column prop="orderNum" label="订单数量" width="120" />
        <el-table-column prop="perPrice" label="单价" width="120" />
        <el-table-column prop="money" label="金额" width="120" />
        <el-table-column prop="supplier" label="供方" width="120" />
        <el-table-column prop="createdTime" label="制单时间" width="120" />
        <el-table-column prop="making" label="制单人" width="120" />
        <el-table-column prop="auditTime" label="审核时间" width="120" />
        <el-table-column prop="audit" label="审核人" width="120" />
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
<<<<<<< HEAD
            <el-button type="warning" size="mini" @click="updated(scope.row.id)">编辑</el-button>
=======
            <el-link @click="updated(scope.row.id)">编辑</el-link>
>>>>>>> master
            <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
              <el-button slot="reference" type="danger" size="mini">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="form.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="form.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
    <editDialog :id="id" :dialog="editDialog" />
  </div>
</template>

<script>
import editDialog from '@/views/order/edit/edit'
import { export2Excel } from '@/utils/common'
export default {
  name: 'Edit',
  components: { editDialog },
  data() {
    return {
      id: '测试施工单id',
      total: 0,
      form: {
        page: 1,
        count: 10
        // no: '',
        // modelNo: '',
        // boxType: '',
        // supplier: ''
      },
      editDialog: {
        show: false
      },
      tableData: [],
      value: '',
      options: [],
      boxTypeOptions: [],
      supplierOptions: []

    }
  },
  created() {
    this.initTable()
    var data = {
      no: '22'
    }
    this.tableData.push(data)
  },
  methods: {
    query() {
      this.initTable()
    },
    initTable() {},
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.form.size = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.form.page = page
      this.initTable()
    },
    deleted(id) {
<<<<<<< HEAD

    },
    add() {
      this.editDialog.show = true
    },
=======
      def
    },
    add() {},
>>>>>>> master
    updated(id) {
      this.id = id
      this.editDialog.show = true
    },
    toExcel() {
      var list = this.tableData
      const th = ['部门名称', '最后操作时间']
      const filterVal = ['name', 'lastmodifytime']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '部门管理')
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
