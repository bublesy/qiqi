import request from '@/utils/request'

export function getShipDaily(data) {
  return request({ url: '/delivery/note', method: 'get', params: data })
}

export function delShipDaily(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateShipDaily(data) {
  return request({ url: '', method: 'post', data: data })
}

export function updateSign(data) {
  return request({ url: '/delivery/note/sign', method: 'post', data: data })
}

