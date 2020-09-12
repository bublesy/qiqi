import request from '@/utils/request'

export function getCustomer(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delCustomer(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateCustomer(data) {
  return request({ url: '', method: 'post', data: data })
}

