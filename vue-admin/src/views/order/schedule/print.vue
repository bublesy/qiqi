<template>
  <div style="padding:30px">
    <el-button v-print="'#printTest'" type="success">打印</el-button>
    <el-button type="info" @click="back">返回</el-button>
    <el-card id="printTest">
      <h1 style="margin-left:41%">{{ name2 }}</h1>
      <p class="font">生产排期表</p>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="date" label="排期日期" width="120" />
        <el-table-column prop="productDay" label="生产天数" width="120" />
        <el-table-column prop="no" label="任务编号" width="120" />
        <el-table-column prop="name" label="客户简介" width="120" />
        <el-table-column prop="boxType" label="箱型" width="120" />
        <el-table-column prop="material" label="材质" width="120" />
        <el-table-column prop="stare" label="楞型" width="120" />
        <el-table-column prop="cartonSize" label="纸箱尺寸" width="150">
          <template slot-scope="scope">
            {{ scope.row.length+' X '+scope.row.width+' X '+scope.row.height }}
          </template>
        </el-table-column>
        <el-table-column prop="paperLength" label="纸长" width="120" />
        <el-table-column prop="paperWidth" label="纸宽" width="120" />
        <el-table-column prop="pressureSpecification" label="分压规格" width="120" />
        <el-table-column prop="unit" label="单位" width="120" />
        <el-table-column prop="orderNum" label="订单数量" width="120" />
        <el-table-column prop="productNum" label="成品数量" width="120" />
        <el-table-column prop="finished" label="已产数量" width="120" />
        <el-table-column prop="deliveryDate" label="交货日期" width="180" />
        <el-table-column prop="isSchedule" label="是否排期" width="120" />
      <!-- <template slot-scope="scope">
          <span v-if="scope.row.isSchedule === true">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column> -->
      </el-table>
    </el-card>
    <!-- <el-pagination
      :current-page="form.page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="form.count"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    /> -->
  </div>
</template>

<script>
// import { export2Excel } from '@/utils/common'
import { list } from '@/api/basedata/firm'
export default {
  data() {
    return {
      tableData: [],
      total: 0,
      form: {
        count: 10,
        page: 1
      },

      id: '',
      name2: ''
    }
  },
  created() {
    list().then(res => {
      console.log(res)
      var firmList = []
      firmList = res.list
      firmList.forEach(a => {
        if (a.isFirm === true) {
          this.name2 = a.name
          console.log('name:' + this.name2)
        }
      })
    })
    var list2 = []
    var object = this.$route.query
    console.log(object)
    for (const key in object) {
      if (object.hasOwnProperty(key)) {
        const element = object[key]
        list2.push(element)
      }
    }
    this.tableData = list2
  },
  methods: {
    back() {
      this.$router.push('/schedule')
    }

  }
}
</script>

<style lang="scss" scoped>
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
</style>>

