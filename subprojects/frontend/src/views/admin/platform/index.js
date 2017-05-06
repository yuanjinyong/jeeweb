import sys from './sys'
import tools from './tools'

export default [
  {
    path: '',
    component: {
      template: '<router-view></router-view>'
    },
    children: [
      {
        path: 'sys',
        component: {
          template: '<router-view></router-view>'
        },
        children: sys
      },
      {
        path: 'tools',
        component: {
          template: '<router-view></router-view>'
        },
        children: tools
      }
    ]
  }
]
