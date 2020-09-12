<template>
  <div class="app-container">
    <div>
      <h2 style="text-align:center">海宁市中奇纸箱包装厂</h2>
      <h2 style="text-align:center">生产&nbsp;&nbsp;制作单</h2>
      <div class="dh">
        <p>订货单：<span style="  font-weight: bolder;">百味林</span></p>
        <p>任务编号：<span style="  font-weight: bolder;font-size: 20px;">12090032</span></p>
      </div>
      <table>
        <tr>
          <td>款号:</td>
          <td colspan="2">客户的箱号或者款号</td>
          <td>箱型：</td>
          <td colspan="2">五层箱     </td>
          <td>纸箱尺寸:</td>
          <td colspan="2">600x3000x400</td>
          <td>订单数量：</td>
          <td colspan="2">560</td>
        </tr>
        <tr>
          <td>材质:</td>
          <td colspan="2">美卡+120+110+120</td>
          <td>愣型</td>
          <td colspan="2">AB    </td>
          <td>纸箱尺寸:</td>
          <td colspan="2">1838x704</td>
          <td>订单数量：</td>
          <td colspan="2">0.68</td>
        </tr>
        <tr>
          <td colspan="3" class="center">印刷</td>
          <td colspan="3" class="center">结合</td>
          <td colspan="6" rowspan="5" class="xx"><span>箱型展开图: </span>
            <!-- <img src="./1.jpg" alt="" style="width:400px;height:200px" class="xx_img"> -->
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
          <td>工序</td>
          <td>数量</td>
          <td>操作</td>
          <td>检验</td>
          <td>废品</td>
          <td>备注</td>
        </tr>
        <tr>
          <td><input type="checkbox"> 分压<input type="checkbox"></td>
          <td />
          <td />
          <td />
          <td />
          <td />
        </tr>
        <tr>
          <td><input type="checkbox"> 裱糊<input type="checkbox" checked="checked"></td>
          <td />
          <td />
          <td />
          <td />
          <td />
        </tr>
        <tr>
          <td><input type="checkbox" checked="checked"> 印刷<input type="checkbox" checked="checked"></td>
          <td />
          <td />
          <td />
          <td />
          <td />
          <td colspan="5">说明及注意事项</td>
          <td>总面积</td>
        </tr>
        <tr>
          <td><input type="checkbox" checked="checked"> 开槽<input type="checkbox" checked="checked"></td>
          <td />
          <td />
          <td />
          <td />
          <td />
          <td colspan="5" rowspan="3" style="vertical-align:top;">生产时注意的内容写在这里!显示在生产单上!</td>
          <td>373.744</td>
        </tr>
        <tr>
          <td><input type="checkbox"> 模切<input type="checkbox"></td>
          <td />
          <td />
          <td />
          <td />
          <td />
          <td>是否常规</td>
        </tr><tr /><tr>
          <td><input type="checkbox" checked="checked"> 结合<input type="checkbox" checked="checked"></td>
          <td />
          <td />
          <td />
          <td />
          <td />
          <td>
            <input type="checkbox"> 是<input type="checkbox">
            <input type="checkbox" checked="checked"> 否<input type="checkbox" checked="checked">
          </td>
        </tr>
      </table>
      <div class="footer">
        <p>制单:管理</p>
        <p>复核：</p>
        <p>制单日期：2020-09-11 13:55</p>
        <p>交货日期：2020-09-11</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TagMethods',
  inject: ['closeTag', 'reloadTag', 'refreshTag'],
  data() {
    return {
      my: '这是测试文本'
    }
  },
  methods: {
    changeText() {
      this.my = '这是修改后的文本'
      this.reloadTag()
    }
  }

}
</script>

<style scoped>
.dh{
  display: flex;
  justify-content: space-between;
  padding: 0 10px;
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
