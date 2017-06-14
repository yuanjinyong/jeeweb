<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .jw-menu-header {
    padding: 10px 20px;
    font-size: 24px;
    background-color: #eef1f6;
    border-bottom: 1px solid #ccc;
  }

  .jw-top-menu-header {

  }

  .jw-side-menu-header {
    border-right: 1px solid #ccc;
  }

  .jw-menu-switch {
    font-size: 24px;
    padding: 5px;
  }

  .jw-side-menu-body {
    overflow: auto;
    border-right: 1px solid #ccc;
  }

  .jw-view-tab-header {
    padding-top: 13px;
  }

  .jw-view-tab-body {
    overflow: auto;
    margin-top: -15px;
  }
</style>


<template>
  <div>
    <div v-if="layout.window.width < 768">
      <!-- 顶部 -->
      <jw-head id="layoutTop"></jw-head>

      <!-- 中部 -->
      <div id="layoutMiddle" :style="{'width':layout.middle.width+'px', 'height':layout.middle.height+'px'}">
        <loading v-if="loadingUser"></loading>
        <div>
          <el-row id="topMenu">
            <div class="jw-menu-header jw-top-menu-header">
              <el-button class="jw-menu-switch" type="text" @click="onshowMenu">
                <i class="fa fa-bars" aria-hidden="true"></i>
              </el-button>
            </div>
            <el-col style="float: left;position: absolute;" :span="8">
              <jw-menu style="z-index: 999999;border-right: 1px solid #ccc;border-bottom: 1px solid #ccc;"
                       :menu-list="menuList" @select="onSelectMenu" v-show="showMenu"></jw-menu>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="24" style="overflow-x: auto;">
              <router-view></router-view>
            </el-col>
          </el-row>
        </div>
      </div>

      <!-- 底部 -->
      <jw-foot id="layoutBottom" v-show="false"></jw-foot>
    </div>


    <div v-else>
      <!-- 顶部 -->
      <jw-head id="layoutTop"></jw-head>

      <!-- 中部 -->
      <div id="layoutMiddle" :style="{'width':layout.middle.width+'px', 'height':layout.middle.height+'px'}">
        <loading v-if="loadingUser"></loading>
        <!-- 左部 -->
        <div id="layoutLeft" style="float: left;"
             :style="{'width':layout.left.width+'px', 'height':layout.left.height+'px'}">
          <div id="sideMenu" class="jw-side-menu">
            <div class="jw-menu-header jw-side-menu-header">
              系统菜单
              <el-button class="jw-menu-switch pull-right" type="text" @click="onSwitchSideMenu">
                <i class="fa fa-bars" aria-hidden="true"></i>
              </el-button>
            </div>
            <jw-menu class="jw-side-menu-body"
                     :style="{'width':layout.sideMenu.body.width+'px', 'height':layout.sideMenu.body.height+'px'}"
                     :default-active="tabsManager.activeName" :menu-list="menuList"
                     @select="onSelectMenu"></jw-menu>
          </div>
        </div>

        <!-- 右部 -->
        <div id="layoutRight"
             :style="{'width':layout.right.width+'px', 'height':layout.right.height+'px', 'margin-left':layout.left.width+'px'}">
          <div id="viewTab" class="jw-view-tab">
            <el-tabs type="card" class="jw-card-tabs jw-view-tab-header" v-model="tabsManager.activeName"
                     @tab-remove="onCloseTab" @tab-click="onClickTab">
              <el-tab-pane v-for="(item, index) in tabsManager.tabs"
                           :closable="item.params.f_closable !== false"
                           :label="item.params.f_name"
                           :name="item.params.f_id"
                           :key="item.params.f_id">
              </el-tab-pane>
            </el-tabs>
            <div class="jw-view-tab-body"
                 :style="{'width':layout.viewTab.body.width+'px', 'height':layout.viewTab.body.height+'px'}">
              <router-view></router-view>
            </div>
          </div>
        </div>
      </div>

      <!-- 底部 -->
      <jw-foot id="layoutBottom"></jw-foot>
    </div>
  </div>
</template>


