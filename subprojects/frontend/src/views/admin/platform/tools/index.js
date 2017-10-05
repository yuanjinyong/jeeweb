import schema from './schema'

export default [{
  path: 'code/generation',
  component: r => require.ensure([], () => r(require('./code/generation/rule/View')), 'platform-tools-code-generation')
}, {
  path: 'information/schema',
  component: r => require.ensure([], () => r(require('./information/schema/View')), 'platform-tools-information-schema')
}, {
  path: 'schema',
  component: {
    template: '<router-view></router-view>'
  },
  children: schema
}]
