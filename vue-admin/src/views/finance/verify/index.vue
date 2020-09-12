<template>
  <div class="app-container">
    <el-select v-model="value" placeholder="请选择月份">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-select v-model="value" placeholder="请选择客户">
      <el-option
        v-for="item in options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
    <el-button type="primary">确定</el-button>
    <el-button type="primary">打印</el-button>
    <el-button type="primary">导出</el-button>
    <div>
      <h2 style="text-align:center">海宁中奇纸箱包装厂</h2>
      <p style="text-align:center;margin-top:-10px">地址：海宁市长安镇东陈村&nbsp;&nbsp;&nbsp;&nbsp;电话：15325300000</p>
    </div>
    <div class="jie">
      <h2 style="text-align:center">2012年09月份月结对账单</h2>
      <p>客户：平湖吉安</p>
      <p>电话：87578878</p>
      <p>传真：</p>
      <p class="dy">打印日期：2020-09-11</p>
    </div>
    <el-pagination
      style="float:right;margin-top:-60px"
      small
      layout="prev, pager, next"
      :total="50"
    />
    <table>
      <tr>
        <th>出货日期</th>
        <th>出货单号</th>
        <th>物品单号/款号</th>
        <th>箱型</th>
        <th>长x宽x高</th>
        <th>数量</th>
        <th>单价</th>
        <th>金额</th>
      </tr>
      <tr>
        <td>2020-09-11</td>
        <td>202</td>
        <td>五箱</td>
        <td>780x670x430</td>
        <td>90</td>
        <td>13.08</td>
        <td>6</td>
        <td>550.6</td>
      </tr>
    </table>
  </div>
</template>

<script>

export default window.$crudCommon({
  name: 'Verify',
  data() {
    return {
      a: [],
      options: [{
        value: '选项1',
        label: '2020-02-02'
      }, {
        value: '选项2',
        label: '双皮奶'
      }, {
        value: '选项3',
        label: '蚵仔煎'
      }, {
        value: '选项4',
        label: '龙须面'
      }, {
        value: '选项5',
        label: '北京烤鸭'
      }],
      value: ''
    }
  },
  created() {
  },
  methods: {
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
  name: 'finance/verify', // 模块名字
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
