<template>
  <div class="app-container">
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd" />
    <el-row :gutter="20">
      <!--用户数据-->
      <el-col>
        <!--工具栏-->
        <div class="head-container">
          <!-- 新增 -->
          <el-button class="filter-item" size="mini" type="primary" icon="el-icon-plus" style="margin-left:0px;" @click="add">新增</el-button>
        </div>
        <!--表格渲染-->
        <el-table v-loading="loading" :data="tableData" size="small" style="width: 100%;">
          <el-table-column prop="username" label="用户名" />
          <el-table-column prop="nickname" label="昵称" />
          <el-table-column :show-overflow-tooltip="true" label="角色">
            <template slot-scope="scope">
              <el-tag v-for="(name, i) in scope.row.roleNames" :key="i" size="medium">{{ name }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="125" align="center" fixed="right">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
              <el-popover
                :ref="scope.row.id"
                placement="top"
                width="180"
              >
                <p>确定删除本条数据吗？</p>
                <div style="text-align: right; margin: 0">
                  <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
                  <el-button :loading="delLoading" type="primary" size="mini" @click="subDelete(scope.row.id)">确定</el-button>
                </div>
                <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini" />
              </el-popover>
            </template>
          </el-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
          style="margin-top: 10px"
          :total="pagination.total"
          :current-page="pagination.page"
          align="center"
          layout="total, prev, pager, next, sizes"
          @size-change="pagination.size"
          @current-change="pageChange"
        />
      </el-col>
    </el-row>
  </div>
</template>

<script>
import initData from '@/mixins/initData'
import { getUsers, removeUser } from '@/api/qiqi-dev/user'
import eForm from './form'
export default {
  name: 'DevUser',
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      deptName: '',
      depts: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      initMethod: 'loadData',
      tableData: [],
      isAdd: false,
      delLoading: false
    }
  },
  created() {
    this.init()
  },
  methods: {
    loadData() {
      getUsers(this.queryParams).then(data => {
        this.tableData = data.list
        this.pagination.total = data.total
      }).finally(() => {
        this.loading = false
      })
    },
    add() {
      this.isAdd = true
      this.$refs.form.dialog = true
    },
    edit(data) {
      this.isAdd = false
      const _this = this.$refs.form
      _this.form = { ...data }
      _this.dialog = true
    },
    subDelete(id) {
      this.delLoading = true
      removeUser(id).then(res => {
        this.loadData()
        this.$notify({
          title: '删除成功',
          type: 'success',
          duration: 2500
        })
      }).finally(() => {
        this.delLoading = false
        this.$refs[id].doClose()
      })
    }
  }
}
</script>

<style scoped>
</style>
