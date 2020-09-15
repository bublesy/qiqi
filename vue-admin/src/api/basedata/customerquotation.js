import request from '@/utils/request'

export function getCustomerQuotation(data) {
  return request({ url: '/customer/quotation/list', method: 'post', data: data })
}

export function delCustomerQuotation(id) {
  return request({ url: '/customer/quotation/' + id, method: 'delete' })
}

export function addOrUpdateCustomerQuotation(data) {
  return request({ url: '/customer/quotation', method: 'post', data: data })
}

export function getSingleCustomerQuotation(id) {
  return request({ url: '/customer/quotation/' + id, method: 'get' })
}

