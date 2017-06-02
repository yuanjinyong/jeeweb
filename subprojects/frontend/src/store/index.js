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
    menuList: [],
    permissionList: [],
    hasPermission (f_menu_id) {
      return this.permissionList.indexOf(f_menu_id) > -1
    },
    _addPermission (menus, permissions) {
      menus.forEach((menu) => {
        if (menu.f_type >= 3) { // 只有按钮和令牌才需要在这里判断，根、目录、页面不需要进行权限判断
          permissions.push(menu.f_id)
        }

        if (menu.children && menu.children.length > 0) {
          this._addPermission(menu.children, permissions)
        }
      })
    },
    dicts: {
      'YesNo': {1: '是', 2: '否'},
      'MenuType': {0: '根', 1: '目录', 2: '页面', 3: '按钮', 4: '令牌'},
      'UserStatus': {1: '正常', 2: '锁定', 3: '注销'},
      'HttpMethods': [
        {
          f_item_code: '[]',
          f_item_text: '[]'
        },
        {
          f_item_code: '[DELETE]',
          f_item_text: '[DELETE]'
        },
        {
          f_item_code: '[GET]',
          f_item_text: '[GET]'
        },
        {
          f_item_code: '[POST]',
          f_item_text: '[POST]'
        },
        {
          f_item_code: '[PUT]',
          f_item_text: '[PUT]'
        }
      ]
    }
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
      state.permissionList = []
      state._addPermission(state.menuList, state.permissionList)
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
Vue.prototype.hasPermission = function (f_menu_id) {
  return Vue.store.state.hasPermission(f_menu_id)
}

export default store
