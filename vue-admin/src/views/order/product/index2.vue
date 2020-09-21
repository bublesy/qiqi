<template>
  <div id="printTest" class="app-container">
    <el-button type="info" style="margin-left:84%" @click="back">返回</el-button>
    <el-button v-print="'#printTest'" type="success">打印</el-button>
    <h2 style="text-align:center">海宁市中奇纸箱包装厂</h2>
    <h2 style="text-align:center">生产&nbsp;&nbsp;制作单</h2>
    <div class="dh">
      <p>订货单：<span style="  font-weight: bolder;">{{ tableData.name }}</span></p>
      <div>
        <p>任务编号：<span style="  font-weight: bolder;font-size: 20px;">{{ tableData.no }}</span></p>
        <p style="margin-top:-10px">交货日期：{{ tableData.deliveryDate }}</p>
      </div>
    </div>
    <table>
      <tr>
        <td>客户单号</td>
        <td class="center">{{ tableData.no }}</td>
        <td>产品名称</td>
        <td class="center">{{ tableData.boxType }}   </td>
        <td>纸箱尺寸</td>
        <td>{{ tableData.length }}*{{ +tableData.width }}*{{ tableData.length }}</td>
        <td class="center">订单数量</td>
        <td class="center">{{ tableData.orderNum }}</td>
        <td class="center">纸箱面积</td>
        <td class="center">{{ tableData.paperArea }}</td>
      </tr>
      <tr>
        <td>款号/型号</td>
        <td class="center">{{ tableData.modelNo }}</td>
        <td>愣&nbsp;&nbsp;&nbsp;&nbsp;型</td>
        <td class="center">{{ tableData.stare }} </td>
        <td>纸片尺寸</td>
        <td class="center">{{ tableData.paperLength }}*{{ +tableData.paperWidth }}</td>
        <td class="center">颜色</td>
        <td class="center">{{ tableData.color }}</td>
        <td>总面积:</td>
        <td class="center">{{ tableData.totalArea }}</td>
      </tr>
      <tr>
        <td>材质</td>
        <td class="center">{{ tableData.material }}</td>
        <td>生产流程</td>
        <td class="center" colspan="3"><span v-if="tableData.partialPressure">分压</span>&nbsp;&nbsp;
          <span v-if="tableData.pasting">裱糊</span>&nbsp;&nbsp;
          <span v-if="tableData.printing">印刷</span>&nbsp;&nbsp;
          <span v-if="tableData.grooving">开槽</span>&nbsp;&nbsp;
          <span v-if="tableData.dieCutting">模切</span>&nbsp;&nbsp;
        </td>
        <td>结合</td>
        <td class="center">{{ tableData.combine }}</td>
        <td>是否常规</td>
        <td>
          <input type="checkbox" :checked="tableData.conventional"> 是
          <!-- <input type="checkbox" :checked="tableData.partialPressure"> 否 -->
        </td> </tr>
      <tr>
        <td class="center">备注</td>
        <td colspan="10">{{ tableData.remark }}</td>
      </tr>
    </table>
    <img v-if="url" :src="url" class="avatar" style="width:200px;height:150px">
  </div>
</template>

<script>
import { getUser } from '@/api/order/customerOrder'
export default window.$crudCommon({
  data() {
    return {
      tableData: {},
      a: [],
      name: '',
      url: ''

    }
  },
  created() {
    this.url = localStorage.getItem('imageUrl')
    getUser().then(res => {
      this.name = res
    })
    var row = this.$route.query.row
    this.row = row
    this.tableData = row
  },
  methods: {
    back() {
      this.$router.push('/edit')
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
