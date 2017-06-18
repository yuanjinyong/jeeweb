import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    devMode: process.env.NODE_ENV === 'development',
    mock: false,
    layout: {
      window: {
        width: window.innerWidth, height: window.innerHeight
      },
      top: {
        width: window.innerWidth, height: 0
      },
      middle: {
        width: window.innerWidth, height: 0
      },
      left: {
        width: 260, height: 0
      },
      right: {
        width: 0, height: 0
      },
      bottom: {
        width: window.innerWidth, height: 0
      },
      topMenu: {
        width: window.innerWidth, height: 0
      },
      sideMenu: {
        header: {
          width: 0, height: 0
        },
        body: {
          width: 0, height: 0
        }
      },
      viewTab: {
        header: {
          width: 0, height: 0
        },
        body: {
          width: 0, height: 0
        }
      }
    },
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
    dicts: {}
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
    setDicts (state, payload) {
      state.dicts = payload
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
