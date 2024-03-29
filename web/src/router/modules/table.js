/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const tableRouter = {
  path: '/table',
  component: Layout,
  redirect: '/table/complex-table',
  name: 'Table',
  meta: {
    title: 'Table',
    icon: 'table'
  },
  children: [
    {
      path: 'dynamic-table',
      component: () => import('@/views/table/dynamic-table/index'),
      name: 'DynamicTable',
      meta: { title: 'Dynamic Table' }
    },
    {
      path: 'drag-table',
      component: () => import('@/views/table/drag-table'),
      name: 'DragTable',
      meta: { title: 'Drag Table' }
    },
    {
      path: 'inline-edit-table',
      component: () => import('@/views/table/inline-edit-table'),
      name: 'InlineEditTable',
      meta: { title: 'it' }
    },
    {
      path: 'sale-table',
      component: () => import('@/views/table/complex-table'),
      name: 'SaleTable',
      meta: { title: 'Satış Tablosu' }
    },
    {
      path: 'customer-table',
      component: () => import('@/views/table/customer-table'),
      name: 'CustomerTable',
      meta: { title: 'Müşteri Tablosu' }
    },
    {
      path: 'invoice-table',
      component: () => import('@/views/table/invoice-table'),
      name: 'InvoiceTable',
      meta: { title: 'Fatura Tablosu' }
    },
    {
      path: 'order-table',
      component: () => import('@/views/table/order-table'),
      name: 'InvoiceTable',
      meta: { title: 'Sipariş Tablosu' }
    },
    {
      path: 'country-table',
      component: () => import('@/views/table/country-table'),
      name: 'CountryTable',
      meta: { title: 'Ülke Tablosu' }
    }
  ]
}
export default tableRouter
