import partner from './partner'

export default [{
  path: '',
  component: {
    template: '<router-view></router-view>'
  },
  children: [{
    path: 'partner',
    component: {
      template: '<router-view></router-view>'
    },
    children: partner
  }]
}]
