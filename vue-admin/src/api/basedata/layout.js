import request from '@/utils/request'

export function add(data) {
  return request({ url: '/print/layout/', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/print/layout/all', method: 'post', data: data })
}

export function removeById(id) {
  return request({ url: '/print/layout/' + id, method: 'delete' })
}

export function getById(id) {
  return request({ url: '/print/layout/' + id, method: 'get' })
}

export function id(id) {
  return request({ url: '/texture/of/material/' + id, method: 'get' })
}
