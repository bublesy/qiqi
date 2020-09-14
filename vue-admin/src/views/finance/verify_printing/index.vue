<template>
  <div class="app-container">
    对账打印单细
  </div>
</template>

<script>
// 引入打印
import { export2Excel } from '@/utils/common'
export default window.$crudCommon({
  name: 'Verify_printing',
  data() {
    return {
      form: [
        {
          data: '2020-09-11',
          shipment: 202,
          goods: '五箱',
          type: 780 * 670 * 430,
          long: 90,
          number: 13.09,
          price: 7,
          money: 856
        }

      ],
      a: [],
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
      value: ''
    }
  },
  created: function() {
    var aData = new Date()
    console.log(aData) // Wed Aug 21 2019 10:00:58 GMT+0800 (中国标准时间)

    this.data =
      aData.getFullYear() + '-' + (aData.getMonth() + 1) + '-' + aData.getDate()
    console.log(this.data) // 2019-8-20
  },
  methods: {
    a() {
      console.log(11)
      this.$router.push('/finance/settlement')
    },
    search() {
      this.$router.push('/finance/receivables')
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`)
    },
    // 打印功能
    toExcel() {
      var list = this.form
      const th = ['出货日期门', '出货单号', '物品单号/款号', '箱型', '长x宽x高', '数量', '单价', '金额']
      const filterVal = ['data', 'shipment', 'goods', 'type', 'long', 'number', 'price', 'money']
      const data = list.map(v => filterVal.map(k => v[k]))
      export2Excel(th, data, this.value)
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
  name: 'finance/verify_printing', // 模块名字
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
.jie{
  width: 100%;
  height: 120px;
  border-bottom:1px solid #717171;
}
.jie>p{
  line-height: 10px;
}
.dy{
  float: left;
  margin: -50px 0 0 45%  ;
}
table{
  margin-top: 5px;
  width: 100%;
  color: black;

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
