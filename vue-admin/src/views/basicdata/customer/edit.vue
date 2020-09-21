<template>
  <el-dialog
    title="客户资料"
    :visible.sync="dialog.show"
    width="700px"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" label-width="80px" :rules="rules" size="mini" :inline="true">
      <el-form-item label="编码:" prop="code">
        <el-input v-model="form.code" />
      </el-form-item>
      <el-form-item label="客户简称:" prop="shorts">
        <el-input v-model="form.shorts" />
      </el-form-item>
      <el-form-item label="客户名称:" prop="fullName">
        <el-input v-model="form.fullName" />
      </el-form-item>
      <el-form-item label="电话:" prop="phone">
        <el-input v-model="form.phone" @input="phone" />
      </el-form-item>
      <el-form-item label="手机:" prop="mobilePhone">
        <el-input v-model="form.mobilePhone" @input="mobilePhone" />
      </el-form-item>
      <el-form-item label="传真:">
        <el-input v-model="form.fax" />
      </el-form-item>
      <el-form-item label="联系人:">
        <el-input v-model="form.contact" />
      </el-form-item>
      <el-form-item label="网址:">
        <el-input v-model="form.url" />
      </el-form-item>
      <el-form-item label="地址:" prop="address">
        <el-input v-model="form.address" />
      </el-form-item>
      <el-form-item label="开户银行:">
        <el-input v-model="form.bank" />
      </el-form-item>
      <el-form-item label="指定业务员:">
        <el-select v-model="form.salesman" placeholder="请选择">
          <el-option
            v-for="item in salesmanOptions"
            :key="item.id"
            :label="item.nickname"
            :value="item.nickname"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="银行账号:">
        <el-input v-model="form.account" />
      </el-form-item>
      <el-form-item label="税务登记证号:">
        <el-input v-model="form.registration" />
      </el-form-item>
      <el-form-item label="结算方式:">
        <el-input v-model="form.payment" />
      </el-form-item>
      <el-form-item label="币种:">
        <el-input v-model="form.currency" readonly />
      </el-form-item>
      <el-form-item label="期初应收:">
        <el-input-number v-model="form.beginReceive" :controls="false" :precision="decimal" :min="0.00" />
      </el-form-item>
      <el-form-item label="信用额度:">
        <el-input-number v-model="form.credit" :controls="false" :precision="decimal" :min="0.00" />
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdateCustomer, getSingleCustomer, getSalesman } from '@/api/basedata/customer'
export default {
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
        currency: '人民币'
      },
      rules: {
        code: [
          { required: true, message: '请输入编码', trigger: 'blur' }
        ],
        as: [
          { required: true, message: '请输入简称', trigger: 'blur' }
        ],
        fullName: [
          { required: true, message: '请输入全称', trigger: 'blur' }
        ],
        // phone: [
        //   { required: true, message: '请输入电话', trigger: 'blur' },
        //   { min: 8, max: 8, message: '电话号码为8位', trigger: 'blur' }
        // ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' }
        ],
        mobilePhone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { min: 11, max: 11, message: '手机号码必须为11位', trigger: 'blur' }
        ]
      },
      salesmanOptions: [],
      currencyOptions: [],
      decimal: 2
    }
  },
  watch: {
    'dialog.show': function(val) {
      if (val) {
        if (this.id === '' || this.id === null) {
          this.addForm = Object.assign({}, this.$options.data().addForm)
          // this.$refs.form.resetFields()
          getSalesman().then(res => {
            this.salesmanOptions = res.list
          })
        } else {
          getSingleCustomer(this.id).then(res => {
            getSalesman().then(res => {
              this.salesmanOptions = res.list
            })
            this.form = res
          })
        }
      }
    }
  },
  methods: {
    sure() {
      this.$refs.form.validate(x => {
        if (!x) {
          return
        }
        var data = {}
        data = this.form
        addOrUpdateCustomer(data).then(res => {
          if (res) {
            this.$message.success('保存成功')
            this.$emit('init')
            this.dialog.show = false
          } else {
            this.$message.info('手机号已存在')
          }
        })
        // 业务逻辑
      })
    },
    phone(x) {
      if (isNaN(x)) {
        this.form.phone = this.form.phone.substring(0, this.form.phone.length - 1)
        if (isNaN(this.form.phone)) {
          this.form.phone = ''
        }
      }
    },
    mobilePhone(x) {
      if (x.substring(0, 1) !== '1') {
        this.form.mobilePhone = ''
      }
      if (isNaN(x)) {
        this.form.mobilePhone = this.form.mobilePhone.substring(0, this.form.mobilePhone.length - 1)
        if (isNaN(this.form.mobilePhone)) {
          this.form.mobilePhone = ''
        }
      }
    }
  }
}
</script>

<style scoped>
.el-link{
  color: rgb(0, 162, 255);
  font: 1em sans-serif;
}

</style>
