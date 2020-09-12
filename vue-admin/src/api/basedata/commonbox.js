import request from '@/utils/request'

export function getCommonBox(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delCommonBox(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateCommonBox(data) {
  return request({ url: '', method: 'post', data: data })
}

