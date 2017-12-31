export default [{
  path: '',
  component: r => require.ensure([], () => r(require('./View')), 'diy-partner-clothes')
}, {
  path: 'color',
  component: r => require.ensure([], () => r(require('./color/View')), 'diy-partner-clothes-color')
}, {
  path: 'size',
  component: r => require.ensure([], () => r(require('./size/View')), 'diy-partner-clothes-size')
}, {
  path: 'style',
  component: r => require.ensure([], () => r(require('./style/View')), 'diy-partner-clothes-style')
}]
