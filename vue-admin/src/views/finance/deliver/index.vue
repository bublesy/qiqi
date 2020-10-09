<template>
  <div class="app-container">
    <div class="box">
      <div class="top">
        <!-- <el-tooltip class="item" effect="dark" content="单击下表一行数据无误，后选择过账" placement="top">
          <el-button type="primary" @click="batchPosting()">批量过账</el-button>
        </el-tooltip> -->
      </div>
    </div>
    <el-dialog
      title="确认过账"
      :visible.sync="dialogVisible"
      width="30%"
      :close-on-click-modal="false"
    >
      <span>当前过账单号{{ oNo }},过账年月 {{ dateFormat(new Date()) }}，确定过账吗?
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="postingOk()">确 定</el-button>
      </span>
    </el-dialog>
    <el-form ref="form" :model="form" label-width="80px" inline>
      <el-form-item label="回签状态:">
        <el-select v-model="form.signs" placeholder="请选择回签状态" :clearable="true">
          <el-option label="已回签" value="已回签" />
          <el-option label="未回签" value="未回签" />
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称:">
        <el-input v-model="form.name" />
      </el-form-item>
    </el-form>
    <el-button type="primary" @click="loadData ()">查询</el-button>
    <el-table
      :data="tableData"
      border
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <!-- <el-table-column type="selection" width="55" /> -->
      <el-table-column prop="name" label="客户名称" width="180" />
      <el-table-column prop="outNo" label="出货单号" />
      <el-table-column prop="material" label="材质" />
      <el-table-column prop="boxType" label="箱型" />
      <el-table-column prop="sendNum" label="出货数量" />
      <el-table-column prop="orderNum" label="订单数量" />
      <el-table-column prop="perPrice" label="单价" />
      <el-table-column prop="money" label="金额" />
      <el-table-column prop="unit" label="单位" />
      <el-table-column prop="signs" label="回签状态" />
      <el-table-column prop="settlement" label="结算状态" />
      <el-table-column prop="remark" label="备注" />
      <!-- <el-table-column label="操作">
        <template slot-scope="scope">
          <el-link type="primary" size="small" @click="posting(scope)">过账</el-link>
        </template>
      </el-table-column> -->
    </el-table>
    <el-pagination
      style="margin-top: 10px"
      :total="pagination.total"
      :current-page="pagination.page"
      layout="total, prev, pager, next, sizes"
      @size-change="sizeChange"
      @current-change="pageChange"
    />
  </div>
</template>

<script>
// import { endList } from '@/api/end-product/product'
// import { noList } from '@/api/end-product/product'
// import { getUser } from '@/api/accessories/means'
import { updatePosting } from '@/api/end-product/product'
// import { wareList } from '@/api/warehouse/warehouse'
import { getList } from '@/api/end-product/product'
import initData from '@/mixins/initData'

export default window.$crudCommon({
  name: 'Deliver',
  mixins: [initData],
  data() {
    return {
      a: [],
      // 控制弹出框的显示于隐藏
      dialogVisible: false,
      input: '',
      radio: '1',
      tableData: [],
      outNoFor: [],
      deliverymanFor: [],
      form: {
        signs: '',
        page: '1',
        size: '10',
        name: ''
      },
      oNo: '',
      ids: [],
      multipleSelection: []
    }
  },
  created() {
    this.loadData()
  },
  methods: {
    batchPosting() {
      console.log(this.multipleSelection)
      if (this.multipleSelection.length === 0) {
        this.$message.error('请选择过账的内容！！！')
        return
      } else {
        this.ids = []
        this.multipleSelection.forEach(a => {
          this.ids.push(a.eid)
        })
        updatePosting(this.ids).then(res => {
          if (res) {
            this.$message.success('成功')
            this.loadData()
          } else {
            this.$message.error('失败')
          }
          this.dialogVisible = false
        })
      }
    },
    postingOk() {
      updatePosting(this.ids).then(res => {
        if (res) {
          this.$message.success('成功')
          this.loadData()
        } else {
          this.$message.error('失败')
        }
        this.dialogVisible = false
      })
    },
    posting(scope) {
      this.dialogVisible = true
      this.oNo = scope.row.outNo
      this.ids = []
      this.ids.push(scope.row.eid)
    },
    dateFormat: function(time) {
      var date = new Date(time)
      var year = date.getFullYear()
      /* 在日期格式中，月份是从0开始的，因此要加0
     * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
     * */
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      // 拼接
      return year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
    },
    loadData() {
      this.form.page = this.pagination.page
      this.form.count = this.pagination.size
      getList(this.form).then(res => {
        console.log(res)
        this.tableData = res.list
        this.tableData.forEach(a => {
          console.log(a.signs)
          if (a.signs === null) {
            // a.signs === '未回签'
            this.$set(a, 'signs', '未回签')
            console.log(a.signs)
          }
        })
        this.pagination.total - res.total
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
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
