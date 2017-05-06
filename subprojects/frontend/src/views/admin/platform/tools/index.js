import CodeGeneration from './code/generation/view'
import InformationSchema from './information/schema/view'

export default [
  {
    path: '',
    component: {
      template: '<router-view></router-view>'
    }
  },
  {
    path: 'code/generation',
    component: CodeGeneration
  },
  {
    path: 'information/schema',
    component: InformationSchema
  }
]
