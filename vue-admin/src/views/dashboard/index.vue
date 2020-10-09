<template>
  <div class="dashboard-container">
    <!-- 新增/编辑盘点数量 -->
    <el-dialog title="预警" :visible.sync="warningVisible">
      <el-table
        ref="singleTable"
        :data="warData"
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column v-show="true" prop="position" label="仓库库存剩余数量" />
      </el-table>
      <el-table
        ref="singleTable"
        :data="endWarData"
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column v-show="true" prop="position" label="成品库存剩余数量" />
      </el-table>
      <el-table
        ref="singleTable"
        :data="custSettData"
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column v-show="true" prop="position" label="客户结算预警" />
      </el-table>
      <el-table
        ref="singleTable"
        :data="supplierSettData"
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column v-show="true" prop="position" label="供应商结算预警" />
      </el-table>
      <el-table
        ref="singleTable"
        :data="endDisData"
        highlight-current-row
        style="width: 100%"
      >
        <el-table-column v-show="true" prop="position" label="成品发货预警" />
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
        ><h1>￥0</h1>
          供应商未付款</el-col>
        <el-col
          :span="3"
        ><h1>￥0</h1>
          供应商已付款</el-col>
        <el-col
          :span="3"
        ><h1>{{ position }}</h1>
          仓库数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ surplus }}</h1>
          仓库剩余数量</el-col>
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
          成品仓库数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ endProductPos }}</h1>
          成品仓库剩余数量</el-col>
        <el-col
          :span="3"
        ><h1>{{ alreadyDeliveryQuantity }}</h1>
          成品仓库已发货</el-col>
        <el-col
          :span="3"
        ><h1>{{ delivereds }}</h1>
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
  purchase,
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
      queryParams: {
        size: 10,
        page: 1,
        time: ''
      },
      warningVisible: false,
      warData: [], custSettData: [], supplierSettData: [], endWarData: [], endDisData: []
    }
  },
  computed: {
    ...mapGetters(['name'])
  },
  created() {
    // 供应商已结未结 this.queryParams.time = this.form.time
    // if (this.queryParams.time === null) {
    //   this.$set(this.queryParams, 'time', '')
    // }
    // this.queryParams.time = this.aaa.time
    console.log(this.queryParams)
    purchase(this.queryParams).then(res => {
      console.log('供应商', res)
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
    mlist(this.mlists).then((res) => {
      // console.log(res)
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
    // 仓库原料
    warehouseList(this.formWare).then((res) => {
      console.log('原料', res.list)
      var pos = 0
      var del = 0
      res.list.forEach((a) => {
        pos += parseInt(a.position)
        if (a.deliveryQuantity === null) {
          a.deliveryQuantity = 0
        }
        del += parseInt(a.deliveryQuantity)
      })
      this.position = pos
      this.deliveryQuantity = del
      this.surplus = this.position - this.deliveryQuantity
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
