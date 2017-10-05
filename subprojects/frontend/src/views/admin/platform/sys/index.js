export default [{
  path: 'dict',
  component: r => require.ensure([], () => r(require('./dict/View')), 'platform-sys-dict')
}, {
  path: 'menu',
  component: r => require.ensure([], () => r(require('./menu/View')), 'platform-sys-menu')
}, {
  path: 'role',
  component: r => require.ensure([], () => r(require('./role/View')), 'platform-sys-role')
}, {
  path: 'setting',
  component: r => require.ensure([], () => r(require('./setting/View')), 'platform-sys-setting')
}, {
  path: 'url',
  component: r => require.ensure([], () => r(require('./url/View')), 'platform-sys-url')
}, {
  path: 'user',
  component: r => require.ensure([], () => r(require('./user/View')), 'platform-sys-user')
}]
