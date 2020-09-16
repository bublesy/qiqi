import request from '@/utils/request'

export function getOrderNotProduct(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delOrderNotProduct(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateOrderNotProduct(data) {
  return request({ url: '', method: 'post', data: data })
}

