import request from '@/utils/request'

export function add(data) {
  return request({ url: '/material/data', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/material/data/list', method: 'post', data: data })
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

// 领料人
export function getUser() {
  return request({ url: '/sys/user/salesman', method: 'get' })
}

// 对账明细表
export function record(data) {
  return request({ url: '/bill', method: 'post', data: data })
}
// 客户数据
export function customer() {
  return request({ url: '/order/customer', method: 'get' })
}
