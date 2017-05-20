var VueJw = {
  vm: null,
  permissionList: null,
  hasPermission (f_menu_id) {
    if (!this.permissionList) {
      this._initPermissionList()
    }

    return this.permissionList.indexOf(f_menu_id) > -1
  },
  _initPermissionList () {
    if (this.vm && this.vm.store.state.menuList) {
      this.permissionList = []
      this._addPermission(this.vm.store.state.menuList, this.permissionList)
    }
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
  }
}

/**
 * Install plugin.
 */
const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  VueJw.vm = Vue

  Vue.prototype.$jw = VueJw
  Vue.jw = VueJw
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
