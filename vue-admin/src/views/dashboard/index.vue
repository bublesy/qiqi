<template>
  <div class="dashboard-container">
    <!-- 新增/编辑盘点数量 -->
    <el-dialog title="预警" :visible.sync="warningVisible">
      <h3 align="center">仓库预警</h3>
      <el-table
        ref="singleTable"
        :data="warData"
        highlight-current-row
        style="width: 100%"
        border
      >
        <el-table-column v-show="true" prop="warehouseNo" label="入仓单号" />
        <el-table-column v-show="true" prop="surplusNum" label="仓库库存剩余数量" />
      </el-table>
      <br>
      <h3 align="center">成品仓库预警</h3>
      <el-table
        ref="singleTable"
        :data="endWarData"
        highlight-current-row
        style="width: 100%"
        border
      >
        <el-table-column v-show="true" prop="warehouseNo" label="入仓单号" />
        <el-table-column v-show="true" prop="endProductPos" label="成品库存剩余数量" />
      </el-table>
      <br>
      <h3 align="center">客户结算预警</h3>
      <el-table
        ref="singleTable"
        :data="clients"
        highlight-current-row
        style="width: 100%"
        border
      >
        <el-table-column v-show="true" prop="name" label="客户名称" />
        <el-table-column v-show="true" prop="orderNum" label="订单数量" width="200" />
        <el-table-column label="金额" prop="money" />
        <el-table-column v-show="true" prop="deliveryDate" label="交货日期" />
        <el-table-column label="期初金额" prop="beginReceive" />
        <el-table-column v-show="true" prop="payed" label="已付款" />
        <el-table-column v-show="true" prop="unPayed" label="未付款" />
      </el-table>
      <h3 align="center">供应商结算预警</h3>
      <el-table
        ref="singleTable"
        :data="supplierSettData"
        highlight-current-row
        style="width: 100%"
        border
      >
        <el-table-column v-show="true" prop="documentsNo" label="采购单号" />
        <el-table-column v-show="true" prop="settlementDate" label="结算日期" width="200" />
        <el-table-column v-show="true" prop="purchaseQuantity" label="订单数量" />
        <el-table-column v-show="true" prop="costPrice" label="成本价" />
        <el-table-column v-show="true" prop="amount" label="结算金额" />
        <el-table-column v-show="true" prop="alreadyMoney" label="已收金额" />
        <el-table-column v-show="true" prop="stayAlreadyMoney" label="代收金额" />
      </el-table>
      <br>
      <h3 align="center">成品发货预警</h3>
      <el-table
        ref="singleTable"
        :data="endDisData"
        highlight-current-row
        style="width: 100%"
        border
      >
        <el-table-column v-show="true" prop="orderQuantity" label="订单数量" />
        <el-table-column v-show="true" prop="alreadyDeliveryQuantity" label="已发货数量" />
        <el-table-column v-show="true" prop="stayDeliveryQuantity" label="订单待发货数量" />
        <el-table-column v-show="true" prop="endProductPos" label="成品仓库剩余库存" />
        <el-table-column v-show="true" prop="storageQuantity" label="成品入库数量" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="warningNo">取 消</el-button>
        <el-button size="small" type="primary" @click="warningOk('purForm')">确 定</el-button>
      </span>
    </el-dialog>
    <H4>营业数据</H4>
    <el-card class="box-card">
      <el-row :gutter="20" style="text-align: center">
        <el-col
          :span="3"
        ><h1>￥{{ amount }}</h1>
          年度营业额</el-col>
        <el-col
          :span="3"
        ><h1>￥{{ unpaid }}</h1>
          客户未付款</el-col>
        <el-col
          :span="3"
        ><h1>￥{{ paid }}</h1>
          客户已付款</el-col>
        <el-col
          :span="3"
        ><h1>￥{{ unpayed }}</h1>
          供应商未付款</el-col>
        <el-col
          :span="3"
        ><h1>￥{{ yfmoney }}</h1>
          供应商已付款</el-col>
        <el-col
          :span="3"
        ><h1>￥{{ paid-yfmoney }}</h1>
          毛利
        </el-col>
        <el-col
          :span="3"
        ><h1>{{ position }}</h1>
          原料入库数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ surplus }}</h1>
          原料剩余数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ waste }}</h1>
          原料损耗数量</el-col>
        <!-- <el-col
          :span="3"
        ><h1>{{ c }}</h1>
          仓库已发货</el-col>
        <el-col
          :span="3"
        ><h1>{{ noc }}</h1>
          仓库未发货</el-col>
        -->
        <el-col
          :span="3"
        ><h1>{{ storageQuantity }}</h1>
          成品入库数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ endProductPos }}</h1>
          成品仓库剩余数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ alreadyDeliveryQuantity }}</h1>
          订单已发货</el-col>
        <el-col
          :span="3"
        ><h1>{{ delivereds }}</h1>
          订单未发货</el-col>
        <el-col
          :span="3"
        ><h1>{{ getOrder }}</h1>
          客户订单总数</el-col>
        <el-col
          :span="3"
        ><h1>{{ customs }}</h1>
          客户数</el-col>
        <el-col
          :span="3"
        ><h1>{{ added }}</h1>
          较昨日新增客户数</el-col>
      </el-row>
    </el-card>
    <H4>营业额</H4>
    <el-card>
      <el-date-picker
        v-model="value2"
        :type="dateType1"
        placeholder="选择日期"
        :format="dateType"
        @change="typeChange"
      />
      <el-col :span="4" style="float: right">
        <el-radio-group v-model="timeType" @change="typeChange">
          <el-radio-button label="日" />
          <el-radio-button label="月" />
          <el-radio-button label="年" />
        </el-radio-group>
      </el-col>
      <div ref="chart" style="width: 100%; height: 500px" />
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getWarDate } from '@/api/warehouse/warehouse'
import { getEndWarDate } from '@/api/warehouse/warehouse'
import { getSupplierSettData } from '@/api/warehouse/warehouse'
import { getEndDisData } from '@/api/warehouse/warehouse'

