export * from './admin'
import admin from './admin'

import RegisterView from './RegisterView'

export default [{
  path: '/',
  redirect: '/admin'
}, {
  name: 'register',
  path: '/register',
  component: RegisterView
}, {
  path: '/admin',
  component: {
    template: '<router-view></router-view>'
  },
  children: admin
}]
