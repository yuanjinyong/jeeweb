<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .jw-menu-head {
    padding: 10px 20px;
    font-size: 24px;
    background-color: #eef1f6;
    border-bottom: 1px solid #ccc;
  }

  .jw-card-tabs {
    padding-top: 13px;
  }
</style>


<template>
  <div class="jw-admin-index">
    <!-- 顶部 -->
    <jw-head id="headContainer"></jw-head>


    <!-- 中部 -->
    <div id="middleContainer" :style="{'min-height': middleSize.height + 'px'}">
      <loading v-if="loadingUser"></loading>
      <div v-else>
        <div v-show="curUser">
          <div v-if="showTopMenu">
            <el-row>
              <div class="jw-menu-head">
                <i class="fa fa-bars" aria-hidden="true" style="cursor: pointer;" @click="onShowSideMenu"></i>
              </div>
              <el-col style="float: left;position: absolute;" :span="18">
                <jw-side-menu style="z-index: 999999;border-right: 1px solid #ccc;border-bottom: 1px solid #ccc;"
                  :menu-list="menuList" @select="onSelectMenu" v-show="showSideMenu"></jw-side-menu>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="24">
                <router-view></router-view>
              </el-col>
            </el-row>
          </div>

          <div v-else>
            <el-row v-if="menuSize.width > 0">
              <div style="float: left;"
                :style="{'width': menuSize.width + 'px', 'height': menuSize.height + 'px'}">
                <div id="menuHead" class="jw-menu-head" style="border-right: 1px solid #ccc;">
                  系统菜单 <i class="fa fa-bars pull-right" aria-hidden="true" style="cursor: pointer;"
                  @click="onShowPopMenu"></i>
                </div>

                <div>
                  <jw-side-menu style="overflow:auto;border-right: 1px solid #ccc;"
                    :style="{'width': menuSize.width + 'px', 'height': menuSize.height + 'px'}"
                    :default-active="tabsManager.activeName" :menu-list="menuList"
                    @select="onSelectMenu"></jw-side-menu>
                </div>
              </div>

              <div :style="{'margin-left': menuSize.width + 'px'}">
                <div :style="{'width': bodySize.width + 'px'}">
                  <el-tabs type="card" class="jw-card-tabs" v-model="tabsManager.activeName"
                    @tab-remove="onCloseTab" @tab-click="onClickTab">
                    <el-tab-pane v-for="(item, index) in tabsManager.tabs"
                      :closable="item.params.f_closable !== false"
                      :label="item.params.f_name"
                      :name="item.params.f_id"
                      :key="item.params.f_id">
                    </el-tab-pane>
                  </el-tabs>
                  <div style="overflow:auto; margin-top: -15px;"
                    :style="{'width': bodySize.width + 'px', 'height': bodySize.height + 'px'}">
                    <keep-alive>
                      <router-view></router-view>
                    </keep-alive>
                  </div>
                </div>
              </div>
            </el-row>
          </div>
        </div>
      </div>
    </div>


    <!-- 底部 -->
    <jw-foot id="footContainer"></jw-foot>
  </div>
</template>


<script type="text/ecmascript-6">
  import $ from 'jquery'

  export default {
    name: 'adminIndex',
    data () {
      return {
        windowSize: {width: 0, height: 0},
        middleSize: {width: 0, height: 0},
        menuSize: {width: 260, height: 0},
        bodySize: {width: 0, height: 0},
        showTopMenu: false,
        showSideMenu: false,
        showPopMenu: false,
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
      curUser () {
        return this.$store.state.user
      },
      menuList () {
        return [].concat(this.$store.state.menuHome, this.$store.state.menuList)
      }
    },
    created () {
      window.devMode && console && console.info('created', this.$options.name, this._uid)

      this.loadUser()
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)

      window.addEventListener('resize', this.onResize)
      this.onResize()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
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

        vm.windowSize.width = window.innerWidth
        vm.windowSize.height = window.innerHeight
        vm.showTopMenu = vm.windowSize.width < 768

        vm.resizeTimer = setTimeout(function () {
          vm.middleSize.width = vm.windowSize.width
          vm.middleSize.height = vm.windowSize.height - $('#middleContainer').offset().top - $('#footContainer').outerHeight() - 2
          vm.middleSize.height = window.innerHeight - document.getElementById('headContainer').clientHeight - document.getElementById('footContainer').clientHeight - 2
          vm.menuSize.height = vm.middleSize.height - $('#menuHead').outerHeight()
          vm.bodySize.width = vm.middleSize.width - vm.menuSize.width
          vm.bodySize.height = vm.middleSize.height - $('#menuHead').outerHeight()

          vm.$store.commit('updateLayout', {
            window: {width: vm.windowSize.width, height: vm.windowSize.height},
            middle: {width: vm.middleSize.width, height: vm.middleSize.height},
            body: {width: vm.bodySize.width, height: vm.bodySize.height}
          })

          vm.resizeTimer = null
        }, 50)
      },
      onShowSideMenu () {
        this.showSideMenu = !this.showSideMenu
      },
      onShowPopMenu () {
        this.showPopMenu = !this.showPopMenu
      },
      onSelectMenu (index, indexPath, route) {
        this.showSideMenu = false

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
