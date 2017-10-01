// The Vue build version to load with the `import` command (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'

import moment from 'moment'
Object.defineProperty(Vue.prototype, '$moment', {value: moment})
Vue.moment = moment

import lodash from 'lodash'
Object.defineProperty(Vue.prototype, '$lodash', {value: lodash})
Vue.lodash = lodash

import VueCookie from 'vue-cookie'
Vue.use(VueCookie)

import 'font-awesome-webpack'

import ElementUI from 'element-ui'
Vue.use(ElementUI)
import 'element-ui/lib/theme-default/index.css'
// import 'element-ui/lib/theme-defaut/base.css' // fade/zoom 等
import CollapseTransition from 'element-ui/lib/transitions/collapse-transition' // collapse 展开折叠
Vue.component(CollapseTransition.name, CollapseTransition)

import VueCfg from './plugins/vue-cfg'

import {AgGridVue} from 'ag-grid-vue'
Vue.component('ag-grid', AgGridVue) // 注册全局组件

import 'ag-grid/dist/styles/ag-grid.css'
// import 'ag-grid/dist/styles/theme-blue.css'
// import 'ag-grid/dist/styles/theme-bootstrap.css'
// import 'ag-grid/dist/styles/theme-dark.css'
import 'ag-grid/dist/styles/theme-fresh.css'
// import 'ag-grid/dist/styles/theme-material.css'
// import 'ag-grid-enterprise/main' // need if you use ag-grid enterprise features
import VueGrid from './plugins/vue-grid'

import store from './store'
import router from './router'
import './resource'
import VueJw from './plugins/vue-jw'
import './mixins'
import './directives'
import './filters'
import './components'
import App from './App'

window.devMode = process.env.NODE_ENV === 'development'
window.devMode && console && console.warn('调试模式开启')

Vue.use(VueCfg)
Vue.use(VueGrid)
Vue.use(VueJw)

// Vue.mixin({
//   created () {
//     console.log('created', this.$options.name, this._uid)
//   },
//   mounted () {
//     console.log('mounted', this.$options.name, this._uid)
//   },
//   activated () {
//     console.log('activated', this.$options.name, this._uid)
//   }
// })

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: {App}
})
