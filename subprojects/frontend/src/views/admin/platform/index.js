export * from './sys'
import sys from './sys'

export * from './tools'
import tools from './tools'

export default [{
  path: 'sys',
  component: {
    template: '<router-view></router-view>'
  },
  children: sys
}, {
  path: 'tools',
  component: {
    template: '<router-view></router-view>'
  },
  children: tools
}]
