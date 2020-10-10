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

export function getWarDate(params) {
  return request({ url: '/warehouse/getWarDate', method: 'get', params: params })
}

export function getEndWarDate(params) {
  return request({ url: '/end/product/warehouse/getEndWarDate', method: 'get', params: params })
}

export function getSupplierSettData(params) {
  return request({ url: '/purchase/order/getSupplierSettData', method: 'get', params: params })
}

export function getEndDisData(params) {
  return request({ url: '/end/product/warehouse/getEndDisData', method: 'get', params: params })
}

