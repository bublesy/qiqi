import request from '@/utils/request'

export function getCommonBox(data) {
  return request({ url: '/common/box/list', method: 'post', data: data })
}

export function delCommonBox(id) {
  return request({ url: '/common/box/' + id, method: 'delete' })
}

export function addOrUpdateCommonBox(data) {
  return request({ url: '/common/box', method: 'post', data: data })
}

export function getSingleCommBox(id) {
  return request({ url: '/common/box/' + id, method: 'get' })
}

