import request from '@/utils/request'

export function add(data) {
  return request({ url: '/paperboard/data/setting/add', data: data, method: 'post' })
}

export function list(params) {
  return request({ url: '/paperboard/data/setting/list', method: 'get', params: params })
}

export function removeById(id) {
  return request({ url: '/paperboard/data/setting/' + id, method: 'delete' })
}

export function getById(id) {
  return request({ url: '/paperboard/data/setting/' + id, method: 'get' })
}

