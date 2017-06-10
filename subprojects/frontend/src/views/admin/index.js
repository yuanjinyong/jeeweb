import IndexView from './IndexView'
import HomeView from './HomeView'
import LoginView from './LoginView'

export * from './platform'
import platform from './platform'

export default [
  {
    path: '',
    component: IndexView,
    children: [
      {
        path: 'home',
        component: HomeView
      },
      {
        path: 'platform',
        component: {
          template: '<router-view></router-view>'
        },
        children: platform
      }
    ]
  },
  {
    path: 'login',
    component: LoginView
  }
]
