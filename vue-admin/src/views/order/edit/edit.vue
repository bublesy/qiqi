<template>
  <el-dialog title="订单" :visible.sync="dialog.show" width="1000px" body-style :close-on-click-modal="false">
    <div>
      <el-button type="primary" size="mini">保存</el-button>
      <el-button type="primary" size="mini">审核</el-button>
      <el-button type="primary" size="mini" @click="back">返回</el-button>
    </div><br>
    <p style="">订单信息</p>
    <el-card>
      <el-form ref="form" :model="form" label-width="80px" size="mini" :inline="true">
        <el-form-item label="客户单号:">
          <el-input v-model="form.no" />
        </el-form-item>
        <el-form-item label="款号:">
          <el-input v-model="form.modelNo" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="箱型:">
          <!-- <el-input v-model="form.boxType" /> -->
          <el-select v-model="boxType" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="单位:">
          <!-- <el-input v-model="form.unit" /> -->
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="材质:">
          <!-- <el-input v-model="form.material" /> -->
          <el-select v-model="material" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="长X宽X高:">
          <el-input v-model="form.length" style="width:50px" />X
          <el-input v-model="form.width" style="width:50px" />X
          <el-input v-model="form.height" style="width:50px" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="组合:">
          <!-- <el-input v-model="form.group" /> -->
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- 2020 9 9 -->
        <el-form-item label="交货日期:">
          <el-input v-model="form.delivery" />
        </el-form-item>
        <el-form-item label="订货数量:">
          <el-input v-model="form.orderNum" />
        </el-form-item>
        <!-- 1.00 -->
        <el-form-item label="单价:">
          <el-input v-model="form.perPrice" />
        </el-form-item>
        <el-form-item label="金额:">
          <el-input v-model="form.money" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="供方:">
          <!-- <el-input v-model="form.supplier" /> -->
          <el-select v-model="supplier" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>
    <p>生产单信息</p>
    <el-card>
      <el-form :model="form2" :inline="true" size="mini">
        <el-form-item label="纸长X纸宽:">
          <el-input v-model="form2.length" style="width:50px" />X
          <!-- xlk -->
          <!-- <el-input v-model="form2.width" style="width:50px" /> -->
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="纸片数量:">
          <el-input v-model="form2.num" />
        </el-form-item>
        <!-- fxk -->
        <el-form-item label="">
          <el-checkbox v-model="form2.noLinePress">无压线</el-checkbox>
        </el-form-item>
        <!-- fxk -->
        <el-form-item label="">
          <el-checkbox v-model="form2.materialStock">存料</el-checkbox>
        </el-form-item>
        <el-form-item label="耗用门幅:">
          <el-input v-model="form2.fabric" />
        </el-form-item>
        <el-form-item label="门幅倍数:">
          <el-input v-model="form2.ratio" />
        </el-form-item>
        <el-form-item label="打包:">
          <el-input v-model="form2.pack" />
        </el-form-item>
        <el-form-item label="打钉数:">
          <el-input v-model="form2.pack" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="钉类:">
          <el-input v-model="form2.NailClass" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="颜色:">
          <!-- <el-input v-model="form2.color" /> -->
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="印刷版面:">
          <!-- <el-input v-model="form2.printSurface" /> -->
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- fxk -->
        <el-form-item label="">
          <el-checkbox v-model="form2.partialPressure">分压</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form2.pasting">裱糊</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form2.printing">印刷</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form2.grooving">开槽</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form2.dieCutting">模切</el-checkbox>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="结合:">
          <!-- <el-input v-model="form2.combine" /> -->
          <el-select v-model="value" placeholder="请选择">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form2.combine" />
        </el-form-item>
      </el-form>
      <upload />
    </el-card>
  </el-dialog>
</template>

<script>
import upload from '@/views/order/edit/upload'
export default {
  components: { upload },
  props: {
    dialog: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      form: {

      },
      form2: {

      },
      // 下拉框数组
      value: '',
      options: []
    }
  },
  methods: {
    back() {
      this.dialog.show = false
    }
  }

}
</script>

<style scoped>

</style>