import {
  getOrder,
  purchase,
  get,
  added,
  amount,
  paid,
  unpaid,
  orders,
  warehouseList,
  endWarehouseList,
  client
  // mlist
} from '@/api/accessories/means'
export default {
  name: 'Dashboard',
  inject: ['closeTag'],
  data() {
    return {
      customs: '',
      added: '',
      amount: '',
      paid: '',
      unpaid: '',
      orders: '',
      value2: '',
      timeType: '',
      getOrder: '',
      xData: [
        '1:00',
        '2:00',
        '3:00',
        '4:00',
        '5:00',
        '6:00',
        '7:00',
        '8:00',
        '9:00',
        '10:00',
        '11:00',
        '12:00',
        '13:00',
        '14:00',
        '15:00',
        '16:00',
        '17:00',
        '18:00',
        '19:00',
        '20:00',
        '21:00',
        '22:00',
        '23:00',
        '24:00'
      ],
      yData: [1, 8, 20, 26, 42],
      dateType: 'yyyy-MM-dd',
      dateType1: 'date',
      x: {
        page: 1,
        size: 10,
        time: ''
      },
      //  供应商相关
      alreadyMoney: '',
      wj: '',
      profits: 0,
      formWare: {},
      formEndWare: { carryTo: '' },
      // 库存相关
      deliveryQuantity: '',
      position: '',
      c: '',
      noc: '',
      c1: '',
      noc1: '',
      pos: '',
      pos1: '',
      allPos: '',
      allPos1: '',
      am: {
        type: '3',
        date: 'Mon, 28 Sep 2020 10:40:29 GMT'
      },
      pa: {
        type: '3',
        date: 'Mon, 28 Sep 2020 10:40:29 GMT'
      },
      un: {
        type: '3',
        date: 'Mon, 28 Sep 2020 10:40:29 GMT'
      },
      gross: '',
      mlists: {
        time: '',
        page: 1,
        count: 10,
        quantityOverdue: '',
        customerName: '',
        name: '',
        discount: 100,
        no: '',
        customerNo: '',
        boxType: '',
        modelNo: '',
        length: '',
        width: '',
        unit: '',
        orderNum: '',
        height: '',
        perPrice: '',
        amount: '',
        costPrice: '',
        costAmount: '',
        pbilling: '',
        quantity: '',
        position: 0,
        deliveryQuantity: 0
      },
      surplus: 0,
      storageQuantity: 0,
      endProductPos: 0,
      alreadyDeliveryQuantity: 0,
      delivereds: '',
      time: '',
      unpayed: 0,
      yfmoney: 0,
      totalAmout: 0,
      waste: 0,
      aaa: {
        size: 10,
        page: 1,
        time: ''
      },
      warningVisible: false,
      warData: [], custSettData: [], supplierSettData: [], endWarData: [], endDisData: [], clients: []
    }
  },
  computed: {
    ...mapGetters(['name'])
  },
  created() {
    // 供应商已结未结
    purchase(this.aaa).then(res => {
      console.log('供应商', res)
      var not = 0
      var tot = 0
      res.list.forEach((a) => {
        if (a.alreadyMoney === null) {
          a.alreadyMoney = 0
        }
        not += parseInt(a.alreadyMoney)
        tot += parseInt(a.totalAmount)
      })
      this.yfmoney = not
      this.totalAmout = tot
      this.unpayed = this.totalAmout - this.yfmoney
    })
    // 未付款
    unpaid(this.un).then(res => {
      this.unpaid = res
    })
    // 已付款
    paid(this.pa).then(res => {
      this.paid = res
    })
    // 毛利
    // mlist(this.mlists).then((res) => {
    //   // console.log(res)
    //   var ml = 0
    //   res.list.forEach((a) => {
    //     a.costAmount = a.costPrice * a.position
    //     a.profit = a.discountAmount - a.costAmount
    //     ml += a.profit
    //     this.gross = ml.toFixed(2)
    //   })
    // })
    // 营业额
    amount(this.am).then((res) => {
      this.amount = res
    })
    // 客户订单总数
    getOrder().then((res) => {
      this.getOrder = res
      // console.log(res)
    })
    // 客户总数
    get().then((res) => {
      this.customs = res
    })
    // 新增总数
    added().then((res) => {
      this.added = res
    })
    // 仓库原料
    warehouseList(this.formWare).then((res) => {
      console.log('原料', res.list)
      var pos = 0
      var del = 0
      var was = 0
      res.list.forEach((a) => {
        pos += parseInt(a.position)
        if (a.deliveryQuantity === null) {
          a.deliveryQuantity = 0
        }
        del += parseInt(a.deliveryQuantity)
        was += parseInt(a.deliveryQuantity)
      })
      this.position = pos
      this.deliveryQuantity = del
      this.surplus = this.position - this.deliveryQuantity
      this.waste = was
    })
    // 成品库存
    endWarehouseList(this.formEndWare).then((res) => {
      console.log('成品', res)
      var sto = 0
      var end = 0
      var alr = 0
      var ord = 0
      res.list.forEach((a) => {
        if (a.storageQuantity === null) {
          a.storageQuantity = 0
        }
        if (a.alreadyDeliveryQuantity === null) {
          a.alreadyDeliveryQuantity = 0
        }
        if (a.orderNum === null) {
          a.orderNum = 0
        }
        sto += parseInt(a.storageQuantity)
        end += parseInt(a.endProductPos)
        alr += parseInt(a.alreadyDeliveryQuantity)
        ord += parseInt(a.orderNum)
      })
      this.storageQuantity = sto
      this.endProductPos = end
      this.alreadyDeliveryQuantity = alr
      this.orderNum = ord
      this.delivereds = this.orderNum - this.alreadyDeliveryQuantity
    })
    // 订单总数
    var data = {
      //  date: this.value2,
      date: 'Mon, 28 Sep 2020 10:40:29 GMT ',
      type: 1
    }
    orders(data).then((res) => {
      for (var i = 0; i < res.length; i++) {
        this.xData[i] = res[i].小时
        this.yData[i] = res[i].营业额
      }
      this.initCharts()
    })
    this.getWarning()
  },
  mounted() {
    this.initCharts()
  },
  methods: {
    warningNo() { this.warningVisible = false },
    warningOk() { this.warningVisible = false },
    getWarning() {
      this.warningVisible = true
      // 客户结算预警
      client().then(res => {
        console.log('客户', res)
        this.clients = res
        this.clients.forEach(x => {
          this.$set(x, 'unPayed', x.money - x.payed)
        })
      })
      getWarDate().then(res => {
        this.warData = res
        this.warData.forEach(a => {
          var b = parseInt(a.position)
          var c = parseInt(a.deliveryQuantity)
          a.surplusNum = b - c
        })
      })
      getEndWarDate().then(res => {
        this.endWarData = res
      })
      getSupplierSettData().then(res => {
        this.supplierSettData = res
        this.supplierSettData.forEach(a => {
          a.stayAlreadyMoney = parseInt(a.amount) - a.alreadyMoney
          var t = new Date(a.billingDate)
          var t_s = t.getTime()
          var days = parseInt(a.settlementPeriod)
          var time = t_s + 1000 * 60 * 60 * 24 * days
          var date = new Date(time)
          var year = date.getFullYear()
          /* 在日期格式中，月份是从0开始的，因此要加0
          * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
          * */
          var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
          var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
          var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
          var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
          var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
          // 拼接
          year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
          a.settlementDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
        })
      })
      getEndDisData().then(res => {
        this.endDisData = res
        this.endDisData.forEach(a => {
          a.stayDeliveryQuantity = parseInt(a.orderQuantity) - a.alreadyDeliveryQuantity
        })
      })
    },
    initCharts() {
      const myChart = this.$echarts.init(this.$refs.chart)
      myChart.setOption({
        title: {
          text: ''
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['营业额', '成本价', '毛利']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          // type: 'category',
          // boundaryGap: false,
          data: this.xData
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '营业额',
            type: 'line',
            stack: '总量',
            data: this.yData
          }
        ]
      })
    },
    typeChange(val) {
      if (this.value2 === null || this.value2 === '') {
        var a = new Date().getTime()
        this.value2 = a
        var b = new Date(this.value2)
        var c = b.toString()
        this.value2 = c
        console.log(this.value2)
      } else {
        var d = this.value2.toString()
        this.value2 = d
        console.log(this.value2)
      }
      // 日
      this.yData = []
      this.xData = []
      var data = {
        //  date: this.value2,
        date: this.value2,
        type: 1
      }

      if (val === '日' || this.timeType === '日') {
        this.dateType = 'yyyy-MM-dd'
        this.dateType1 = 'date'
        data.type = 1
        data.date = this.value2
        orders(data).then((res) => {
          for (var i = 0; i < res.length; i++) {
            this.xData[i] = res[i].小时
            this.yData[i] = res[i].营业额
          }
          // this.yData= res[0][0]
          this.initCharts()
        })
      } else if (val === '月') {
        this.dateType = 'yyyy-MM'
        this.dateType1 = 'month'
        data.type = 2
        data.date = this.value2
        orders(data).then((res) => {
          for (var i = 0; i < res.length; i++) {
            this.xData[i] = res[i].月份
            this.yData[i] = res[i].营业额
          }
          // this.yData= res[0][0]
          this.initCharts()
        })
      } else if (val === '年') {
        this.dateType = 'yyyy'
        this.dateType1 = 'year'
        data.type = 3
        data.date = this.value2
        orders(data).then((res) => {
          for (var i = 0; i < res.length; i++) {
            this.xData[i] = res[i].年份
            this.yData[i] = res[i].营业额
          }
          // this.yData= res[0][0]
          this.initCharts()
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
