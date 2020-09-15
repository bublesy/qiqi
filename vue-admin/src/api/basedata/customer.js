import request from '@/utils/request'

export function getCustomer(data) {
  return request({ url: '/customer/information/list', method: 'post', data: data })
}

export function delCustomer(id) {
  return request({ url: '/customer/information/' + id, method: 'delete' })
}

export function addOrUpdateCustomer(data) {
  return request({ url: '/customer/information', method: 'post', data: data })
}

export function getSingleCustomer(id) {
  return request({ url: '/customer/information/' + id, method: 'get' })
}

export function getSalesman() {
  return request({ url: '/sys/user', method: 'get' })
}

