import request from '@/utils/request'

export function getMenus() {
  return request({ url: '/dev/menu', method: 'get' })
}

export function addMenu(data) {
  return request({ url: '/dev/menu', data: data, method: 'post' })
}

export function editMenu(data) {
  return request({ url: '/dev/menu', data: data, method: 'put' })
}

export function removeMenu(id) {
  return request({ url: '/dev/menu/' + id, method: 'delete' })
}

export function getMenuOption() {
  return request({ url: '/dev/menu/tree', method: 'get' })
}
