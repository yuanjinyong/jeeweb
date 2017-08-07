export * from './process'
import process from './process'

export default [{
  path: '',
  component: {
    template: '<router-view></router-view>'
  },
  children: [{
    path: 'process',
    component: {
      template: '<router-view></router-view>'
    },
    children: process
  }]
}]
