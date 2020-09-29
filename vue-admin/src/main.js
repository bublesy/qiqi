import Vue from 'vue'

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
// 时间插件
import moment from 'moment'
Vue.prototype.$moment = moment

Vue.use(ElementUI)
import Avue from '@smallwei/avue'
import '@smallwei/avue/lib/index.css'
Vue.use(Avue)
Vue.use(Print)

import crudCommon from '@/mixins/crud'
window.$crudCommon = crudCommon

Vue.config.productionTip = false

Vue.prototype.baseURL = process.env.VUE_APP_BASE_API

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
