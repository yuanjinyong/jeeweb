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
    dicts: {},
    getDictValue (value, dict) {
      let items = dict
      if (typeof (dict) === 'string') {
        items = this.dicts[dict]
        if (!items) {
          console && console.error('请先配置字典', dict)
        }
      }

      if (typeof (items) === 'object') {
        if (typeof (items.length) !== 'undefined') {
          for (let i = 0; i < items.length; i++) {
            let item = items[i]
            if (item.f_item_code === value + '') {
              return item.f_item_text
            }
          }
        } else {
          return items[value]
        }
      }
      return value
    },
    getDictItems (dict, cb) {
      let dictOptions = dict
      if (typeof (dict) === 'string') {
        dictOptions = this.dicts[dict]
        if (!dictOptions) {
          console && console.error('请先配置字典', dict)
        }
      }

      let items = null
      if (typeof (dictOptions) === 'object') {
        if (typeof (dictOptions.length) !== 'undefined') { // 如果为数组
          items = [].concat(dictOptions)
          cb(items)
        } else if (dictOptions.url) { // 如果为带url的对象
          Vue.http.get(dictOptions.url).then((response) => {
            items = []
            let codeFiled = dictOptions.codeFiled ? dictOptions.codeFiled : 'f_item_code'
            let textFiled = dictOptions.textFiled ? dictOptions.textFiled : 'f_item_text'
            let dictItems = response.body.success ? response.body.data.items : []
            dictItems.forEach((dictItem) => {
              items.push({f_item_code: dictItem[codeFiled], f_item_text: dictItem[textFiled]})
            })
            cb(items)
          })
        } else {
          items = []
          for (let p in dictOptions) {
            items.push({f_item_code: p, f_item_text: dictOptions[p]})
          }
          cb(items)
        }
      }
    },
    companyList: [],
    company: {},
    employee: {},
    user: null,
    menuList: [],
    findMenuById (menuId, menus) {
      for (let i in menus) {
        let menu = menus[i]
        if (menu.f_id === menuId) {
          return menu
        }

        if (menu.children && menu.children.length > 0) {
          let subMenu = this.findMenuById(menuId, menu.children)
          if (subMenu) {
            return subMenu
          }
        }
      }

      return null
    },
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
    tabs: {
      activeName: 'AdminHome',
      routes: [{
        path: '/admin/home',
        params: {
          f_id: 'AdminHome',
          f_name: '首页',
          f_route_path: '/admin/home',
          f_icon: 'fa fa-home',
          f_closable: false,
          f_type: 2
        }
      }]
    },
    _initTabs (tabs, menuList) {
      // console.log('_initTabs', tabs, menuList, routePath)
      // 仍旧保留原来已有的Tab页
      let routes = []
      tabs.routes.forEach((route) => {
        let menu = this.findMenuByRoutePath(route.path, menuList)
        if (menu) {
          routes.push(route)
        }
      })
      tabs.routes = routes
    },
    findMenuByRoutePath (routePath, menus) {
      for (let i in menus) {
        let menu = menus[i]
        if (menu.f_route_path === routePath) {
          return menu
        }

        if (menu.children && menu.children.length > 0) {
          let subMenu = this.findMenuByRoutePath(routePath, menu.children)
          if (subMenu) {
            return subMenu
          }
        }
      }

      return null
    }
  },
  mutations: {
    setMock (state, payload) {
      state.mock = payload
    },
    updateLayout (state, payload) {
      state.layout = payload
    },
    setDicts (state, payload) {
      state.dicts = payload
    },
    setUser (state, payload) {
      state.user = payload
    },
    openTab (state, payload) {
      // console.log('openTab', state.tabs, payload)
      let route = payload
      let hasTab = false
      for (let i in state.tabs.routes) {
        if (state.tabs.routes[i].params.f_id === route.params.f_id) {
          hasTab = true
          break
        }
      }
      if (!hasTab) {
        state.tabs.routes.push(route)
      }

      state.tabs.activeName = route.params.f_id
      if (route.status !== 'afterRoute') {
        Vue.router.push(route)
      }
    },
    switchTab (state, payload) {
      // console.log('switchTab', state.tabs, payload)
      let activeTabName = payload
      for (let i in state.tabs.routes) {
        if (state.tabs.routes[i].params.f_id === activeTabName) {
          state.tabs.activeName = activeTabName
          Vue.router.push(state.tabs.routes[i])
          break
        }
      }
    },
    removeTab (state, payload) {
      // console.log('removeTab', state.tabs, payload)
      let removedTabName = payload
      if (state.tabs.activeName === removedTabName) { // 如果移除的是当前Tab页
        state.tabs.routes.forEach((route, index) => {
          if (route.params.f_id === removedTabName) {
            let nextRoute = state.tabs.routes[index + 1] || state.tabs.routes[index - 1]
            if (nextRoute) {
              state.tabs.activeName = nextRoute.params.f_id
              Vue.router.push(nextRoute)
            }
          }
        })
      }

      state.tabs.routes = state.tabs.routes.filter(route => route.params.f_id !== removedTabName)
    },
    backupRoute (state, payload) {
      if (payload) {
        state.originalRoute = payload
        console && console.log('backupRoute', payload.path)
      }
    },
    index (state, payload) {
      // console && console.log('index', payload)
      state.companyList = payload.companyList || []
      state.company = payload.company || {}
      state.employee = payload.employee || {}

      state.user = payload.user || null
      state.menuList = [].concat(state.menuHome, payload.menuList || [])
      state.permissionList = []
      state._addPermission(state.menuList, state.permissionList)
    },
    switch (state, payload) {
      // console && console.log('switch', payload)
      state.companyList = payload.companyList || []
      state.company = payload.company || {}
      state.employee = payload.employee || {}

      state.menuList = [].concat(state.menuHome, payload.menuList || [])
      state.permissionList = []
      state._addPermission(state.menuList, state.permissionList)

      state._initTabs(state.tabs, state.menuList)
    },
    logout (state) {
      state.originalRoute = null
      state.user = null
      state.menuList = []
      state.tabs.routes = state.tabs.routes.slice(0, 1)
    }
  }
})
Vue.store = store

Vue.prototype.getDictValue = function (value, dict) {
  return Vue.store.state.getDictValue(value, dict)
}
Vue.prototype.getDictItems = function (dict, cb) {
  return Vue.store.state.getDictItems(dict, cb)
}
Vue.prototype.hasPermission = function (f_menu_id) {
  return Vue.store.state.hasPermission(f_menu_id)
}
Vue.prototype.findMenuByRoutePath = function (routePath) {
  let menu = Vue.store.state.findMenuByRoutePath(routePath, Vue.store.state.menuList)
  if (menu) {
    return menu
  }

  return Vue.store.state.menuList[0]
}
Vue.prototype.findMenuById = function (menuId) {
  return Vue.store.state.findMenuById(menuId, Vue.store.state.menuList)
}

export default store
