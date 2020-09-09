<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!-- 新增 -->
      <div style="display: inline-block;margin: 0px 2px;">
        <el-button class="filter-item" size="mini" type="primary" icon="el-icon-plus" @click="add">新增</el-button>
      </div>
    </div>
    <!--表单组件-->
    <eForm ref="form" :is-add="isAdd" />
    <!--表格渲染-->
    <el-table
      :data="tableData"
      :tree-props="{children: 'children'}"
      row-key="id"
      size="small"
      class="table-fixed"
    >
      <el-table-column prop="title" :show-overflow-tooltip="true" label="菜单名称" width="120px" />
      <el-table-column prop="type" label="类型" width="80px" />
      <el-table-column prop="icon" label="图标" width="80px">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="80px" />
      <el-table-column :show-overflow-tooltip="true" prop="code" label="编码" />
      <!-- <el-table-column :show-overflow-tooltip="true" prop="path" label="路由地址" /> -->
      <el-table-column :show-overflow-tooltip="true" prop="redirect" label="重定向地址" />
      <el-table-column :show-overflow-tooltip="true" prop="component" label="组件路径" />
      <el-table-column prop="cache" label="缓存" align="center" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.type==='按钮'?'':(scope.row.cache?'是':'否') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="hidden" label="可见" align="center" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.row.type==='按钮'?'':(scope.row.hidden?'否':'是') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createDate" label="创建日期" width="150px" />
      <el-table-column label="操作" width="150px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="edit(scope.row)"
          />
          <el-popconfirm
            title="确定删除吗? 如果存在下级节点则一并删除，此操作不能撤销！"
            @onConfirm="subDelete(scope.row.id)"
          >
            <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini" />
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getMenus, removeMenu } from '@/api/qiqi-dev/menu'
import eForm from './form'
export default {
  name: 'Menu',
  components: { eForm },
  data() {
    return {
      tableData: [],
      isAdd: false,
      delLoading: false
    }
  },
  mounted() {
    this.loadData()
  },
  methods: {
    loadData() {
      getMenus().then(data => {
        this.tableData = data
      })
    },
    add() {
      this.isAdd = true
      this.$refs.form.dialog = true
    },
    edit(data) {
      this.isAdd = false
      const _this = this.$refs.form
      if (data.parentId === '0') {
        _this.isTop = 'true'
      } else {
        _this.isTop = 'false'
      }
      _this.form = { ...data }
      _this.dialog = true
    },
    subDelete(id) {
      this.delLoading = true
      removeMenu(id).then(res => {
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
