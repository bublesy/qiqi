<template>
  <div>
    {{ my }}
    <el-button type="primary" @click="changeText">修改</el-button>
    <el-button type="primary" @click="closeTag">关闭</el-button>
    <el-button type="primary" @click="reloadTag">重载</el-button>
    <el-button type="primary" @click="refreshTag">刷新</el-button>
  </div>
</template>

<script>
export default {
  name: 'TagMethods',
  inject: ['closeTag', 'reloadTag', 'refreshTag'],
  data() {
    return {
      my: '这是测试文本'
    }
  },
  methods: {
    changeText() {
      this.my = '这是修改后的文本'
      this.reloadTag()
    }
  }

}
</script>

<style>

</style>
