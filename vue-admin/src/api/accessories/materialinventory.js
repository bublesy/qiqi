import request from '@/utils/request'

export function add(data) {
  return request({ url: '/material/data', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/material/inventory/list', method: 'get', params: params })
}

export function specificationList(data) {
  return request({ url: '/material/data/specificationList', method: 'get', data: data })
}

export function removeById(id) {
  return request({ url: '/material/data/' + id, method: 'delete' })
}

export function updated(data) {
  return request({ url: '/material/data', method: 'put', data: data })
}

export function getById(id) {
  return request({ url: '/material/data/' + id, method: 'get' })
}
