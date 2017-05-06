import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    devMode: process.env.NODE_ENV === 'development',
    mock: false,
    layout: {window: {width: 0, height: 0}, middle: {width: 0, height: 0}, body: {width: 0, height: 0}},
    menuHome: {
      f_id: 'AdminHome',
      f_name: '首页',
      f_route_path: '/admin/home',
      f_icon: 'fa fa-home',
      f_closable: false,
      f_type: 2
    },
    originalRoute: null,
    user: null,
    menuList: []
  },
  mutations: {
    setMock (state, payload) {
      state.mock = payload
    },
    updateLayout (state, payload) {
      state.layout = payload
    },
    setUser (state, payload) {
      state.user = payload.user
    },
    setMenuList (state, payload) {
      state.menuList = payload.menuList
    },
    backupRoute (state, payload) {
      state.originalRoute = payload
    },
    logout (state) {
      state.user = null
      state.menuList = []
    }
  }
})
Vue.store = store

export default store
