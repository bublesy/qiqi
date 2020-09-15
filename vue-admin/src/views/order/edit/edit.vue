<template>
  <el-dialog title="订单" :visible.sync="dialog.show" width="1000px" body-style :close-on-click-modal="false">
    <div>
      <el-button type="primary" size="mini" @click="save">保存</el-button>
      <el-button type="primary" size="mini" @click="audit">审核</el-button>

      <el-button type="primary" size="mini" @click="back">返回</el-button>
    </div><br>
    <p style="">订单信息</p>
    <el-card>
      <el-form ref="form" :model="form" label-width="80px" size="mini" :rules="rules" :inline="true">
        <el-form-item label="客户单号:">
          <el-input v-model="form.no" />
        </el-form-item>
        <el-form-item label="款号:">
          <el-input v-model="form.modelNo" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="箱型:" prop="boxType">
          <el-select v-model="form.boxType" placeholder="请选择">
            <el-option
              v-for="item in boxTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="单位:">
          <el-select v-model="form.unit" placeholder="请选择">
            <el-option
              v-for="item in unitOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="材质:" prop="material">
          <el-select v-model="form.material" placeholder="请选择">
            <el-option
              v-for="item in materialOptions"
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
          <el-select v-model="form.group" placeholder="请选择">
            <el-option
              v-for="item in groupOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- 2020 9 9 -->
        <el-form-item label="交货日期:">
          <el-date-picker
            v-model="form.delivery"
            style="width:190px"
            type="date"
            placeholder="选择日期"
          />
        </el-form-item>
        <el-form-item label="订货数量:">
          <el-input v-model="form.orderNum" @input="orderNum" />
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
          <el-select v-model="form.supplier" placeholder="请选择">
            <el-option
              v-for="item in supplierOptions"
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
          <el-select v-model="form2.width" placeholder="请选择">
            <el-option
              v-for="item in widthOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="纸片数量:">
          <el-input v-model="form2.paperNum" @input="paperNum" />
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
          <el-input v-model="form2.ratio" @input="ratio" />
        </el-form-item>
        <el-form-item label="打包:">
          <el-input v-model="form2.pack" />
        </el-form-item>
        <el-form-item label="打钉数:">
          <el-input v-model="form2.pack" @input="pack" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="钉类:">
          <el-input v-model="form2.NailClass" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="颜色:">
          <el-select v-model="form2.color" placeholder="请选择">
            <el-option
              v-for="item in colorOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="印刷版面:">
          <el-select v-model="form2.printSurface" placeholder="请选择">
            <el-option
              v-for="item in printSurfaceOptions"
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
          <el-select v-model="form2.combine" placeholder="请选择">
            <el-option
              v-for="item in combineOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form2.remark" />
        </el-form-item>
      </el-form>
      <p>添加样图:</p>
      <upload />
    </el-card>
  </el-dialog>
</template>

<script>
import upload from '@/views/order/edit/upload'
import { addOrUpdateOrder } from '@/api/order/customerOrder'
export default {
  components: { upload },
  props: {
    dialog: {
      type: Object,
      default: () => {}
    },
    id: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      form: {

      },
      form2: {

      },
      rules: {
        material: [
          { required: true, message: '请输入材质', trigger: 'blur' }
        ],
        boxType: [
          { required: true, message: '请输入箱型', trigger: 'blur' }
        ]
      },
      // 下拉框数组
      value: '',
      options: [],
      boxTypeOptions: [],
      unitOptions: [],
      materialOptions: [],
      groupOptions: [],
      supplierOptions: [],
      widthOptions: [],
      colorOptions: [],
      printSurfaceOptions: [],
      combineOptions: []
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        if (this.id !== '' && this.id !== null) {
          // 编辑
          console.log('aa')
        } else {
          // 新增
          this.form = Object.assign({}, this.$options.data().form)
        }
      }
    }
  },
  methods: {
    save() {
      var list = []
      list.push(this.form)
      list.push(this.form2)
      addOrUpdateOrder(list).then(res => {
        console.log(res)
      })
      this.dialog.show = false
    },
    audit() {
      this.dialog.show = false
    },
    back() {
      this.dialog.show = false
    },
    orderNum(x) {
      if (isNaN(x)) {
        this.form.orderNum = this.form.orderNum.substring(0, this.form.orderNum.length - 1)
        if (isNaN(this.form.orderNum)) {
          this.form.orderNum = ''
        }
      }
    },
    paperNum(x) {
      if (isNaN(x)) {
        this.form2.paperNum = this.form2.paperNum.substring(0, this.form2.paperNum.length - 1)
        if (isNaN(this.form2.paperNum)) {
          this.form2.paperNum = ''
        }
      }
    },
    ratio(x) {
      if (isNaN(x)) {
        this.form2.ratio = this.form2.ratio.substring(0, this.form2.ratio.length - 1)
        if (isNaN(this.form2.ratio)) {
          this.form2.ratio = ''
        }
      }
    },
    pack(x) {
      if (isNaN(x)) {
        this.form2.pack = this.form2.pack.substring(0, this.form2.pack.length - 1)
        if (isNaN(this.form2.pack)) {
          this.form2.pack = ''
        }
      }
    }
  }

}
</script>

<style scoped>

</style>
