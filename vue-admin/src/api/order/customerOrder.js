import request from '@/utils/request'

export function getOrder(data) {
  return request({ url: '/order/list', method: 'post', data: data })
}

export function getSingleOrder(id) {
  return request({ url: '/order/' + id, method: 'get' })
}

export function delOrder(id) {
  return request({ url: '/order/' + id, method: 'delete' })
}

export function addOrUpdateOrder(data) {
  return request({ url: '/order', method: 'post', data: data })
}

/** 获取供应商 */
export function getSupplier() {
  return request({ url: '/order/supplier', method: 'get' })
}

/** 材质 */
export function getMaterial() {
  return request({ url: '/texture/of/material/list', method: 'get' })
}

/** 获取结合*/
export function getUnite() {
  return request({ url: '/unite/list', method: 'get' })
}

/** 获取颜色 */
export function getColor() {
  return request({ url: '/colour/list', method: 'get' })
}

/** 组合*/
export function getCombination() {
  return request({ url: '/combination/list', method: 'get' })
}

/** 钉类*/
export function getNails() {
  return request({ url: '/nails/list', method: 'get' })
}

/** 印刷版面 */
export function getPrintLayout() {
  return request({ url: '/print/layout/list', method: 'get' })
}

