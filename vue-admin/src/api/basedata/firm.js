import request from '@/utils/request'

export function add(data) {
  return request({ url: '/vendor', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/vendor', method: 'get', data: data })
}

export function removeById(id) {
  return request({ url: '/vendor/' + id, method: 'delete' })
}

export function updated(data) {
  return request({ url: '/vendor', method: 'put', data: data })
}

export function getById(id) {
  return request({ url: '/vendor/' + id, method: 'get' })
}

export function updateById(data) {
  return request({ url: '/vendor/', data: data, method: 'put' })
}
