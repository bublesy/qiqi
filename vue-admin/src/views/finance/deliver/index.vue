<template>
  <div class="app-container">
    <div class="box">
      <div class="top">
        <el-tooltip class="item" effect="dark" content="单击下表一行数据无误，后选择过账" placement="top">
          <el-button type="primary" @click="dialogVisible = true">过账</el-button>
        </el-tooltip>
      </div>
    </div>
    <el-dialog
      title="确认过账"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
      :close-on-click-modal="false"
    >
      <span>当前出货单号【BWLO00079】过账年月【2012年09月】，确定过账吗?
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
    <el-form ref="form" :model="form" label-width="80px" inline>
      <el-form-item label="单据类型:">
        <el-select v-model="form.region" placeholder="请选择单据类型">
          <el-option label="区域一" value="shanghai" />
          <el-option label="区域二" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="回签状态:">
        <el-select v-model="form.region" placeholder="请选择回签状态">
          <el-option label="区域一" value="shanghai" />
          <el-option label="区域二" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="活动时间:">
        <el-col :span="18">
          <el-date-picker v-model="form.date1" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-col>
      </el-form-item>
      <el-form-item label="账款年月:">
        <el-col :span="18">
          <el-date-picker v-model="form.date1" type="date" placeholder="选择日期" style="width: 100%;" />
        </el-col>
      </el-form-item>
    </el-form>
    <el-form ref="form" :model="form" label-width="80px" inline>
      <el-form-item label="出货单号:">
        <el-input v-model="form.user" placeholder="请选择出货单号" />
      </el-form-item>
      <el-form-item label="车号:">
        <el-select v-model="form.region" placeholder="请选择车号">
          <el-option label="区域一" value="shanghai" />
          <el-option label="区域二" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="送货人:">
        <el-select v-model="form.region" placeholder="请选择送货人">
          <el-option label="区域一" value="shanghai" />
          <el-option label="区域二" value="beijing" />
        </el-select>
      </el-form-item>
      <el-form-item label="业务员:">
        <el-select v-model="form.region" placeholder="请选择业务员">
          <el-option label="区域一" value="shanghai" />
          <el-option label="区域二" value="beijing" />
        </el-select>
      </el-form-item>
    </el-form>
    <!-- <div class="bz">
      <el-radio v-model="radio" label="1">备选项</el-radio>
      <el-radio v-model="radio" label="2">备选项</el-radio>
      备注<el-input v-model="input" placeholder="请输入内容" />
    </div> -->
    <div class="xz">
      <el-radio v-model="radio" label="1">备选项</el-radio>
      <el-radio v-model="radio" label="2">作废标记</el-radio>
    </div>
    <div style="float:left;margin-left: 95px;margin-bottom:20px">备注</div>
    <div class="bz">
      <el-input
        v-model="input"
        tooltip-effect="dark"
        size="small"
        style="foalt:left"
        placeholder="请输入内容"
        clearable
        @click="clear"
      />
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      height="600"
      @current-change="handleCurrentChange"
    >
      <el-table-column
        type="selection"
        width="55"
      />
      <el-table-column
        label="#"
        type="index"
        width="50"
      />
      <el-table-column
        prop="name"
        label="客户名称"
        width="180"
      />
      <el-table-column
        prop="name"
        label="任务编号"
        width="180"
      />
      <el-table-column
        prop="address"
        label="款号"
      />
      <el-table-column
        prop="address"
        label="材质"
      /><el-table-column
        prop="address"
        label="箱型"
      /><el-table-column
        prop="address"
        label="长"
      /><el-table-column
        prop="address"
        label="宽"
      />
      <el-table-column
        prop="address"
        label="高"
      />
      <el-table-column
        prop="address"
        label="出货数量"
      />
      <el-table-column
        prop="address"
        label="单价"
      />
      <el-table-column
        prop="address"
        label="金额"
      />
      <el-table-column
        prop="address"
        label="单位"
      />
      <el-table-column
        prop="address"
        label="备注"
      />
    </el-table>
  </div>
</template>

<script>

export default window.$crudCommon({
  name: 'Deliver',
  data() {
    return {
      a: [],
      // 控制弹出框的显示于隐藏
      dialogVisible: false,
      input: '',
      radio: '1',
      tableData: [{
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-08',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-06',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-07',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      },
      {
        date: '2016-05-03',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-04',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-01',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-08',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-06',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        date: '2016-05-07',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }]
    }
  },
  created() {
  },
  methods: {
    clear() {
      console.log(11111)
    },
    handleCurrentChange(val) {
      // this.$router.push('/finance/receivables')
      console.log(555)
    },
    onLoadTable({ page, value, data }, callback) {
      // 首次加载去查询对应的值
      if (value) {
        this.$message.success('首次查询' + value)
        callback({
          id: '0',
          name: '张三',
          sex: '男'
        })
        return
      }
      if (data) {
        this.$message.success('搜索查询参数' + JSON.stringify(data))
      }
      if (page) {
        this.$message.success('分页参数' + JSON.stringify(page))
      }
      // 分页查询信息
      callback({
        total: 2,
        data: [{
          id: '0',
          name: '张三',
          sex: '男'
        }, {
          id: '1',
          name: '李四',
          sex: '女'
        }]
      })
    },
    setVal() {
      this.a = [{
        label: '选项1',
        value: 0
      }, {
        label: '选项2',
        value: 1
      }]
    },

    beforeOpen(done, type) {
      this.setVal()
      done()
    },

    // 列表前操作方法
    listBefore() {
    },

    // 列表后操作方法
    listAfter() {
    },

    // 新增前操作方法
    addBefore() {
      // this.form.createUser = 'small'
    },
    // 新增后操作方法
    addAfter() {
    },

    // 修改前操作方法
    updateBefore() {
      // this.form.updateUser = 'small'
    },

    // 修改后操作方法
    updateAfter() {},

    // 删除前操作方法
    delBefore() {},

    // 删除后操作方法
    delAfter() {}
  }
}, {
  name: 'finance/deliver', // 模块名字
  list: 'getRoles', // 列表接口名字
  update: 'editRole', // 更新接口名字
  add: 'addRole', // 新增接口名字
  del: 'removeRole', // 删除接口名字
  rowKey: 'id', // 主键
  pageNumber: 'pageNumber', // 页码
  pageSize: 'pageSize', // 页数
  total: 'total', // 总页数
  data: 'list'// 列表属性
})
</script>
<style lang="scss" scoped>
.xz{
  float: left;
  margin-left: 10px;
  margin-right: 20px;
}
.bz{
  float: left;
  margin-left: 20px;
  margin-top: -10px;
}
</style>
