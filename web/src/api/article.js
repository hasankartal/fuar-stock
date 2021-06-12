import request from '@/utils/request'
import id from "element-ui/src/locale/lang/id";

export function fetchList(query) {
  return request({
    url: '/vue-element-admin/article/list',
    method: 'get',
    params: query
  })
}

export function fetchArticle(id) {
  return request({
    url: '/vue-element-admin/article/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/vue-element-admin/article/pv',
    method: 'get',
    params: { pv }
  })
}

export function createArticle(data) {
  return request({
    url: '/vue-element-admin/article/create',
    method: 'post',
    data
  })
}

export function getSale() {
  request({
    url: 'http://localhost:8011/sale?token=token',
    method: 'get'
  })
}

export function fetchSaleList() {
 /* get('http://localhost:8011/sale?token=token').then(response => {
    //this.total = 3
    this.list = response.data.map(v => {
    v.id = v.id
    v.amount = v.amount
    return v
  })
  setTimeout(() => {
    this.listLoading = false
    }, 1.5 * 1000)
  }) */
  return request({
    url: 'http://localhost:8011/sale?token=token',
    method: 'get'
  })

}

export function createSale(data) {
  request({
    url: 'http://localhost:8011/sale/add?token=token',
    method: 'post',
    data
  })
}

export function deleteSale(data) {
//  axios.delete('https://my-json-server.typicode.com/json/posts/' + id);
  request({
    url: 'http://localhost:8011/sale/delete?token=token',
    method: 'delete',
    data
  })
}

export function getExcelSale(data) {
  //  axios.delete('https://my-json-server.typicode.com/json/posts/' + id);
  return request({
      url: 'http://localhost:8011/sale/excelSale?token=token',
      method: 'post',
      responseType: 'blob',
      data
    })
  }

export function updateSale(data) {
  request({
    url: 'http://localhost:8011/sale/update?token=token',
    method: 'post',
    data
  })
}
