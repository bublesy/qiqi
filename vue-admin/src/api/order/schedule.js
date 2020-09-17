import request from '@/utils/request'

export function getSchedule(data) {
  return request({ url: '/schedule/list', method: 'post', data: data })
}

export function delSchedule(id) {
  return request({ url: '/schedule/' + id, method: 'delete' })
}

export function addOrUpdateSchedule(data) {
  return request({ url: '/schedule', method: 'post', data: data })
}

export function getSingleSchedule(id) {
  return request({ url: '/schedule/' + id, method: 'get' })
}

