export * from './dict'
import {DictView} from './dict'

export * from './menu'
import {MenuView} from './menu'

export * from './role'
import {RoleView} from './role'

export * from './url'
import {UrlView} from './url'

export * from './user'
import {UserView} from './user'

export default [
  {
    path: '',
    component: {
      template: '<router-view></router-view>'
    }
  },
  {
    path: 'dict',
    component: DictView
  },
  {
    path: 'menu',
    component: MenuView
  },
  {
    path: 'role',
    component: RoleView
  },
  {
    path: 'url',
    component: UrlView
  },
  {
    path: 'user',
    component: UserView
  }
]
