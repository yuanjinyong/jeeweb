import IndexView from './IndexView'
import HomeView from './HomeView'
import LoginView from './LoginView'

export * from './activiti'
import activiti from './activiti'

export * from './platform'
import platform from './platform'

export * from './profile'
import profile from './profile'

export default [{
  path: '',
  component: IndexView,
  children: [{
    path: 'home',
    component: HomeView
  }, {
    path: 'activiti',
    component: {
      template: '<router-view></router-view>'
    },
    children: activiti
  }, {
    path: 'platform',
    component: {
      template: '<router-view></router-view>'
    },
    children: platform
  }, {
    path: 'profile',
    component: {
      template: '<router-view></router-view>'
    },
    children: profile
  }]
}, {
  path: 'login',
  component: LoginView
}]
