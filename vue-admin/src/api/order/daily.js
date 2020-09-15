import request from '@/utils/request'

export function getDaily(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delDaily(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateDaily(data) {
  return request({ url: '', method: 'post', data: data })
}

