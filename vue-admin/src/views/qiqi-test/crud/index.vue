<template>
  <div class="app-container">
    <h2 style="text-align:center">海宁市中奇纸箱包装厂</h2>
    <h2 style="text-align:center">生产&nbsp;&nbsp;制作单</h2>
    <div class="dh">
      <p>订货单：<span style="  font-weight: bolder;">百味林</span></p>
      <div>
        <p>任务编号：<span style="  font-weight: bolder;font-size: 20px;">12090032</span></p>
        <p style="margin-top:-10px">交货日期：2020-09-12</p>
      </div>
    </div>
    <table>
      <tr>
        <td>客户单号</td>
        <td class="center">客户订单号</td>
        <td>产品名称</td>
        <td class="center">五层箱</td>
        <td>纸箱尺寸</td>
        <td>600x3000x400</td>
        <td class="center">订单数量</td>
        <td class="center">560</td>
        <td class="center">纸箱面积</td>
        <td class="center">0.6674</td>
      </tr>
      <tr>
        <td>款号/型号</td>
        <td class="center">客户的箱号或者款号</td>
        <td>愣&nbsp;&nbsp;&nbsp;&nbsp;型</td>
        <td class="center">AB</td>
        <td>纸箱尺寸</td>
        <td class="center">600x3000</td>
        <td class="center">颜色</td>
        <td class="center">黑色</td>
        <td>总面积：</td>
        <td class="center">373.744</td>
      </tr>
      <tr>
        <td>材质</td>
        <td class="center">美卡+120+110+120</td>
        <td>生产类型</td>
        <td class="center" colspan="3">印刷&nbsp;&nbsp;开槽</td>
        <td>结合</td>
        <td class="center">粘胶</td>
        <td>是否常规</td>
        <td />
      </tr>
      <tr>
        <td class="center">备注</td>
        <td colspan="10">生产时注意的内容写在这里！显示生产单上！</td>
      </tr>
    </table>
    <div class="img">
      <img src="./1.jpg" alt="">
    </div>
  </div>
</template>

<script>

export default window.$crudCommon({
  data() {
    return {
      a: []
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
  name: 'qiqi-test/crud', // 模块名字
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
<style scoped>
.dh{
  display: flex;
  justify-content: space-between;
  padding: 0 10px;
  width: 1200px;
  margin: 0 auto;
}
table,tr,td{
  border:1px solid black;
}
table{
width: 1200px;
  border-collapse:collapse;
  height: auto;
  margin: 0 auto;
}
tr{
  height: 35px;
}
table td{
  /* width: 40px ; */
   padding:15px;
  height: 35px;

  /* text-align: center; */
}
.center{
  text-align: center;

}
.img{
   width: 1100px;
   height: 300px;
  margin: 20px auto;
  background: pink;
  /* overflow: hidden; */
}
.img img{
  width: 100%;
  height:100%;
}
</style>
