import request from '@/utils/request'

export function getOrder(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delOrder(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateOrder(data) {
  return request({ url: '', method: 'post', data: data })
}

