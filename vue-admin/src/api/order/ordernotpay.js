import request from '@/utils/request'

export function getOrderNotPay(data) {
  return request({ url: '/notPay/list', method: 'post', data: data })
}

export function delOrderNotPay(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateOrderNotPay(data) {
  return request({ url: '', method: 'post', data: data })
}

