import request from '@/utils/request'

export function getPosition(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delPosition(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdatePosition(data) {
  return request({ url: '', method: 'post', data: data })
}

