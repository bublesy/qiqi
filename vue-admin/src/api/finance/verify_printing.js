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

export function getCustomerById(id) {
  return request({ url: '/customer/information/' + id, method: 'get' })
}
