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
    }
  ]
}
export default tableRouter
