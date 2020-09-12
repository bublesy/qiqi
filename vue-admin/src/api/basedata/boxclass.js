import request from '@/utils/request'

export function getBoxClass(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delBoxClass(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateBoxClass(data) {
  return request({ url: '', method: 'post', data: data })
}

