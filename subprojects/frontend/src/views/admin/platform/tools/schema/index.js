export default [{
  path: 'table',
  component: r => require.ensure([], () => r(require('./table/View')), 'platform-tools-schema-table')
}]
