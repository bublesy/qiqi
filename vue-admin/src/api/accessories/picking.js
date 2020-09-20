import request from '@/utils/request'

export function add(data) {
  return request({ url: '/material/picking', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/material/picking/list', method: 'post', data: data })
}

export function removeById(id) {
  return request({ url: '/material/picking/' + id, method: 'delete' })
}

export function updated(data) {
  return request({ url: '/material/picking', method: 'put', data: data })
}

export function getById(id) {
  return request({ url: '/material/picking/' + id, method: 'get' })
}

export function listunit(data) {
  return request({ url: '/material/data/list', method: 'post', data: data })
}
