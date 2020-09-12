<template>
  <el-container>
    <el-main>
      <el-table
        ref="singleTable"
        :data="stockTable"
        highlight-current-row
        style="width: 100%"
        align="center"
      >
        <el-table-column type="index" width="50" />
        <el-table-column property="material" label="材质" width="120" />
        <el-table-column property="paperLength" label="纸长" width="120" />
        <el-table-column property="paperWidth" label="纸宽" width="120" />
        <el-table-column property="stock" label="库存" width="120" />
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
    </el-main>
  </el-container>
</template>
<script>
import initData from '@/mixins/initData'

export default {
  name: 'CardboardInventory',
  mixins: [initData],

  data() {
    return {
      stockTable: []
    }
  },

  methods: {

  }
}
</script>
