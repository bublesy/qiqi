<template>
  <el-date-picker
    v-model="dateTimeHorizon"
    type="daterange"
    align="right"
    unlink-panels
    range-separator="至"
    start-placeholder="开始日期"
    end-placeholder="结束日期"
    :picker-options="pickerOptions"
  />
</template>
<script>
export default {
  name: 'DateTimeHorizon',
  data() {
    const _this = this
    return {
      // 选择的时间,默认选今天
      dateTimeHorizon: [_this.$moment().startOf('day'), _this.$moment().endOf('day')],
      // 时间选择快捷方式
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().startOf('day'),
                _this.$moment().endOf('day')
              ])
          }
        }, {
          text: '昨天',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().subtract(1, 'days').startOf('day'),
                _this.$moment().subtract(1, 'days').endOf('day')
              ])
          }
        }, {
          text: '本周',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().startOf('isoWeek'),
                _this.$moment().endOf('isoWeek')
              ])
          }
        }, {
          text: '上周',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().subtract(1, 'weeks').startOf('isoWeek'),
                _this.$moment().subtract(1, 'weeks').endOf('isoWeek')
              ])
          }
        }, {
          text: '本月',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().startOf('month'),
                _this.$moment().endOf('month')
              ])
          }
        }, {
          text: '上月',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().subtract(1, 'months').startOf('month'),
                _this.$moment().subtract(1, 'months').endOf('month')
              ])
          }
        }, {
          text: '本季',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().startOf('quarter'),
                _this.$moment().endOf('quarter')
              ])
          }
        }, {
          text: '上季',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().subtract(1, 'quarters').startOf('quarter'),
                _this.$moment().subtract(1, 'quarters').endOf('quarter')
              ])
          }
        }, {
          text: '今年',
          onClick(picker) {
            picker.$emit('pick',
              [_this.$moment().startOf('year'),
                _this.$moment().endOf('year')
              ])
          }
        }]
      }
    }
  },
  watch: {
    dateTimeHorizon: {
      handler(newValue, oldValue) {
        this.$emit('dateTimeHorizonCallBack', this.dateTimeHorizon)
      },
      immediate: true
    }
  }
}
</script>
