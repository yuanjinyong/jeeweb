import admin from './admin'

export default [{
  path: '/',
  redirect: '/admin'
}, {
  name: 'register',
  path: '/register',
  component: r => require.ensure([], () => r(require('./RegisterView')), 'register')
}, {
  path: '/admin',
  component: {
    template: '<router-view></router-view>'
  },
  children: admin
}]
