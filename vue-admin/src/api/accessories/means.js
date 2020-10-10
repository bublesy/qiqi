import request from '@/utils/request'

export function add(data) {
  return request({ url: '/material/data', data: data, method: 'post' })
}

export function list(data) {
  return request({ url: '/material/data/list', method: 'post', data: data })
}

export function specificationList(data) {
  return request({ url: '/material/data/specificationList', method: 'get', data: data })
}

export function removeById(id) {
  return request({ url: '/material/data/' + id, method: 'delete' })
}

export function updated(data) {
  return request({ url: '/material/data', method: 'put', data: data })
}

export function getById(id) {
  return request({ url: '/material/data/' + id, method: 'get' })
}

// 领料人
export function getUser() {
  return request({ url: '/sys/user/salesman', method: 'get' })
}

// 对账明细表
export function record(data) {
  return request({ url: '/bill', method: 'post', data: data })
}
// 客户数据
export function customer() {
  return request({ url: '/order/customer', method: 'get' })
}

// 过账
export function toPost(data) {
  return request({ url: '/customer/detail/post', method: 'get', params: data })
}

export function getPost(id) {
  return request({ url: '/customer/detail/' + id, method: 'get' })
}

export function toSettle(data) {
  return request({ url: '/customer/detail/settlement', method: 'get', params: data })
}
// 客户订单总数
export function getOrder() {
  return request({ url: '/home/orders', method: 'get' })
}
// 客户总数
export function get() {
  return request({ url: '/home/customerTotal', method: 'get' })
}
// 较昨日新增
export function added() {
  return request({ url: '/home/add', method: 'get' })
}
// 订单总金额
export function amount(param) {
  return request({ url: '/home/orderMoneyTotal', params: param, method: 'get' })
}
// 订单已付款
export function paid(param) {
  return request({ url: '/home/orderPayed', params: param, method: 'get' })
}
// 订单未付款
export function unpaid(param) {
  return request({ url: '/home/orderUnPayed', params: param, method: 'get' })
}
// 订单总数
export function orders(param) {
  return request({ url: '/home/orderTotal', params: param, method: 'get' })
}
// 营业额

// 供应商已结未结
export function purchase(params) {
  return request({ url: '/purchase/order/purList', method: 'get', params: params })
}

// 原料库存
export function warehouseList(params) {
  return request({ url: '/warehouse/list', method: 'get', params: params })
}

// 成品库存
export function endWarehouseList(params) {
  return request({ url: '/end/product/warehouse/list', method: 'get', params: params })
}

// 毛利估算数据
export function mlist(data) {
  return request({ url: '/purchase/order/list', method: 'get', params: data })
}
// 客户结算预警
export function client() {
  return request({ url: '/home/warning', method: 'get' })
}

