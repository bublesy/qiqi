import { constantRoutes } from '@/router'
import Layout from '@/layout'
import _import from '../_import_developer'

const state = {
  routes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.routes = constantRoutes.concat(routes)
  }
}

const httpRouters = []
// 遍历添加子路由
function allRoute(data, source) {
  if (data.length > 0) {
    data.forEach((item, i) => {
      source[i] = {
        path: item.path,
        name: item.code,
        hidden: item.hidden,
        meta: {
          title: item.title,
          noCache: !item.cache
        },
        children: []
      }
      if (item.type === 1) {
        source[i].alwaysShow = true
        source[i].component = _import('/multilevel')
      } else {
        source[i].component = _import(item.component)
      }
      if (item.redirect) {
        source[i].redirect = item.redirect
      }
      if (item.icon && item.icon.trim()) {
        source[i].meta.icon = item.icon
      }
      if (item.children != null && item.children.length > 0) {
        allRoute(item.children, source[i].children)
      }
    })
  }
}

const actions = {
  generateRoutes({ commit }, menus) {
    // 清空路由
    httpRouters.length = 0
    // 返回新路由
    return new Promise(resolve => {
      menus.forEach((item, i) => {
        if (item.type === 1) {
          httpRouters[i] = {
            path: item.path,
            component: Layout,
            redirect: item.redirect,
            alwaysShow: true,
            meta: {
              title: item.title,
              noCache: !item.cache
            },
            children: []
          }
          if (item.icon && item.icon.trim()) {
            httpRouters[i].meta.icon = item.icon
          }
          if (item.children && item.children.length > 0) {
            allRoute(item.children, httpRouters[i].children)
          }
        } else {
          httpRouters[i] = {
            path: '/demo',
            component: Layout,
            children: [
              {
                path: item.path,
                name: item.code,
                hidden: item.hidden,
                component: _import(item.component),
                meta: {
                  title: item.title,
                  noCache: !item.cache
                }
              }
            ]
          }
          if (item.icon && item.icon.trim()) {
            httpRouters[i].children[0].meta.icon = item.icon
          }
        }
      })

      httpRouters.push({ path: '*', redirect: '/404', hidden: true })
      commit('SET_ROUTES', httpRouters)
      resolve(httpRouters)
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
