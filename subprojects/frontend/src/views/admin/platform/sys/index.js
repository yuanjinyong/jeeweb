export * from './menu'
import {MenuView} from './menu'

export * from './role'
import {RoleView} from './role'

export * from './setting'
import {SettingView} from './setting'

export * from './url'
import {UrlView} from './url'

export default [{
  path: 'dict',
  component: r => require.ensure([], () => r(require('./dict/DictView')), 'sys-dict')
}, {
  path: 'menu',
  component: MenuView
}, {
  path: 'role',
  component: RoleView
}, {
  path: 'setting',
  component: SettingView
}, {
  path: 'url',
  component: UrlView
}, {
  path: 'user',
  component: r => require.ensure([], () => r(require('./user/View')), 'sys-user')
}]
