import request from '@/utils/request'
import moment from 'moment'
import id from "element-ui/src/locale/lang/id";
import qs from 'qs';

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

export function fetchSaleSearchList(data) {
   return request({
     url: 'http://localhost:8011/sale/search?token=token',
     method: 'get',
     params: {
      moneyType: data.moneyType,
      orderDate: moment(String(data.orderDate)).format('DD-MM-YYYY')
    },
    paramsSerializer: (params) => {
      return qs.stringify(params, { arrayFormat: 'repeat' })
    },
   })
 
}
 
export function createSale(data) {
  return request({
    url: 'http://localhost:8011/sale/add?token=token',
    method: 'post',
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

export function deleteSale(data) {
//  axios.delete('https://my-json-server.typicode.com/json/posts/' + id);
  request({
    url: 'http://localhost:8011/sale/delete?token=token',
    method: 'delete',
    data
  })
}

export function exportSaleExcel(data) {
  return request({
      url: 'http://localhost:8011/sale/exportExcel?token=token',
      method: 'post',
      responseType: 'blob',
      data
    })
}

export function exportSaleExcelByParameters(data) {
  return request({
      url: 'http://localhost:8011/sale/exportExcelByParameters?token=token',
      method: 'get',
      responseType: 'blob',
      params: {
        moneyType: data.moneyType
      },
      paramsSerializer: (params) => {
        return qs.stringify(params, { arrayFormat: 'repeat' })
      }
    })
}

export function fetchCustomerList() {
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
     url: 'http://localhost:8011/customer?token=token',
     method: 'get'
   })
 }
 
export function fetchCustomerSearchList(data) {
  return request({
    url: 'http://localhost:8011/customer/search?token=token',
    method: 'get',
    params: {
      name: data.name,
      surname: data.surname,
      address: data.address
    },
    paramsSerializer: (params) => {
      return qs.stringify(params, { arrayFormat: 'repeat' })
    },
  })
}

export function createCustomer(data) {
  return request({
    url: 'http://localhost:8011/customer/add?token=token',
    method: 'post',
    data
  })
}

export function updateCustomer(data) {
  request({
    url: 'http://localhost:8011/customer/update?token=token',
    method: 'post',
    data
  })
}

export function deleteCustomer(data) {
//  axios.delete('https://my-json-server.typicode.com/json/posts/' + id);
  request({
    url: 'http://localhost:8011/customer/delete?token=token',
    method: 'delete',
    data
  })
}

export function exportCustomerExcel(data) {
  return request({
      url: 'http://localhost:8011/customer/exportExcel?token=token',
      method: 'get',
      params: {
        name: data.name,
        surname: data.surname,
        address: data.address
      },
      responseType: 'blob',
    })
}

export function exportCustomerExcelByParameters(data) {
  return request({
      url: 'http://localhost:8011/customer/exportExcelByParameters?token=token',
      method: 'get',
      responseType: 'blob',
      params: {
        name: data.name != undefined ? data.name : null,
        surname: data.surname != undefined ? data.surname : null,
        address: data.address != undefined ? data.address : null
      },
      paramsSerializer: (params) => {
        return qs.stringify(params, { arrayFormat: 'repeat' })
      }
    })
}

export function fetchInvoiceList() {
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
    url: 'http://localhost:8011/invoice?token=token',
    method: 'get'
  })
}

export function fetchInvoiceSearchList(data) {
  return request({
    url: 'http://localhost:8011/invoice/search?token=token',
    method: 'get',
    params: {
      moneyType: data.moneyType,
      orderDate: moment(String(data.orderDate)).format('DD-MM-YYYY')
    },
    paramsSerializer: (params) => {
      return qs.stringify(params, { arrayFormat: 'repeat' })
    },
  })

}

export function createInvoice(data) {
  return request({
    url: 'http://localhost:8011/invoice/add?token=token',
    method: 'post',
    data
  })
}

