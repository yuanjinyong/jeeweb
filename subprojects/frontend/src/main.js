// The Vue build version to load with the `import` command (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'

// import moment from 'moment'
// Object.defineProperty(Vue.prototype, '$moment', {value: moment})

import VueCookie from 'vue-cookie'
Vue.use(VueCookie)

import 'font-awesome-loader'
import 'bootstrap-loader'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
Vue.use(ElementUI)

import VueCfg from './assets/js/vue-cfg'

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
import './filters'
import './components'
import App from './App'

window.devMode = process.env.NODE_ENV === 'development'
window.devMode && console && console.debug('调试模式开启')

Vue.use(VueCfg)
Vue.use(VueGrid)
Vue.use(VueJw)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  template: '<App/>',
  components: {App}
})
