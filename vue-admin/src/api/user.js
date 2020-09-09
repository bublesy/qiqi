import request from '@/utils/request'

export function login(data) {
  return request({ url: '/login', method: 'post', data: data })
}

export function getInfo(token) {
  return request({
    url: '/sys/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/sys/logout',
    method: 'post'
  })
}
