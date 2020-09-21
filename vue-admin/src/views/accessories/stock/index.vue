<template>
  <el-container>
    <el-main>
      <h1 align="center">辅料库存表</h1>
      <el-form :inline="true" :model="form" size="mini">
        <el-form-item label="时间:">
          <el-date-picker
            v-model="form.date"
            type="date"
            placeholder="选择日期"
            value-format="yyyy-MM-dd"
          />
        </el-form-item>
        <el-button type="primary" size="mini" @click="loadData()">查询</el-button>
        <el-button type="primary" size="mini" @click="toExcel">导出 </el-button>
      </el-form>
      <div>
        <div id="printTest">
          <el-table
            ref="singleTable"
            :data="tableData"
            highlight-current-row
            style="width: 70%;margin-top:20px"
            border=""
          >
            <!-- 复选框是否开启 -->
            <!-- <el-table-column v-show="true" prop="pickingPeople" label="领料人" /> -->
            <el-table-column v-show="true" type="index" width="150" label="编码" />
            <el-table-column v-show="true" prop="specifications" label="品名规格" />
            <el-table-column v-show="true" prop="company" label="单位" />
            <el-table-column v-show="true" prop="number" label="数量" />
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-link type="warning" size="small" @click="printing(scope)">生成打印单</el-link>
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
      </div>
      <!-- 新增/编辑对账明细单 -->
      <!-- <el-dialog :title="titleType+'对账明细表'" :visible.sync="purAddVisible" :close-on-click-modal="false">
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
      </el-dialog> -->
    </el-main>
  </el-container>

</template>

<script>
import initData from '@/mixins/initData'
import { list } from '@/api/accessories/materialinventory'
import { getById, getUser } from '@/api/accessories/means'
import { export2Excel } from '@/utils/common'

// import { export2Excel } from '@/utils/common'

export default {
  name: 'Stock',
  mixins: [initData],
  data() {
    return {
      form: { time: '' },
      formAdd: {
        number: ''
      },
      // 表格数据
      tableData: [],
      pickingPeople: []
    }
  },
  created() {
    this.init()
    getUser().then(res => {
      console.log(res)
      this.pickingPeople = res.nickname
    })
  },
  methods: {
    loadData() {
      this.queryParams.time = this.form.time
      list(this.queryParams).then(res => {
        console.log(res)
        this.tableData = res.list
        this.tableData.forEach(a => {
          getById(a.specificationId).then(res => {
            this.$set(a, 'specifications', res.specification)
          })
        })
        this.pagination.total = res.total
      })
    },
    printing() {
      this.$router.push('/accessories/printing')
    },
    toExcel() {
      var list = this.tableData
      const th = ['编码', '品名规格', '单位', '数量']
      const filterVal = ['index', 'specifications', 'company', 'number']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, '辅料领料')
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
