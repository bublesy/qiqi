import Vue from 'vue'
import Date from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css
import Print from 'vue-print-nb'

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control

Vue.use(ElementUI)
import Avue from '@smallwei/avue'
import '@smallwei/avue/lib/index.css'
Vue.use(Avue)
Vue.use(Print)

import crudCommon from '@/mixins/crud'
window.$crudCommon = crudCommon

Vue.config.productionTip = false

Date.prototype.dateStr = function() {
  return `${this.getFullYear()}-${this.getMonth() + 1 >= 10 ? (this.getMonth() + 1) : '0' + (this.getMonth() + 1)}-${this.getDate() >= 10 ? this.getDate() : '0' + this.getDate()}
            ${this.getHours() >= 10 ? this.getHours() : '0' + this.getHours()} : ${this.getMinutes() >= 10 ? this.getMinutes() : '0' + this.getMinutes()} : ${this.getSeconds() >= 10 ? this.getSeconds() : '0' + this.getSeconds()}`
}

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
