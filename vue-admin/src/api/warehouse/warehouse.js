import request from '@/utils/request'

export function add(data) {
  return request({ url: '/warehouse/add', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/warehouse/list', method: 'get', params: params })
}

export function removeById(id) {
  return request({ url: '/warehouse/' + id, method: 'delete' })
}

export function getById(id) {
  return request({ url: '/warehouse/' + id, method: 'get' })
}

export function getSupplierById(id) {
  return request({ url: '/warehouse/' + id, method: 'get' })
}

export function wareList(data) {
  return request({ url: '/warehouse/wareList', method: 'post', data: data })
}

export function check(data) {
  return request({ url: '/warehouse/check', method: 'post', data: data })
}
