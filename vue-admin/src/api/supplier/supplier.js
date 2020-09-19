import request from '@/utils/request'

export function add(data) {
  return request({ url: '/supplier/add', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/supplier/list', method: 'get', params: params })
}

export function removeById(id) {
  return request({ url: '/supplier/' + id, method: 'delete' })
}

export function getById(id) {
  return request({ url: '/supplier/' + id, method: 'get' })
}

export function getSupplierById(id) {
  return request({ url: '/supplier/' + id, method: 'get' })
}

