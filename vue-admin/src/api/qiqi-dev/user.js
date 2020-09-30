import request from '@/utils/request'

export function getUsers(param) {
  return request({ url: '/dev/user', params: param, method: 'get' })
}

export function addUser(data) {
  return request({ url: '/dev/user', data: data, method: 'post' })
}

export function editUser(data) {
  return request({ url: '/dev/user', data: data, method: 'put' })
}

export function removeUser(id) {
  return request({ url: '/dev/user/' + id, method: 'delete' })
}

export function editPwdUser(data) {
  return request({ url: '/dev/user/editPwdUser', data: data, method: 'put' })
}

