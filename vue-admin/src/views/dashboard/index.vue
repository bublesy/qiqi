<template>
  <div class="dashboard-container">
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
          客户已收款</el-col>
        <el-col
          :span="3"
        ><h1>{{ allPos }}</h1>
          仓库数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ pos }}</h1>
          仓库剩余库存</el-col>
        <el-col
          :span="3"
        ><h1>{{ c }}</h1>
          仓库已发货</el-col>
        <el-col
          :span="3"
        ><h1>{{ noc }}</h1>
          仓库未发货</el-col>
        <el-col
          :span="3"
        ><h1>{{ allPos1 }}</h1>
          成品仓库实际库存</el-col>
        <el-col
          :span="3"
        ><h1>{{ pos1 }}</h1>
          成品仓库剩余库存</el-col>
        <el-col
          :span="3"
        ><h1>{{ c1 }}</h1>
          成品仓库已发货</el-col>
        <el-col
          :span="3"
        ><h1>{{ noc1 }}</h1>
          成品仓库未发货</el-col>
        <el-col
          :span="3"
        ><h1>￥{{ gross }}</h1>
          毛利
        </el-col>
        <el-col
          :span="3"
        ><h1>{{ customs }}</h1>
          订单总数</el-col>
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
import {
  get,
  added,
  amount,
  paid,
  unpaid,
  orders,
  warehouseList,
  endWarehouseList,
  mlist
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
        quantity: ''
      }
    }
  },
  computed: {
    ...mapGetters(['name'])
  },
  created() {
    // 未付款
    unpaid(this.un).then(res => {
      this.unpaid = res
    })
    // 已付款
    paid(this.pa).then(res => {
      this.paid = res
    })
    // 毛利
    mlist(this.mlists).then((res) => {
      console.log(res)
      var ml = 0
      res.list.forEach((a) => {
        a.costAmount = a.costPrice * a.position
        a.profit = a.discountAmount - a.costAmount
        ml += a.profit
        this.gross = ml.toFixed(2)
      })
    })
    // 营业额
    amount(this.am).then((res) => {
      this.amount = res
    })
    // 客户总数
    get().then((res) => {
      this.customs = res
    })
    // 新增总数
    added().then((res) => {
      this.added = res
    })
    // 总库存
    warehouseList(this.formWare).then((res) => {
      var a1 = 0
      var a2 = 0
      var a3 = 0
      res.list.forEach((a) => {
        if (a.deliveryQuantity === undefined) {
          a.deliveryQuantity = 0
        }
        if (a.orderQuantity === undefined) {
          a.orderQuantity = 0
        }
        if (a.deliveryStatus === '未送货') {
          a1 += parseInt(a.orderQuantity) - parseInt(a.deliveryQuantity)
        }
        if (a.deliveryStatus === '已送货') {
          a2 += parseInt(a.orderQuantity) - parseInt(a.deliveryQuantity)
        }
        a3 += parseInt(a.position)
      })
      this.pos = a3
      this.noc = a1
      this.c = a2
      this.allPos = a3 + a2
    })
    endWarehouseList(this.formEndWare).then((res) => {
      // console.log('a',res)
      var a1 = 0
      var a2 = 0
      var a3 = 0
      res.list.forEach((a) => {
        if (a.deliveryQuantity === undefined) {
          a.deliveryQuantity = 0
        }
        if (a.orderQuantity === undefined) {
          a.orderQuantity = 0
        }
        if (a.carryTo === '未送货') {
          a1 += parseInt(a.orderQuantity) - parseInt(a.deliveryQuantity)
        }
        if (a.carryTo === '已送货') {
          a2 += parseInt(a.deliveryQuantity)
        }
        a3 += parseInt(a.endProductPos)
      })
      this.noc1 = a1
      this.c1 = a2
      this.pos1 = a3
      this.allPos1 = a3 + a2
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
  },
  mounted() {
    this.initCharts()
  },
  methods: {
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
