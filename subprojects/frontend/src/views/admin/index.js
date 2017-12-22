import IndexView from './IndexView'
import HomeView from './HomeView'
import LoginView from './LoginView'

import activiti from './activiti'
import diy from './diy'
import platform from './platform'
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
    path: 'diy',
    component: {
      template: '<router-view></router-view>'
    },
    children: diy
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
