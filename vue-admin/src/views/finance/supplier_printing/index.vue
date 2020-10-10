<template>
  <div class="app-container">
    <div class="xz" />
    <el-button @click="toBack">返回</el-button>
    <el-button v-print="'#printTest'" type="primary">打印</el-button>
    <div id="printTest" class="biaoge">
      <h2 style="text-align:center">供方月结对账单</h2>
      <p>供方:{{ name }}</p>
      <p style="text-align:center;margin-top:-40px">打印日期:{{ value }}</p>
      <el-table
        :data="tableData"
        border
        style="width: 400,margin-top:20px"
        :summary-method="getSummaries"
        show-summary
      >
        <el-table-column
          prop="taskNumber"
          label="任务编号"
        />
        <el-table-column
          prop="inProductDate"
          label="入仓日期"
        />
        <el-table-column
          prop="documentsNo"
          label="采购序号"
          width="160"
        />
        <el-table-column
          prop="material"
          label="材质"
        />
        <el-table-column
          prop="paperLength"
          label="纸长"
        />
        <el-table-column
          prop="paperWidth"
          label="纸宽"
        />
        <el-table-column
          prop="purchaseQuantity"
          label="购入数量"
        />
        <el-table-column
          prop="returnNum"
          label="退货数量"
        />
        <el-table-column
          prop="returnAmount"
          label="退货金额"
        />
        <el-table-column
          prop="costPrice"
          label="成本价"
        />
        <el-table-column
          prop="totalAmount"
          label="总金额"
        />
        <el-table-column
          prop="creditDate"
          label="账款年月"
          width="160"
        />
        <el-table-column
          prop="alreadyMoney"
          label="已付"
        />
        <el-table-column
          prop="unPayed"
          label="欠款"
        />
        <el-table-column v-show="true" prop="settlementDate" label="结算日期" width="150">
          <template slot-scope="scope">
            <div v-for="(item,key) in scope.row.array" :key="key">
              {{ item }}
            </div>
          </template>
        </el-table-column>
        <!-- <el-table-column
          prop="carryTo"
          label="是否过账"
        /> -->
        <el-table-column
          prop="settlementStatus"
          label="结算状态"
        />
      </el-table>
    </div>
  </div>
</template>

<script>

export default window.$crudCommon({
  name: 'Supplier_printing',
  data() {
    return {
      a: [],
      // 表单数据
      tableData: [{
        number: 12,
        data: '2020-09-09',
        serial: 480,
        texture: 'A646A',
        long: 2323,
        wide: 889,
        buy: 0,
        return: 0.00,
        amount: 6.5,
        univalence: 550,
        credit: '2020-09-10'
      }],
      name: ''
    }
  },
  created: function() {
    this.tableData = this.$route.params.data
    this.name = this.$route.params.data[0].customerName
    console.log(this.tableData)
    var aData = new Date()
    console.log(aData) // Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)
    this.value =
      aData.getFullYear() + '-' + (aData.getMonth() + 1) + '-' + aData.getDate()
    console.log(this.value) // 2019-8-20
  },
  methods: {
    getSummaries(param) {
      const { columns, data } = param
      const sums = []
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = '总价'
          return
        }
        if (index === 1 || index === 2 || index === 3 || index === 4 || index === 5 || index === 9 || index === 11 || index === 14 || index === 15 || index === 16) {
          sums[index] = ''
          return
        }
        const values = data.map(item => Number(item[column.property]))
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr)
            if (!isNaN(value)) {
              return prev + curr
            } else {
              return prev
            }
          }, 0)
          sums[index] += ' '
        } else {
          sums[index] = 'N/A'
        }
      })

      return sums
    },
    // 返回
    toBack() {
      this.$router.push('/finance/supplier')
    },
    onLoadTable({ page, value, data }, callback) {
      // 首次加载去查询对应的值
      if (value) {
        this.$message.success('首次查询' + value)
        callback({
          id: '0',
          name: '张三',
          sex: '男'
        })
        return
      }
      if (data) {
        this.$message.success('搜索查询参数' + JSON.stringify(data))
      }
      if (page) {
        this.$message.success('分页参数' + JSON.stringify(page))
      }
      // 分页查询信息
      callback({
        total: 2,
        data: [{
          id: '0',
          name: '张三',
          sex: '男'
        }, {
          id: '1',
          name: '李四',
          sex: '女'
        }]
      })
    },
    setVal() {
      this.a = [{
        label: '选项1',
        value: 0
      }, {
        label: '选项2',
        value: 1
      }]
    },

    beforeOpen(done, type) {
      this.setVal()
      done()
    },

    // 列表前操作方法
    listBefore() {
    },

    // 列表后操作方法
    listAfter() {
    },

    // 新增前操作方法
    addBefore() {
      // this.form.createUser = 'small'
    },
    // 新增后操作方法
    addAfter() {
    },

    // 修改前操作方法
    updateBefore() {
      // this.form.updateUser = 'small'
    },

    // 修改后操作方法
    updateAfter() {},

    // 删除前操作方法
    delBefore() {},

    // 删除后操作方法
    delAfter() {}
  }
}, {
  name: 'finance/supplier_printing', // 模块名字
  list: 'getRoles', // 列表接口名字
  update: 'editRole', // 更新接口名字
  add: 'addRole', // 新增接口名字
  del: 'removeRole', // 删除接口名字
  rowKey: 'id', // 主键
  pageNumber: 'pageNumber', // 页码
  pageSize: 'pageSize', // 页数
  total: 'total', // 总页数
  data: 'list'// 列表属性
})
</script>
<style lang="scss" scoped>
.biaoge{
  width: 100%;
  height: 80px;
  border-bottom:1px solid #717171;
}
table{
  width: 100%;
  color: black;
  margin-top: 30px
}
tr{
  width: 9%;
  line-height: 25px;
  text-align: center;
}
td{
  width: 9%;
  text-align: center;
  height: 30px;
}
</style>
