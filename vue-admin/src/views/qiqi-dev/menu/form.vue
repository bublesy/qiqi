<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" :before-close="cancel" :title="isAdd ? '新增菜单' : '编辑菜单'" width="580px">
    <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" :show-message="false" label-width="90px">
      <el-form-item label="菜单类型">
        <el-radio-group v-model="form.type" size="mini" style="width: 440px" @change="typeChange">
          <el-radio-button label="1">目录</el-radio-button>
          <el-radio-button label="2">菜单</el-radio-button>
          <el-radio-button label="3">按钮</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="form.type.toString() !== 3" label="菜单图标" prop="icon">
        <el-popover
          placement="bottom-start"
          width="440"
          trigger="click"
          @show="$refs['iconSelect'].reset()"
        >
          <IconSelect ref="iconSelect" @selected="selected" />
          <el-input slot="reference" v-model="form.icon" style="width: 440px;" placeholder="点击选择图标" readonly>
            <svg-icon v-if="form.icon" slot="prefix" :icon-class="form.icon" class="el-input__icon" style="height: 32px;width: 16px;" />
            <i v-else slot="prefix" class="el-icon-search el-input__icon" />
          </el-input>
        </el-popover>
      </el-form-item>
      <el-form-item v-if="form.type !== 3" label="菜单缓存" prop="cache">
        <el-radio-group v-model="form.cache" size="mini">
          <el-radio-button label="true">是</el-radio-button>
          <el-radio-button label="false">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="form.type !== 3" label="菜单可见" prop="hidden">
        <el-radio-group v-model="form.hidden" size="mini">
          <el-radio-button label="false">是</el-radio-button>
          <el-radio-button label="true">否</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="标题" prop="title" required>
        <el-input v-model="form.title" :style=" 'width: 168px'" placeholder="菜单标题" />
      </el-form-item>
      <el-form-item v-if="form.type !== 3" label="路由地址" prop="path" required>
        <el-input v-model="form.path" placeholder="路由地址" style="width: 168px;" />
      </el-form-item>
      <el-form-item v-if="form.type !== 1 " label="编码" prop="code" required>
        <el-input v-model="form.code" style="width: 168px;" :placeholder="form.type === '按钮'?'按钮编码':'匹配组件内Name字段'" />
      </el-form-item>
      <el-form-item v-if="form.type === 1" label="重定向" prop="redirect">
        <el-input v-model="form.redirect" style="width: 168px;" placeholder="点击面包屑跳转地址" />
      </el-form-item>
      <el-form-item v-if="form.type !== 3" label="菜单排序" prop="sort">
        <el-input-number v-model.number="form.sort" :min="0" :max="999" controls-position="right" style="width: 168px;" />
      </el-form-item>

      <el-form-item v-if="form.type === 2" label="组件路径" prop="component" required>
        <el-input v-model="form.component" placeholder="组件路径" />
      </el-form-item>
      <el-form-item label="上级类目" prop="parentId">
        <el-cascader
          v-model="form.parentId"
          :show-all-levels="false"
          :options="menus"
          :props="{ checkStrictly: true,value:'id',label:'title',emitPath:false }"
          clearable
        />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getMenuOption, addMenu, editMenu } from '@/api/qiqi-dev/menu'
import IconSelect from '@/components/IconSelect'
export default {
  components: { IconSelect },
  props: {
    isAdd: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      loading: false,
      dialog: false,
      menus: null,
      form: { type: 1, icon: '', cache: false, hidden: false, title: '', path: '', redirect: 'noRedirect', sort: 999, parentId: 0 },
      apiGetPath: [], apiPutPath: [], apiPostPath: [], apiDeletePath: [],
      rules: {
      }
    }
  },
  mounted() {
    this.getMenus()
  },
  methods: {
    typeChange(type) {
      switch (type) {
        case '1':
          this.form = { type: 1, icon: '', cache: false, hidden: false, title: '', path: '', redirect: 'noRedirect', sort: 999, parentId: 0 }
          break
        case '2':
          this.form = { type: 2, icon: '', cache: false, hidden: false, title: '', path: '', code: '', component: '', sort: 999, parentId: 0 }
          break
        case '3':
          this.form = { type: 3, title: '', code: '', parentId: 0 }
          break
      }
      this.clearApiPath()

      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    addApiPath(method) {
      switch (method) {
        case 'get':
          this.apiGetPath.push('')
          break
        case 'put':
          this.apiPutPath.push('')
          break
        case 'post':
          this.apiPostPath.push('')
          break
        case 'delete':
          this.apiDeletePath.push('')
          break
      }
    },
    removeApiPath(index, method) {
      switch (method) {
        case 'get':
          this.apiGetPath.splice(index, 1)
          break
        case 'put':
          this.apiPutPath.splice(index, 1)
          break
        case 'post':
          this.apiPostPath.splice(index, 1)
          break
        case 'delete':
          this.apiDeletePath.splice(index, 1)
          break
      }
    },
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.loading = true
          if (this.form.parentId === null) {
            this.form.parentId = 0
          }

          if (this.isAdd) {
            this.doAdd()
          } else {
            this.doEdit()
          }
        } else {
          return false
        }
      })
    },
    doAdd() {
      addMenu(this.form).then(res => {
        this.resetForm()
        this.$notify({ title: '添加成功', type: 'success', duration: 2500 })
        this.$parent.loadData()
      }).finally(() => {
        this.loading = false
      })
    },
    doEdit() {
      editMenu(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.$parent.loadData()
      }).finally(() => {
        this.loading = false
      })
    },
    resetForm() {
      this.dialog = false
      this.form = { type: 1, icon: '', cache: false, hidden: false, title: '', path: '', redirect: 'noRedirect', sort: 999, parentId: 0 }

      this.clearApiPath()
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    },
    selected(name) {
      this.form.icon = name
    },
    async getMenus() {
      getMenuOption().then(data => {
        this.menus = data
      })
    },
    clearApiPath() {
      this.apiGetPath = []
      this.apiPutPath = []
      this.apiPostPath = []
      this.apiDeletePath = []
    }
  }
}
</script>
