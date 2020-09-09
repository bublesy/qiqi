<template>
  <div class="app-container">
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd" />
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <div style="display: inline-block;margin: 0px 0px 15px 0;">
        <el-button class="filter-item" size="mini" type="primary" icon="el-icon-plus" @click="add">新增</el-button>
      </div>
    </div>
    <el-row :gutter="15">
      <!--角色管理-->
      <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="17" style="margin-bottom: 10px">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <span class="role-span">角色列表</span>
          </div>
          <el-table v-loading="loading" :data="tableData" highlight-current-row size="small" style="width: 100%;" @current-change="handleCurrentChange">
            <el-table-column prop="name" label="名称" />
            <el-table-column prop="remark" label="描述" />
            <el-table-column prop="code" label="编码" />
            <el-table-column label="操作" align="center" fixed="right">
              <template slot-scope="scope">
                <el-button size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)" />
                <el-popconfirm
                  title="确定删除吗? 如果存在下级节点则一并删除，此操作不能撤销！"
                  @onConfirm="subDelete(scope.row.id)"
                >
                  <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini" />
                </el-popconfirm>
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
        </el-card>
      </el-col>
      <!-- 授权 -->
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="7">
        <el-card class="box-card" shadow="never">
          <div slot="header" class="clearfix">
            <el-tooltip class="item" effect="dark" content="选择指定角色分配菜单" placement="top">
              <span class="role-span">菜单分配</span>
            </el-tooltip>
            <el-button
              :disabled="!showButton"
              :loading="menuLoading"
              icon="el-icon-check"
              size="mini"
              style="float: right; padding: 6px 9px;margin-bottom:-5px;margin-top:-5px;"
              type="primary"
              @click="saveMenu"
            >保存</el-button>
          </div>
          <el-tree
            ref="menu"
            :data="menus"
            :default-checked-keys="menuIds"
            :props="defaultProps"
            default-expand-all
            check-strictly
            accordion
            show-checkbox
            node-key="id"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import initData from '@/mixins/initData'
import { getMenuOption } from '@/api/qiqi-dev/menu'
import { getRole, getRoles, removeRole, editRoleMenus } from '@/api/qiqi-dev/role'
import eForm from './form'
export default {
  name: 'DevRole',
  components: { eForm },
  mixins: [initData],
  data() {
    return {
      queryTable: { name: '' },
      tableData: [],
      isAdd: false,
      defaultProps: {
        children: 'children',
        label: 'title',
        value: 'id'
      },
      initMethod: 'loadData',
      menus: [],
      menuIds: [],
      currentId: 0, menuLoading: false, showButton: false,
      delLoading: false
    }
  },
  mounted() {
    this.init()
    this.getMenus()
  },
  methods: {
    loadData() {
      for (const i in this.queryTable) {
        if (this.queryTable[i]) {
          this.queryParams[i] = this.queryTable[i]
        }
      }
      getRoles(this.queryParams).then(data => {
        this.dleChangePage(data.list.length === 0)
        this.tableData = data.list
        this.pagination.total = data.total
      }).finally(() => {
        this.loading = false
      })
    },
    subDelete(id) {
      removeRole(id).then(res => {
        this.init()
        this.$notify({ title: '删除成功', type: 'success', duration: 2500 })
      })
    },
    getMenus() {
      getMenuOption({ hasButton: true }).then(data => {
        this.menus = data
      })
    },
    handleCurrentChange(val) {
      if (val) {
        // 清空菜单的选中
        this.$refs.menu.setCheckedKeys([])

        // 保存当前的角色id
        this.currentId = val.id

        // 点击后显示按钮
        this.showButton = true

        // 初始化
        this.menuIds = []

        // 菜单数据需要特殊处理
        this.menuIds = val.menuIds
      }
    },
    saveMenu() {
      this.menuLoading = true
      const menus = []

      // 得到已选中的 key 值
      this.$refs.menu.getCheckedKeys().forEach(function(data, index) {
        menus.push(data)
      })
      editRoleMenus(this.currentId, menus).then(res => {
        this.$notify({ title: '保存成功', type: 'success', duration: 2500 })
        this.update()
      }).finally(() => {
        this.menuLoading = false
      })
    },
    update() {
      // 无刷新更新 表格数据
      getRole(this.currentId).then(data => {
        const index = this.tableData.findIndex(function(value, index, arr) {
          return value.id === data.id
        })
        if (index !== -1) {
          this.tableData[index] = data
        }
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
    }
  }
}
</script>