<script>
  export default {
    name: 'adminIndex',
    data () {
      return {
        showMenu: false,
        resizeTimer: null,
        tabsManager: {
          activeName: 'AdminHome',
          tabs: []
        },
        loadingUser: true,
        loadingMenu: true
      }
    },
    computed: {
      layout () {
        return this.$store.state.layout
      },
      curUser () {
        return this.$store.state.user
      },
      menuList () {
        return [].concat(this.$store.state.menuHome, this.$store.state.menuList)
      }
    },
    created () {
      this.loadUser()
    },
    mounted () {
      window.addEventListener('resize', this.onResize)
      this.$nextTick(() => {
        this.onResize()
      })
    },
    beforeDestroy () {
      window.removeEventListener('resize', this.onResize)
    },
    watch: {
      curUser (val, oldVal) {
        if (oldVal && !val) { // 登出后，重新调用后台接口加载用户信息，以便自动跳转到登录页面
          this.loadUser()
          return
        }
      }
    },
    methods: {
      loadUser () {
        var vm = this
        vm.loadingUser = true
        vm.$http.get('api/admin/index/user').then(function (response) {
          vm.loadingUser = false
          if (response.body.success) {
            vm.$store.commit('setUser', {user: response.body.data})
            vm.loadMenus()
          } else {
            vm.$store.commit('setUser', {user: null})
          }
        }).catch(function (e) {
          // console.error(e) // 打印一下错误
        })
      },
      loadMenus () {
        var vm = this
        vm.loadingMenu = true
        vm.$http.get('api/admin/index/menus').then(function (response) {
          vm.loadingMenu = false
          if (response.body.success) {
            vm.$store.commit('setMenuList', {menuList: response.body.data.items})
            vm.openMenuByRoute([].concat(vm.$store.state.menuHome, response.body.data.items))
          } else {
            vm.$store.commit('setMenuList', {menuList: []})
          }
        })
      },
      openMenuByRoute (menus) {
        var vm = this
        var menu = vm.findMenuByRoute(vm.$route, menus)
        if (!menu) {
          vm.tabsManager.tabs.push({path: menus[0].f_route_path, params: menus[0]})
        } else {
          if (menu.f_id !== menus[0].f_id) {
            vm.tabsManager.tabs.push({path: menus[0].f_route_path, params: menus[0]})
          }
          vm.tabsManager.tabs.push({path: menu.f_route_path, params: menu})
        }

        var activeTab = vm.tabsManager.tabs[vm.tabsManager.tabs.length - 1]
        vm.tabsManager.activeName = activeTab.params.f_id
        vm.$router.push(activeTab)

        vm.onResize()
      },
      findMenuByRoute (route, menus) {
        for (var i in menus) {
          var menu = menus[i]
          if (menu.f_route_path === route.path) {
            return menu
          }

          if (menu.children && menu.children.length > 0) {
            var subMenu = this.findMenuByRoute(route, menu.children)
            if (subMenu) {
              return subMenu
            }
          }
        }

        return null
      },
      onResize () {
        var vm = this
        if (vm.resizeTimer) {
          clearTimeout(vm.resizeTimer)
        }

        vm.resizeTimer = setTimeout(() => {
          let top = document.getElementById('layoutTop')
          let bottom = document.getElementById('layoutBottom')
          let sideMenu = document.getElementById('sideMenu')
          let topMenu = document.getElementById('topMenu')
          if (!top) {
            return
          }

          let topHeight = top.clientHeight
          let bottomHeight = bottom.clientHeight
          let middleHeight = window.innerHeight - topHeight - bottomHeight - 1
          let leftWidth = 260
          let rightWidth = window.innerWidth - leftWidth
          let sideMenuHeaderHeight = sideMenu ? sideMenu.firstChild.clientHeight : 0
          let sideMenuBodyHeight = middleHeight - sideMenuHeaderHeight
          let topMenuHeaderHeight = topMenu ? topMenu.firstChild.clientHeight : 0

          vm.$store.commit('updateLayout', {
            window: {
              width: window.innerWidth, height: window.innerHeight
            },
            top: {
              width: window.innerWidth, height: topHeight
            },
            middle: {
              width: window.innerWidth, height: middleHeight
            },
            left: {
              width: leftWidth, height: middleHeight
            },
            right: {
              width: rightWidth, height: middleHeight
            },
            bottom: {
              width: window.innerWidth, height: bottomHeight
            },
            topMenu: {
              width: window.innerWidth, height: topMenuHeaderHeight
            },
            sideMenu: {
              header: {
                width: leftWidth, height: sideMenuHeaderHeight
              },
              body: {
                width: leftWidth, height: sideMenuBodyHeight
              }
            },
            viewTab: {
              header: {
                width: rightWidth, height: sideMenuHeaderHeight
              },
              body: {
                width: rightWidth, height: sideMenuBodyHeight
              }
            }
          })
          vm.resizeTimer = null
        }, 50)
      },
      onshowMenu () {
        this.showMenu = !this.showMenu
      },
      onSwitchSideMenu () {
      },
      onSelectMenu (index, indexPath, route) {
        this.showMenu = false

        var tabExists = true
        for (var i in this.tabsManager.tabs) {
          if (this.tabsManager.tabs[i].params.f_id === route.params.f_id) {
            tabExists = false
            break
          }
        }
        if (tabExists) {
          this.tabsManager.tabs.push(route)
        }

        this.tabsManager.activeName = route.params.f_id
        this.$router.push(route)
      },
      onClickTab (tab) {
        for (var i in this.tabsManager.tabs) {
          if (this.tabsManager.tabs[i].params.f_id === this.tabsManager.activeName) {
            this.$router.push(this.tabsManager.tabs[i])
            break
          }
        }
      },
      onCloseTab (targetName) {
        var tabs = this.tabsManager.tabs
        var activeName = this.tabsManager.activeName
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.params.f_id === targetName) {
              var nextTab = tabs[index + 1] || tabs[index - 1]
              if (nextTab) {
                activeName = nextTab.params.f_id
                this.$router.push(nextTab)
              }
            }
          })
        }

        this.tabsManager.activeName = activeName
        this.tabsManager.tabs = tabs.filter(tab => tab.params.f_id !== targetName)
      }
    }
  }
</script>
