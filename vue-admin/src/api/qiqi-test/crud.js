import request from '@/utils/request'

export function getRoles(param) {
  return request({ url: '/dev/role', params: param, method: 'get' })
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
