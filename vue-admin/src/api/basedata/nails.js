import request from '@/utils/request'

export function add(data) {
  return request({ url: '/nails', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/nails/all', method: 'post', data: data })
}

export function removeById(id) {
  return request({ url: '/nails/' + id, method: 'delete' })
}

export function updated(data) {
  return request({ url: '/nails', method: 'put', data: data })
}

export function id(id) {
  return request({ url: '/texture/of/material/' + id, method: 'get' })
}

export function getById(id) {
  return request({ url: '/nails/' + id, method: 'get' })
}
