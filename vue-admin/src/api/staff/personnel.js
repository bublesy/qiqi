import request from '@/utils/request'

export function getPersonnel(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delPersonnel(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdatePersonnel(data) {
  return request({ url: '', method: 'post', data: data })
}

