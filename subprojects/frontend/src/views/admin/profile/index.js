export default [{
  path: 'company',
  component: r => require.ensure([], () => r(require('./company/View')), 'profile-company')
}]
