<template>
  <el-dialog title="订单" :visible.sync="dialog.show" width="1000px" body-style :close-on-click-modal="false">
    <div>
      <el-button type="primary" size="mini" @click="save">保存</el-button>
      <!-- <el-button type="primary" size="mini" @click="audit">审核</el-button> -->

      <el-button type="primary" size="mini" @click="back">返回</el-button>
    </div><br>
    <p style="">订单信息</p>
    <el-card>
      <el-form ref="form" :model="form" label-width="80px" size="mini" :rules="rules" :inline="true">
        <el-form-item label="选择客户:">
          <el-select v-model="form.name" placeholder="请选择">
            <el-option
              v-for="item in customerOptions"
              :key="item.id"
              :label="item.fullName"
              :value="item.fullName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="客户单号:">
          <el-input v-model="form.customerNo" @input="customerNo" />
        </el-form-item>
        <el-form-item label="款号:">
          <el-input v-model="form.modelNo" @input="modelNo" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="箱型:" prop="boxType">
          <el-select v-model="form.boxType" placeholder="请选择">
            <el-option
              v-for="item in boxTypeOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="单位:">
          <el-input v-model="form.unit" />
          <!-- <el-select v-model="form.unit" placeholder="请选择">
            <el-option
              v-for="item in unitOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select> -->
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="材质:" prop="material">
          <el-select v-model="form.material" placeholder="请选择">
            <el-option
              v-for="item in materialOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="长X宽X高:">
          <el-input v-model="form.length" style="width:50px" @input="length" />X
          <el-input v-model="form.width" style="width:50px" @input="width" />X
          <el-input v-model="form.height" style="width:50px" @input="height" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="组合:">
          <el-select v-model="form.groups" placeholder="请选择">
            <el-option
              v-for="item in groupOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <!-- 2020 9 9 -->
        <el-form-item label="交货日期:">
          <el-date-picker
            v-model="form.deliveryDate"
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
          <el-input v-model="form.perPrice" @input="perPrice" />
        </el-form-item>
        <el-form-item label="金额:">
          <el-input v-model="form.money" @input="money" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="供方:">
          <el-select v-model="form.supplier" placeholder="请选择">
            <el-option
              v-for="item in supplierOptions"
              :key="item.id"
              :label="item.fullName"
              :value="item.fullName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="是否成品:">
          <el-checkbox v-model="form.isProduct" />
        </el-form-item>
      </el-form>
    </el-card>
    <p>生产单信息</p>
    <el-card>
      <el-form :model="form" :inline="true" size="mini">
        <el-form-item label="纸长X纸宽:">
          <el-input v-model="form.paperLength" style="width:50px" @input="paperLength" />X
          <el-input v-model="form.paperWidth" style="width:50px" @input="paperWidth" />
          <!-- xlk -->
          <!-- <el-select v-model="form.paperWidth" placeholder="请选择">
            <el-option
              v-for="item in widthOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select> -->
        </el-form-item>
        <el-form-item label="纸片数量:">
          <el-input v-model="form.paperNum" @input="paperNum" />
        </el-form-item>
        <!-- fxk -->
        <el-form-item label="">
          <el-checkbox v-model="form.noLinePress">无压线</el-checkbox>
        </el-form-item>
        <!-- fxk -->
        <el-form-item label="">
          <el-checkbox v-model="form.materialStock">存料</el-checkbox>
        </el-form-item>
        <el-form-item label="耗用门幅:">
          <el-input v-model="form.fabric" @input="fabric" />
        </el-form-item>
        <el-form-item label="门幅倍数:">
          <el-input v-model="form.ratio" @input="ratio" />
        </el-form-item>
        <el-form-item label="打包:">
          <el-input v-model="form.pack" />
        </el-form-item>
        <el-form-item label="打钉数:">
          <el-input v-model="form.nailingNumber" @input="pack" />
        </el-form-item>
        <el-form-item label="楞型:">
          <el-input v-model="form.stare" />
        </el-form-item>
        <el-form-item label="分压规格:">
          <el-input v-model="form.pressureSpecification" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="钉类:">
          <!-- <el-input v-model="form.NailClass" /> -->
          <el-select v-model="form.nailClass" placeholder="请选择">
            <el-option
              v-for="item in NailClassOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="总面积:">
          <el-input v-model="form.totalArea" @input="totalArea" />
        </el-form-item>
        <el-form-item label="纸箱面积:">
          <el-input v-model="form.paperArea" @input="paperArea" />
        </el-form-item>
        <el-form-item label="是否常规:">
          <el-checkbox v-model="form.conventional" />
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="颜色:">
          <el-select v-model="form.color" placeholder="请选择">
            <el-option
              v-for="item in colorOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="印刷版面:">
          <el-select v-model="form.printSurface" placeholder="请选择">
            <el-option
              v-for="item in printSurfaceOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <!-- fxk -->
        <el-form-item label="">
          <el-checkbox v-model="form.partialPressure">分压</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form.pasting">裱糊</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form.printing">印刷</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form.grooving">开槽</el-checkbox>
        </el-form-item>
        <el-form-item label="">
          <el-checkbox v-model="form.dieCutting">模切</el-checkbox>
        </el-form-item>
        <!-- xlk -->
        <el-form-item label="结合:">
          <el-select v-model="form.combine" placeholder="请选择">
            <el-option
              v-for="item in combineOptions"
              :key="item.id"
              :label="item.name"
              :value="item.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" />
        </el-form-item>
      </el-form>
      <p :imageUrl="imageUrl">添加样图:</p>
      <upload />
    </el-card>
  </el-dialog>
