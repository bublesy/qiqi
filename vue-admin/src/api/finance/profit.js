import request from '@/utils/request'

export function getRoleSelector() {
  return request({ url: '/dev/role/selector', method: 'get' })
}

export function getRoles(param) {
  return request({ url: '/dev/role', params: param, method: 'get' })
}

export function getRole(id) {
  return request({ url: '/dev/role/' + id, method: 'get' })
}

export function addRole(data) {
  return request({ url: '/dev/role', data: data, method: 'post' })
}

export function editRole(data) {
  return request({ url: '/dev/role', data: data, method: 'put' })
}

export function removeRole(id) {
  return request({ url: '/dev/role/' + id, method: 'delete' })
}

export function editRoleMenus(id, data) {
  return request({ url: '/dev/role/' + id + '/menu', data: data, method: 'put' })
}

// 毛利估算数据
export function mlist(data) {
  return request({ url: '/purchase/order/list', method: 'get', params: data })
}
// 毛利估算保存
export function updated(data) {
  return request({ url: '/purchase/order', method: 'put', data: data })
}
