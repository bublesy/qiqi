import request from '@/utils/request'

export function add(data) {
  return request({ url: '/supplier/cardboard/quotation/add', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/supplier/cardboard/quotation/list', method: 'get', params: params })
}

export function removeById(id) {
  return request({ url: '/supplier/cardboard/quotation/' + id, method: 'delete' })
}

export function getById(id) {
  return request({ url: '/supplier/cardboard/quotation/' + id, method: 'get' })
}

export function supplierSelect() {
  return request({ url: '/supplier/cardboard/quotation/supplierSelect', method: 'get' })
}

