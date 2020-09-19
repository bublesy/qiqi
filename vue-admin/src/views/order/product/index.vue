<template>
  <div class="app-container">
    <div>
      <el-button type="success" style="margin-left:80%" @click="print">前往打印</el-button>
      <!-- <el-button type="success" style="margin-left:60%" @click="back">返回</el-button> -->
      <h2 style="text-align:center">海宁市中奇纸箱包装厂</h2>
      <h2 style="text-align:center">生产&nbsp;&nbsp;制作单</h2>
      <div class="dh">
        <p>订货单位：<span style="  font-weight: bolder;">{{ tableData.name }}</span></p>
        <p>任务编号：<span style="  font-weight: bolder;font-size: 20px;">{{ tableData.no }}</span></p>
      </div>
      <table>
        <tr>
          <td>款号:</td>
          <td colspan="2">{{ tableData.modelNo }}</td>
          <td>箱型：</td>
          <td colspan="2">{{ tableData.boxType }}    </td>
          <td>纸箱尺寸:</td>
          <td colspan="2">{{ tableData.length }}*{{ +tableData.width }}*{{ tableData.length }}</td>
          <td>订单数量：</td>
          <td colspan="2">{{ tableData.orderNum }}</td>
        </tr>
        <tr>
          <td>材质:</td>
          <td colspan="2">{{ tableData.material }}</td>
          <td>愣型</td>
          <td colspan="2">{{ tableData.stare }}</td>
          <td>纸片尺寸:</td>
          <td colspan="2">{{ tableData.paperLength }}*{{ +tableData.paperWidth }}</td>
          <td>纸箱面积：</td>
          <td colspan="2">{{ tableData.paperArea }}</td>
        </tr>
        <tr>
          <td colspan="3" class="center">印刷</td>
          <td colspan="3" class="center">结合</td>
          <td colspan="6" rowspan="5" class="xx"><span>箱型展开图: </span>
            <img v-if="tableData.img" src="D:\hc\Carton\dream\admin\target\classes\upload\2020\09\19\file.jpg" class="avatar">
          </td>
        </tr>
        <tr>
          <td colspan="2">颜色</td>
          <td>版面</td>
          <td>结合</td>
          <td>钉类</td>
          <td>打钉数</td>
        </tr>
        <tr>
          <td colspan="2">{{ tableData.color }}</td>
          <td>{{ tableData.printSurface }}</td>
          <td>{{ tableData.combine }}</td>
          <td>{{ tableData.nailClass }}</td>
          <td>{{ tableData.nailingNumber }}</td>
        </tr>
        <tr>
          <td>工序</td>
          <td>数量</td>
          <td>操作</td>
          <td>检验</td>
          <td>废品</td>
          <td>备注</td>
        </tr>
        <tr>
          <td><input type="checkbox" :checked="tableData.partialPressure"> 分压</td>
          <td />
          <td />
          <td />
          <td />
          <td />
        </tr>
        <tr>
          <td><input type="checkbox" :checked="tableData.pasting"> 裱糊</td>
          <td />
          <td />
          <td />
          <td />
          <td />
        </tr>
        <tr>
          <td><input type="checkbox" :checked="tableData.printing"> 印刷</td>
          <td />
          <td />
          <td />
          <td />
          <td />
          <td colspan="5">说明及注意事项</td>
          <td>总面积</td>
        </tr>
        <tr>
          <td><input type="checkbox" :checked="tableData.grooving"> 开槽</td>
          <td />
          <td />
          <td />
          <td />
          <td />
          <td colspan="5" rowspan="4" style="vertical-align:top;">生产时注意的内容写在这里!显示在生产单上!</td>
          <td>{{ tableData.totalArea }}</td>
        </tr>
        <tr>
          <td><input type="checkbox" :checked="tableData.dieCutting"> 模切</td>
          <td />
          <td />
          <td />
          <td />
          <td />
          <td>是否常规</td>
        </tr>
        <td><input type="checkbox" :checked="tableData.combine"> 结合</td>
        <td />
        <td />
        <td />
        <td />
        <td />
        <td>
          <input type="checkbox" :checked="tableData.conventional"> 是
          <!-- <input type="checkbox" :checked="!conventional"> 否 -->
        </td>
      </table>
      <div class="footer">
        <p>制单:{{ name }}</p>
        <p>复核：</p>
        <p>制单日期：{{ tableData.orderDate }}</p>
        <p>交货日期：{{ tableData.deliveryDate }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { getUser } from '@/api/order/customerOrder'
export default {
  name: 'TagMethods',
  inject: ['closeTag', 'reloadTag', 'refreshTag'],
  data() {
    return {
      tableData: {},
      row: {},
      name: ''
    }
  },
  created() {
    getUser().then(res => {
      this.name = res.nickname
    })
    var row = this.$route.query.row
    this.row = row
    console.log(row.img)
    // row.img = row.img.substring(5, row.img.length)
    this.tableData = row
  },
  methods: {
    changeText() {
      this.reloadTag()
    },
    onload(id) {
      console.log(id)
    },
    print() {
      this.$router.push({
        path: '/index2',
        query: { row: this.row }
      })
    }
  }

}
</script>

<style scoped>
.dh{
  display: flex;
  justify-content: space-between;
  width: 1000px;
  margin: 0 auto;
}
table,tr,td{
  border:1px solid black;
}
table{
width: 1000px;
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
.footer{
  display: flex;
  width: 1000px;
  /* height: 100px;
  background: pink; */
  padding-left: 10px;
  margin: 0 auto;
}
.footer>p:nth-of-type(2){
  margin-left: 150px;
}
.footer>p:nth-of-type(3){
  margin-left: 100px;
}
.footer>p:nth-of-type(4){
  margin-left: 100px;
}
.xx{
  position: relative;
}
.xx span{
  position: absolute;
  top: 0;
  left: 0;
}
.xx_img{
  display: block;
  margin-left: 8%;
}
</style>
