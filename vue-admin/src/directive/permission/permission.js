import store from '@/store'

export default {
  inserted(el, binding, vnode) {
    const { value } = binding
    const permissions = store.getters && store.getters.permissions

    if (value && value instanceof Array && value.length > 0) {
      const permission = value

      const hasPermission = permissions.some(button => {
        return permission.includes(button)
      })

      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`初始化错误! 参考 v-permission="['admin','editor']"`)
    }
  }
}
