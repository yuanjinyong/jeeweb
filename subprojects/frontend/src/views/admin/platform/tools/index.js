export * from './code/generation'
import {GenerationRuleView} from './code/generation'

export * from './information'
import {InformationSchemaView} from './information'

export * from './schema'
import schema from './schema'

export default [{
  path: 'code/generation',
  component: GenerationRuleView
}, {
  path: 'information/schema',
  component: InformationSchemaView
}, {
  path: 'schema',
  component: {
    template: '<router-view></router-view>'
  },
  children: schema
}]
