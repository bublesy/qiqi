import request from '@/utils/request'

export function add(data) {
  return request({ url: '/material/in/store', data: data, method: 'post' })
}

export function addMaterial(data) {
  return request({ url: '/material/in/store', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/material/in/store/list', method: 'post', data: data })
}

export function removeById(id) {
  return request({ url: '/material/in/store/' + id, method: 'delete' })
}

export function updated(data) {
  return request({ url: '/material/in/store', method: 'put', data: data })
}

export function getById(id) {
  return request({ url: '/material/in/store/' + id, method: 'get' })
}

export function listunit(data) {
  return request({ url: '/material/data/list', method: 'post', data: data })
}
