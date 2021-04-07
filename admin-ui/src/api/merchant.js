import request from '@/utils/request'

export function listMerchant() {
  return request({
    url: '/merchant/listMerchant',
    method: 'get'
  })
}

export function createMerchant(data) {
  return request({
    url: '/merchant/addMerchant',
    method: 'post',
    data
  })
}

// export function readminAdmin(data) {
//   return request({
//     url: '/admin/readmin',
//     method: 'get',
//     data
//   })
// }

export function updateMerchant(data) {
  return request({
    url: '/merchant/updateMerchant',
    method: 'post',
    data
  })
}

export function deleteMerchant(id) {
  return request({
    url: '/merchant/deleteMerchant/' + id,
    method: 'get'
  })
}
