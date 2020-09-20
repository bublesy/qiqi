import request from '@/utils/request'

export function add(data) {
  return request({ url: '/colour', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/colour/all', method: 'post', data: data })
}

export function removeById(id) {
  return request({ url: '/colour/' + id, method: 'delete' })
}

export function updated(data) {
  return request({ url: '/colour', method: 'put', data: data })
}

export function getById(id) {
  return request({ url: '/colour/' + id, method: 'get' })
}
