export default {
  data() {
    return {
      loading: true,
      initMethod: 'loadData',
      pagination: {
        size: 10,
        page: 1,
        total: 0
      },
      queryParams: {}
    }
  },
  methods: {
    init() {
      this.loading = true
      this.queryParams = {}

      this.queryParams.page = this.pagination.page
      this.queryParams.size = this.pagination.size

      this.loadData()
    },
    pageChange(e) {
      this.pagination.page = e
      this.init()
    },
    // 分页长度改变
    sizeChange(e) {
      this.pagination.page = 1
      this.pagination.size = e
      this.init()
    },
    // 查询按钮
    toQuery() {
      this.pagination.page = 1
      this.init()
    },
    // 删除最后一页（大于1）的最后一条数据时，页数减一
    dleChangePage(isLast) {
      if (this.pagination.page > 1 && isLast) {
        this.pagination.page = this.pagination.page - 1
      }
    }
  }
}
