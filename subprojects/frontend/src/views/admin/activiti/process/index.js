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
}]
