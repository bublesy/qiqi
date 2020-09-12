import request from '@/utils/request'

export function getDept(data) {
  return request({ url: '', method: 'post', data: data })
}

export function delDept(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateDept(data) {
  return request({ url: '', method: 'post', data: data })
}

