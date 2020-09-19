import request from '@/utils/request'

export function add(data) {
  return request({ url: '/purchase/order/add', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/warehouse/list', method: 'get', params: params })
}

export function listByIds(params) {
  return request({ url: '/purchase/order/listByIds', method: 'post', params: params })
}

export function removeById(id) {
  return request({ url: '/purchase/order/' + id, method: 'delete' })
}

export function getById(id) {
  return request({ url: '/purchase/order/' + id, method: 'get' })
}

export function getNameById(id) {
  return request({ url: '/purchase/order/getNameById/' + id, method: 'get' })
}

