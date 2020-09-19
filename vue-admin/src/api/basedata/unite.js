import request from '@/utils/request'

export function add(data) {
  return request({ url: '/unite', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/unite/all', method: 'post', data: data })
}

export function removeById(id) {
  return request({ url: '/unite/' + id, method: 'delete' })
}

export function getById(data) {
  return request({ url: '/texture/of/material', data: data, method: 'put' })
}

export function id(id) {
  return request({ url: '/texture/of/material/' + id, method: 'get' })
}
