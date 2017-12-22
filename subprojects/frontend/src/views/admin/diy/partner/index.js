export default [{
  path: '',
  component: {
    template: '<router-view></router-view>'
  }
}, {
  path: 'partner',
  component: r => require.ensure([], () => r(require('./partner/View')), 'diy-partner-partner')
}]
