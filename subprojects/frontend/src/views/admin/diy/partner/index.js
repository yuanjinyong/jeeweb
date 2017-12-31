import clothes from './clothes'

export default [{
  path: '',
  component: {
    template: '<router-view></router-view>'
  }
}, {
  path: 'clothes',
  component: {
    template: '<router-view></router-view>'
  },
  children: clothes
}, {
  path: 'partner',
  component: r => require.ensure([], () => r(require('./partner/View')), 'diy-partner-partner')
}]
