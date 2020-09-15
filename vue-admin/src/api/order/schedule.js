import request from '@/utils/request'

export function getSchedule(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delSchedule(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateSchedule(data) {
  return request({ url: '', method: 'post', data: data })
}

