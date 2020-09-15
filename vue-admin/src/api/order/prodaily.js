import request from '@/utils/request'

export function getProDaily(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delProDaily(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateDaily(data) {
  return request({ url: '', method: 'post', data: data })
}

