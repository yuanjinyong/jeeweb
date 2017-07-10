export * from './code/generation'
import {GenerationRuleView} from './code/generation'

export * from './information'
import {InformationSchemaView} from './information'

export default [{
  path: '',
  component: {
    template: '<router-view></router-view>'
  }
}, {
  path: 'code/generation',
  component: GenerationRuleView
}, {
  path: 'information/schema',
  component: InformationSchemaView
}]