export function updateInvoice(data) {
  request({
    url: 'http://localhost:8011/invoice/update?token=token',
    method: 'post',
    data
  })
}

export function deleteInvoice(data) {
//  axios.delete('https://my-json-server.typicode.com/json/posts/' + id);
  request({
    url: 'http://localhost:8011/invoice/delete?token=token',
    method: 'delete',
    data
  })
}

export function exportInvoiceExcel(data) {
  return request({
      url: 'http://localhost:8011/invoice/exportExcel?token=token',
      method: 'post',
      responseType: 'blob',
      data
    })
}

export function exportInvoiceExcelByParameters(data) {
  return request({
      url: 'http://localhost:8011/invoice/exportExcelByParameters?token=token',
      method: 'get',
      responseType: 'blob',
      params: {
        moneyType: data.moneyType
      },
      paramsSerializer: (params) => {
        return qs.stringify(params, { arrayFormat: 'repeat' })
      }
    })
}



export function fetchCountryList() {
    return request({
      url: 'http://localhost:8011/country?token=token',
      method: 'get'
    })
  }
  
  export function fetchCountrySearchList(data) {
    return request({
      url: 'http://localhost:8011/country/search?token=token',
      method: 'get',
      params: {
        code: data.code,
        name: data.name
      },
      paramsSerializer: (params) => {
        return qs.stringify(params, { arrayFormat: 'repeat' })
      },
    })
  
  }
  
  export function createCountry(data) {
    return request({
      url: 'http://localhost:8011/country/add?token=token',
      method: 'post',
      data
    })
  }
  
  export function updateCountry(data) {
    request({
      url: 'http://localhost:8011/country/update?token=token',
      method: 'post',
      data
    })
  }
  
  export function deleteCountry(data) {
  //  axios.delete('https://my-json-server.typicode.com/json/posts/' + id);
    request({
      url: 'http://localhost:8011/country/delete?token=token',
      method: 'delete',
      data
    })
  }
  
  export function exportCountryExcel(data) {
    return request({
        url: 'http://localhost:8011/country/exportExcel?token=token',
        method: 'post',
        responseType: 'blob',
        data
      })
  }
  
  export function exportCountryExcelByParameters(data) {
    return request({
        url: 'http://localhost:8011/country/exportExcelByParameters?token=token',
        method: 'get',
        responseType: 'blob',
        params: {
          code: data.code != undefined ? data.code : null,
          name: data.name != undefined ? data.name : null
        },
        paramsSerializer: (params) => {
          return qs.stringify(params, { arrayFormat: 'repeat' })
        }
      })
  }


  export function fetchOrderList() {
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
        url: 'http://localhost:8011/order?token=token',
        method: 'get'
      })
    }
    
    export function fetchOrderSearchList(data) {
      return request({
        url: 'http://localhost:8011/order/search?token=token',
        method: 'get',
        params: {
          moneyType: data.moneyType,
          customer : data.customer,
          saleId : data.saleId,
        //  orderDate: moment(String(data.orderDate)).format('DD-MM-YYYY')
        },
        paramsSerializer: (params) => {
          return qs.stringify(params, { arrayFormat: 'repeat' })
        },
      })
    
    }
    
    export function createOrder(data) {
      return request({
        url: 'http://localhost:8011/order/add?token=token',
        method: 'post',
        data
      })
    }
    
    export function updateOrder(data) {
      request({
        url: 'http://localhost:8011/order/update?token=token',
        method: 'post',
        data
      })
    }
    
    export function deleteOrder(data) {
    //  axios.delete('https://my-json-server.typicode.com/json/posts/' + id);
      request({
        url: 'http://localhost:8011/order/delete?token=token',
        method: 'delete',
        data
      })
    }
    
    export function exportOrderExcel(data) {
      return request({
          url: 'http://localhost:8011/order/exportExcel?token=token',
          method: 'post',
          responseType: 'blob',
          data
        })
    }
    
  