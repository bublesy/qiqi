import request from '@/utils/request'

export function add(data) {
  return request({ url: '/purchase/order/add', data: data, method: 'post' })
}

export function returnAdd(data) {
  return request({ url: '/purchase/order/returnAdd', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/purchase/order/list', method: 'get', params: params })
}

export function purList(params) {
  return request({ url: '/purchase/order/purList', method: 'get', params: params })
}

export function listByIds(params) {
  return request({ url: '/purchase/order/listByIds', method: 'post', params: params })
}

export function removeById(id) {
  return request({ url: '/purchase/order/remove/' + id, method: 'get' })
}

export function getById(id) {
  return request({ url: '/purchase/order/' + id, method: 'get' })
}

export function getNameById(id) {
  return request({ url: '/purchase/order/getNameById/' + id, method: 'get' })
}

export function getNamesById(id) {
  return request({ url: '/purchase/order/getNamesById/' + id, method: 'get' })
}

export function warehousing(data) {
  return request({ url: '/purchase/order/warehousing', method: 'post', data: data })
}

