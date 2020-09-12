<template>
  <div style="padding:10px">
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="客户单号:">
        <el-input v-model="form.no" />
      </el-form-item>
      <el-form-item label="款号:">
        <el-input v-model="form.modelNo" />
      </el-form-item>
      <!-- xlk -->
      <el-form-item label="箱型:">
        <!-- <el-input v-model="form.boxType" /> -->
        <el-select v-model="boxType" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <!-- xlk -->
      <el-form-item label="供方:">
        <!-- <el-input v-model="form.supplier" /> -->
        <el-select v-model="supplier" placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <el-button type="primary" size="mini">查询</el-button>
    <el-button type="primary" size="mini" @click="add">新增</el-button>
    <el-card>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
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
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-link @click="updated(scope.row.id)">编辑</el-link>
            <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
              <el-button slot="reference" type="danger" size="mini">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :current-page="queryForm.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="queryForm.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
    <editDialog :dialog="editDialog" />
  </div>
</template>

<script>
import editDialog from '@/views/order/edit/edit'
export default {
  name: 'Edit',
  components: { editDialog },
  data() {
    return {
      total: 0,
      queryForm: {
        page: 1,
        count: 10,
        no: '',
        modelNo: '',
        boxType: '',
        supplier: ''
      },
      form: {},
      editDialog: {
        show: false
      },
      tableData: [],
      value: '',
      options: []
    }
  },
  created() {
    var data = {
      no: '22'
    }
    this.tableData.push(data)
  },
  methods: {
    initTable() {},
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.queryForm.size = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.queryForm.page = page
      this.initTable()
    },
    deleted(id) {
      def
    },
    add() {},
    updated(id) {
      this.editDialog.show = true
    }
  }

}
</script>

<style scoped>
.el-link{
  color: rgb(0, 162, 255);
  font: 1em sans-serif;
}

</style>
