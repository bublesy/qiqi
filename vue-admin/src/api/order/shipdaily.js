import request from '@/utils/request'

export function getShipDaily(data) {
  return request({ url: '/ship/list', method: 'post', data: data })
}

export function delShipDaily(id) {
  return request({ url: '' + id, method: 'delete' })
}

export function addOrUpdateShipDaily(data) {
  return request({ url: '', method: 'post', data: data })
}
