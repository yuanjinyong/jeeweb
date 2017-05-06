import Menu from './menu/view'
import Role from './role/view'
import Url from './url/view'
import User from './user/view'

export default [
  {
    path: '',
    component: {
      template: '<router-view></router-view>'
    }
  },
  {
    path: 'menu',
    component: Menu
  },
  {
    path: 'role',
    component: Role
  },
  {
    path: 'url',
    component: Url
  },
  {
    path: 'user',
    component: User
  }
]
