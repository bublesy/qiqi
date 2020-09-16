import request from '@/utils/request'

export function getBoxClass(data) {
  return request({ url: '/box/type/setting/list', method: 'post', data: data })
}

export function delBoxClass(id) {
  return request({ url: '/box/type/setting/' + id, method: 'delete' })
}

export function addOrUpdateBoxClass(data) {
  return request({ url: '/box/type/setting', method: 'post', data: data })
}

export function getSingleBoxClass(id) {
  return request({ url: '/box/type/setting/' + id, method: 'get' })
}

export function getBoxClassList() {
  return request({ url: '/box/type/setting/list', method: 'get' })
}