</template>

<script>
import upload from '@/views/order/edit/upload'
import { addOrUpdateOrder, getSupplier, getMaterial, getUnite, getColor, getNails,
  getCombination, getPrintLayout, getSingleOrder, getCustomer } from '@/api/order/customerOrder'
import { getBoxClassList } from '@/api/basedata/boxclass'
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
    },
    flag: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      imageUrl: '',
      form: {

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
      combineOptions: [],
      NailClassOptions: [],
      customerOptions: []
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        getBoxClassList().then(res => {
          this.boxTypeOptions = res
        })

        getSupplier().then(res => {
          this.supplierOptions = res
        })

        getMaterial().then(res => {
          this.materialOptions = res
        })

        getUnite().then(res => {
          this.combineOptions = res
        })

        getCombination().then(res => {
          this.groupOptions = res
        })

        getColor().then(res => {
          this.colorOptions = res
        })
        getNails().then(res => {
          this.NailClassOptions = res
        })
        getPrintLayout().then(res => {
          this.printSurfaceOptions = res
        })
        getCustomer().then(res => {
          this.customerOptions = res
        })
        if (this.id !== '' && this.id !== null) {
          // 编辑
          console.log(this.id)
          getSingleOrder(this.id).then(res => {
            console.log(res)
            this.form = res
            this.imageUrl = res.img
          })
        } else {
          // 新增
          this.form = Object.assign({}, this.$options.data().form)
        }
      }
    }
  },
  methods: {
    save() {
      // var list = []
      // list.push(this.form)
      // list.push(this.form)
      if (this.flag) {
        this.form.id = null
      }
      this.form.img = localStorage.getItem('imageUrl')
      addOrUpdateOrder(this.form).then(res => {
        if (res) {
          this.$emit('init')
        }
      })
      this.dialog.show = false
    },
    audit() {
      this.dialog.show = false
    },
    back() {
      this.dialog.show = false
    },
    money(x) {
      if (isNaN(x)) {
        this.form.money = this.form.money.substring(0, this.form.money.length - 1)
        if (isNaN(this.form.money)) {
          this.form.money = 0
        }
      }
    },
    perPrice(x) {
      if (isNaN(x)) {
        this.form.perPrice = this.form.perPrice.substring(0, this.form.perPrice.length - 1)
        if (isNaN(this.form.perPrice)) {
          this.form.perPrice = ''
        }
      }
      this.form.money = parseFloat(this.form.orderNum) * parseFloat(this.form.perPrice)
      this.form.money = parseFloat(this.form.money).toFixed(2)
      if (isNaN(this.form.money)) {
        this.form.money = 0
      }
    },
    orderNum(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入整数')
        return
      }
      if (isNaN(x)) {
        this.form.orderNum = this.form.orderNum.substring(0, this.form.orderNum.length - 1)
        if (isNaN(this.form.orderNum)) {
          this.form.orderNum = ''
        }
      }
      this.form.money = parseFloat(this.form.orderNum) * parseFloat(this.form.perPrice)
      this.form.money = parseFloat(this.form.money).toFixed(2)
      if (isNaN(this.form.money)) {
        this.form.money = 0
      }
    },
    paperNum(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入毫米为单位的整数')
        this.form.paperNum = ''
        return
      }
      if (isNaN(x)) {
        this.form.paperNum = this.form.paperNum.substring(0, this.form.paperNum.length - 1)
        if (isNaN(this.form.paperNum)) {
          this.form.paperNum = ''
        }
      }
    },
    ratio(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入整数')
        this.form.ratio = ''
        return
      }
      if (isNaN(x)) {
        this.form.ratio = this.form.ratio.substring(0, this.form.ratio.length - 1)
        if (isNaN(this.form.ratio)) {
          this.form.ratio = ''
        }
      }
    },
    pack(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入整数')
        this.form.nailingNumber = ''
        return
      }
      if (isNaN(x)) {
        this.form.pack = this.form.pack.substring(0, this.form.pack.length - 1)
        if (isNaN(this.form.pack)) {
          this.form.pack = ''
        }
      }
    },
    customerNo(x) {
      if (isNaN(x)) {
        this.form.customerNo = this.form.customerNo.substring(0, this.form.customerNo.length - 1)
        if (isNaN(this.form.customerNo)) {
          this.form.customerNo = ''
        }
      }
    },
    modelNo(x) {
      if (isNaN(x)) {
        this.form.modelNo = this.form.modelNo.substring(0, this.form.modelNo.length - 1)
        if (isNaN(this.form.modelNo)) {
          this.form.modelNo = ''
        }
      }
    },
    length(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入毫米为单位的整数')
        return
      }
      if (isNaN(x)) {
        this.form.length = this.form.length.substring(0, this.form.length.length - 1)
        if (isNaN(this.form.length)) {
          this.form.length = ''
        }
      }
    },
    width(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入毫米为单位的整数')
        return
      }
      if (isNaN(x)) {
        this.form.width = this.form.width.substring(0, this.form.width.length - 1)
        if (isNaN(this.form.width)) {
          this.form.width = ''
        }
      }
    },
    height(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入毫米为单位的整数')
        return
      }
      if (isNaN(x)) {
        this.form.height = this.form.height.substring(0, this.form.height.length - 1)
        if (isNaN(this.form.height)) {
          this.form.height = ''
        }
      }
    },
    paperLength(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入整数')
        this.form.paperLength = ''
        return
      }
      if (isNaN(x)) {
        this.form.paperLength = this.form.paperLength.substring(0, this.form.paperLength.length - 1)
        if (isNaN(this.form.paperLength)) {
          this.form.paperLength = ''
        }
      }
    },
    paperWidth(x) {
      if (!(x % 1 === 0)) {
        this.$message.info('请输入毫米为单位的整数')
        this.form.paperWidth = ''
        return
      }
      if (isNaN(x)) {
        this.form.paperWidth = this.form.paperWidth.substring(0, this.form.paperWidth.length - 1)
        if (isNaN(this.form.paperWidth)) {
          this.form.paperWidth = ''
        }
      }
    },
    fabric(x) {
      if (isNaN(x)) {
        this.form.fabric = this.form.fabric.substring(0, this.form.fabric.length - 1)
        if (isNaN(this.form.fabric)) {
          this.form.fabric = ''
        }
      }
    },
    totalArea(x) {
      if (isNaN(x)) {
        this.form.totalArea = this.form.totalArea.substring(0, this.form.totalArea.length - 1)
        if (isNaN(this.form.totalArea)) {
          this.form.totalArea = ''
        }
      }
    },
    paperArea(x) {
      if (isNaN(x)) {
        this.form.paperArea = this.form.paperArea.substring(0, this.form.paperArea.length - 1)
        if (isNaN(this.form.paperArea)) {
          this.form.paperArea = ''
        }
      }
    }
  }

}
</script>

<style scoped>

</style>
