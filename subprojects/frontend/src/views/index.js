export * from './admin'
import admin from './admin'

export default [
  {
    path: '/',
    redirect: '/admin'
  },
  {
    path: '/admin',
    component: {
      template: '<router-view></router-view>'
    },
    children: admin
  }
]
