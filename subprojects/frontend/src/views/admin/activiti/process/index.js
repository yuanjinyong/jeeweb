export * from './definition'
import {ProcessDefinitionView} from './definition'

export * from './instance'
import {ProcessInstanceView} from './instance'

export * from './model'
import {ProcessModelView} from './model'

export default [{
  path: '',
  component: {
    template: '<router-view></router-view>'
  }
}, {
  path: 'model',
  component: ProcessModelView
}, {
  path: 'definition',
  component: ProcessDefinitionView
}, {
  path: 'instance',
  component: ProcessInstanceView
}]
