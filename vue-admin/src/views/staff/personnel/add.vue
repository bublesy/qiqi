<template>
  <el-dialog
    title="人事管理"
    :visible.sync="dialog.show"
    width="700px"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini" :inline="true">
      <el-form-item label="工号:" prop="no">
        <el-input-number v-model="form.no" :controls="false" />
      </el-form-item>
      <el-form-item label="姓名:" prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="性别:">
        <el-radio v-model="form.sex" :label="true">男</el-radio>
        <el-radio v-model="form.sex" :label="false">女</el-radio>
      </el-form-item>
      <el-form-item label="是否在职:" style="margin-left:90px">
        <el-radio v-model="form.onJob" :label="true">在职</el-radio>
        <el-radio v-model="form.onJob" :label="false">离职</el-radio>
      </el-form-item>
      <el-form-item label="部门:">
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in deptOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职位:">
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in positionOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="身份证号:" prop="identity">
        <el-input v-model="form.identity" />
      </el-form-item>
      <el-form-item label="民族:">
        <el-input v-model="form.nation" />
      </el-form-item>
      <el-form-item label="血型:">
        <el-select v-model="form.bloodType" placeholder="请选择">
          <el-option
            v-for="item in bloodTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学历:">
        <el-select v-model="form.education" placeholder="请选择">
          <el-option
            v-for="item in educationOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="出生日期:">
        <el-date-picker
          v-model="form.birth"
          style="width:200px"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="入职日期:">
        <el-date-picker
          v-model="form.entryDate"
          type="date"
          placeholder="选择日期"
        />
      </el-form-item>
      <el-form-item label="身高:">
        <el-input v-model="form.height" />
      </el-form-item>
      <el-form-item label="体重:">
        <el-input v-model="form.weight" />
      </el-form-item>
      <el-form-item label="政治面貌:">
        <el-input v-model="form.political " />
      </el-form-item>
      <el-form-item label="联系电话:" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="户口地址:">
        <el-input v-model="form.address" />
      </el-form-item>
      <el-form-item label="日工资:">
        <el-input v-model="form.dailyRate " style="width:150px" />元
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialog.show = false">取 消</el-button>
      <el-button type="primary" @click="sure">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { addOrUpdatePersonnel } from '@/api/staff/personnel'
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
        sex: true,
        onJob: true
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        no: [
          { required: true, message: '请输入工号', trigger: 'blur' }
        ],
        identity: [
          { required: true, message: '请输入身份证号', trigger: 'blur' }

        ]
      },
      deptOptions: [],
      positionOptions: [],
      bloodTypeOptions: [],
      educationOptions: []
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
    sure() {
      this.$refs.form.validate(x => {
        if (x) {
          var data = {}
          addOrUpdatePersonnel(data).then(res => {

          })
        }
      })
    }
  }
}
</script>

<style>

</style>
