<template>
  <div style="margin:30px">
    <p class="font">人事档案管理</p>
    <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
      <el-form-item label="工号:">
        <el-input v-model="form.no" />
      </el-form-item>
      <el-form-item label="姓名:">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="性别:">
        <el-radio v-model="form.sex" :label="true">男</el-radio>
        <el-radio v-model="form.sex" :label="false">女</el-radio>
      </el-form-item>
      <el-form-item label="是否在职:">
        <el-radio v-model="form.onJob" :label="true">在职</el-radio>
        <el-radio v-model="form.onJob" :label="false">离职</el-radio>
      </el-form-item><br>
      <el-form-item label="入职日期:">
        <el-date-picker
          v-model="form.entryDate"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="部门:">
        <el-input v-model="form.dept" />
      </el-form-item>
      <el-form-item label="职位:">
        <el-input v-model="form.position" />
      </el-form-item>
    </el-form>
    <el-button size="mini" type="primary" @click="query">查询</el-button>
    <el-button size="mini" type="primary" @click="add">新增</el-button>
    <el-button type="success" size="mini" @click="toExcel">Excel导出</el-button>
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
      <el-table-column prop="no" label="工号" width="120" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="sex" label="性别" width="120" />
      <el-table-column prop="onJob" label="是否在职" width="120" />
      <el-table-column prop="identity" label="身份证号" width="120" />
      <el-table-column prop="nation" label="民族" width="120" />
      <el-table-column prop="birth" label="出生日期" width="120" />
      <el-table-column prop="entryDate" label="入职日期" width="120" />
      <el-table-column prop="education" label="学历" width="120" />
      <el-table-column prop="bloodType" label="血型" width="120" />
      <el-table-column prop="height" label="身高" width="120" />
      <el-table-column prop="dept" label="部门" width="120" />
      <el-table-column prop="position" label="职位" width="120" />
      <el-table-column prop="weight" label="体重" width="120" />
      <el-table-column prop="political" label="政治面貌" width="120" />
      <el-table-column prop="phone" label="联系电话" width="120" />
      <el-table-column prop="address" label="户口地址" width="120" />
      <el-table-column prop="dailyRate" label="日工资" width="120" />
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="warning" size="mini" @click="updated(scope.row.id)">编辑</el-button>
          <el-popconfirm title="内容确定删除吗？" @onConfirm="deleted(scope.row.id)">
            <el-button slot="reference" type="danger" size="mini">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
    <addDialog :id="id" :dialog="addDialog" />
  </div>
</template>

<script>
import { getPersonnel, delPersonnel } from '@/api/staff/personnel'
import { export2Excel } from '@/utils/common'
import addDialog from '@/views/staff/personnel/add'
export default {
  name: 'Personnel',
  components: { addDialog },
  data() {
    return {
      addDialog: {
        show: false
      },
      tableData: [],
      total: 0,
      queryForm: {
        page: 1,
        size: 10
      },
      form: {
        sex: true,
        onJob: true
      }
    }
  },
  created() {
    this.initTable()
    this.tableData.push({ no: '12345', name: 'hc' })
  },
  methods: {
    initTable() {
      getPersonnel(this.queryForm).then(res => {
        if (res) {
          this.tableData = res.list
          this.total = res.total
        }
      })
    },
    add() {
      this.addDialog.show = true
    },
    updated(id) {
      this.id = id
      this.addDialog.show = true
    },
    deleted(id) {
      delPersonnel(id).then(res => {
        if (res) {
          this.$message.success('删除成功')
        } else {
          this.$message.success('删除失败')
        }
      })
    },
    handleSelectionChange() {},
    handleSizeChange(size) {
      this.size = size
      this.initTable()
    },
    handleCurrentChange(page) {
      this.page = page
      this.initTable()
    },
    toExcel() {
      var list = this.tableData
      const th = ['工号', '姓名', '性别', '是否在职', '身份证号', '民族', '出生日期', '入职日期', '学历', '血型', '身高', '部门', '职位', '体重', '政治面貌', '联系电话', '户口地址', '日工资']
      const filterVal = ['no', 'name', 'sex', 'onJob', 'identity', 'nation', 'birth', 'entryDate', 'education', 'bloodType', 'height', 'dept', 'position', 'weight', 'political', 'phone', 'address', 'dailyRate']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '人事档案管理')
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
