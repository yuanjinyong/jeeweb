export * from './company'
import {MyCompanyView} from './company'

export default [
  {
    path: '',
    component: {
      template: '<router-view></router-view>'
    },
    children: []
  },
  {
    path: 'company',
    component: MyCompanyView
  }
]
