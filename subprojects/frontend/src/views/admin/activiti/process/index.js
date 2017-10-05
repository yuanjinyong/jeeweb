export default [{
  path: '',
  component: {
    template: '<router-view></router-view>'
  }
}, {
  path: 'model',
  component: r => require.ensure([], () => r(require('./model/View')), 'activiti-process-model')
}, {
  path: 'definition',
  component: r => require.ensure([], () => r(require('./definition/View')), 'activiti-process-definition')
}, {
  path: 'instance',
  component: r => require.ensure([], () => r(require('./instance/View')), 'activiti-process-instance')
}]
