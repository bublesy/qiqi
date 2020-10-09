import request from '@/utils/request'

export function add(data) {
  return request({ url: '/end/product/warehouse/add', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/end/product/warehouse/list', method: 'get', params: params })
}

export function endList(data) {
  return request({ url: '/end/product/warehouse/endList', method: 'post', data: data })
}

export function noList(params) {
  return request({ url: '/end/product/warehouse/noList', method: 'get', params: params })
}

export function listByIds(params) {
  return request({ url: '/end/product/warehouse/listByIds', method: 'post', params: params })
}

export function removeById(id) {
  return request({ url: '/end/product/warehouse/' + id, method: 'delete' })
}

export function getById(id) {
  return request({ url: '/end/product/warehouse/' + id, method: 'get' })
}

export function getNameById(id) {
  return request({ url: '/purchase/order/getNameById/' + id, method: 'get' })
}

export function updateState(data) {
  return request({ url: '/end/product/warehouse/updateState', method: 'post', data: data })
}

export function updatePosting(data) {
  return request({ url: '/end/product/warehouse/updatePosting', method: 'post', data: data })
}

export function upState(data) {
  return request({ url: '/warehouse/upState', method: 'post', data: data })
}

export function check(data) {
  return request({ url: '/end/product/warehouse/check', method: 'post', data: data })
}

export function getDeliveryList(id) {
  return request({ url: '/delivery/note/getDeliveryList' + id, method: 'get' })
}
