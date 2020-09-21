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

export function pagerSelect() {
  return request({ url: '/supplier/cardboard/quotation/pagerSelect', method: 'get' })
}

export function supplierSelect() {
  return request({ url: '/supplier/cardboard/quotation/supplierSelect', method: 'get' })
}

export function materialSelect() {
  return request({ url: '/supplier/cardboard/quotation/materialSelect', method: 'get' })
}

export function ridgeTypeSelect() {
  return request({ url: '/supplier/cardboard/quotation/ridgeTypeSelect', method: 'get' })
}

export function customerSelect() {
  return request({ url: '/supplier/cardboard/quotation/customerSelect', method: 'get' })
}

