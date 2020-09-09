<template>
  <div id="app">
    <router-view v-if="isRouterAlive" />
  </div>
</template>

<script>
export default {
  name: 'App',
  provide() {
    return {
      // 重新加载标签页(无感刷新)
      reloadTag: this.reloadTag,
      // 主动刷新标签页(有动画效果)
      refreshTag: this.refreshTag,
      // 关闭当前标签页
      closeTag: this.closeTag
    }
  },
  data() {
    return {
      isRouterAlive: true
    }
  },
  methods: {
    reloadTag() {
      this.isRouterAlive = false
      this.$nextTick(function() {
        this.isRouterAlive = true
      })
    },
    refreshTag() {
      this.$store.dispatch('tagsView/delCachedView', this.$route).then(() => {
        this.$nextTick(() => {
          this.$router.replace({
            path: '/redirect' + this.$route.fullPath
          })
        })
      })
    },
    closeTag() {
      if (!this.$route.meta.affix) {
        this.$store.dispatch('tagsView/delView', this.$route).then(({ visitedViews }) => {
          this.toLastView(visitedViews, this.$route)
        })
      } else {
        this.refreshTag()
      }
    },
    toLastView(visitedViews, view) {
      const latestView = visitedViews.slice(-1)[0]
      if (latestView) {
        this.$router.push(latestView.fullPath)
      } else {
        this.$router.replace({ path: '/redirect/' })
      }
    }
  }
}
</script>
