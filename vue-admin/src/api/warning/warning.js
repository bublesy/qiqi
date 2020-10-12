import request from '@/utils/request'

export function modify(data) {
  return request({ url: '/warning', data: data, method: 'put' })
}

export function list(data) {
  return request({ url: '/warning', data: data, method: 'get' })
}

