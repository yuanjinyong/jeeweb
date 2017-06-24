<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .jw-menu {
    color: #bfcbd9;
    background-color: #324157;
  }

  .jw-top-menu {

  }

  .jw-side-menu {

  }

  .jw-menu-switch {
    font-size: 24px;
    padding: 5px;
    color: #bfcbd9;
  }

  .jw-menu-header {
    padding: 10px 20px;
    font-size: 24px;
    border-bottom: 1px solid #5d5d5d;
  }

  .jw-top-menu-header {

  }

  .jw-side-menu-header {
  }

  .jw-menu-body {
  }

  .jw-top-menu-body {
    border-radius: 0;
    z-index: 999999;
  }

  .jw-side-menu-body {
    overflow: auto;
    border-radius: 0;
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
  <div style="height: 100%;" :style="{'background-color':layout.window.width < 768 ? '#fff' : '#324157'}">
    <div v-if="layout.window.width < 768">
      <!-- 顶部 -->
      <jw-head id="layoutTop"></jw-head>

      <!-- 中部 -->
      <div id="layoutMiddle" :style="{'width':layout.middle.width+'px', 'height':layout.middle.height+'px'}">
        <loading v-if="loadingUser"></loading>
        <div>
          <el-row id="topMenu" class="jw-menu jw-top-menu">
            <div class="jw-menu-header jw-top-menu-header">
              <el-button class="jw-menu-switch" type="text" @click="onshowMenu">
                <i class="fa fa-bars" aria-hidden="true"></i>
              </el-button>
            </div>
            <el-col style="float: left;position: absolute;" :span="16">
              <jw-menu class="jw-menu-body jw-top-menu-body" :menu-list="menuList" @select="onSelectMenu"
                       v-show="showMenu">
              </jw-menu>
            </el-col>
          </el-row>

          <div style="background-color: #fff;overflow: auto;"
               :style="{'width':layout.right.width+'px', 'height':layout.right.height+'px'}">
            <router-view></router-view>
          </div>
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
          <div id="sideMenu" class="jw-menu jw-side-menu">
            <div class="jw-menu-header jw-side-menu-header">
              系统菜单
              <el-button class="jw-menu-switch pull-right" type="text" @click="onSwitchSideMenu">
                <i class="fa fa-bars" aria-hidden="true"></i>
              </el-button>
            </div>
            <jw-menu class="jw-menu-body jw-side-menu-body"
                     :style="{'width':layout.sideMenu.body.width+'px', 'height':layout.sideMenu.body.height+'px'}"
                     :default-active="tabs.activeName" :menu-list="menuList" @select="onSelectMenu">
            </jw-menu>
          </div>
        </div>

        <!-- 右部 -->
        <div id="layoutRight" style="background-color: #fff;"
             :style="{'width':layout.right.width+'px', 'height':layout.right.height+'px', 'margin-left':layout.left.width+'px'}">
          <div id="viewTab" class="jw-view-tab">
            <el-tabs type="card" class="jw-card-tabs jw-view-tab-header" v-model="tabs.activeName"
                     @tab-remove="onCloseTab" @tab-click="onClickTab">
              <el-tab-pane v-for="(route, index) in tabs.routes"
                           :closable="route.params.f_closable !== false"
                           :label="route.params.f_name"
                           :name="route.params.f_id"
                           :key="route.params.f_id">
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
  import Vue from 'vue'

  export default {
    name: 'adminIndex',
    beforeRouteEnter (to, from, next) { // 在渲染该组件的对应路由被 confirm 前调用，不能获取组件实例`this`，因为当钩子执行前，组件实例还没被创建。
//      console.log('beforeRouteEnter', from, to)
      Vue.http.get('api/platform/data/dicts').then((response) => {
        Vue.store.commit('setDicts', response.body.data)
        next()
      })
    },
    beforeRouteLeave (to, from, next) { // 导航离开该组件的对应路由时调用，可以访问组件实例 `this`。
//      console.log('beforeRouteLeave', from, to)
      next()
    },
    data () {
      return {
        showMenu: false,
        resizeTimer: null,
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
        return this.$store.state.menuList
      },
      tabs () {
        return this.$store.state.tabs
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
        if (val) {
          this.loadMenus()
        }

        if (oldVal && !val) {
          this.loadUser() // 登出后，重新调用后台接口加载用户信息，以便自动跳转到登录页面
          return
        }
      }
    },
    methods: {
      loadUser () {
        this.loadingUser = true
        this.$http.get('api/admin/index/user').then((response) => {
          this.loadingUser = false
          if (response.body.success) {
            this.$store.commit('setUser', {user: response.body.data})
          } else {
            this.$store.commit('setUser', {user: null})
          }
        }).catch(function (e) {
          // console.error(e) // 打印一下错误
        })
      },
      loadMenus () {
        this.loadingMenu = true
        this.$http.get('api/admin/index/menus').then((response) => {
          this.loadingMenu = false
          if (response.body.success) {
            this.$store.commit('setMenuList', {menuList: response.body.data.items, route: this.$route})
          } else {
            this.$store.commit('setMenuList', {menuList: [], route: this.$route})
          }

          this.onResize()
        })
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
          let topMenuHeaderHeight = topMenu ? topMenu.firstChild.clientHeight : 0
          let leftWidth = window.innerWidth < 768 ? 0 : 260
          let leftHeight = middleHeight - topMenuHeaderHeight
          let sideMenuHeaderHeight = sideMenu ? sideMenu.firstChild.clientHeight : 0
          let sideMenuBodyHeight = leftHeight - sideMenuHeaderHeight
          let rightWidth = window.innerWidth - leftWidth
          let rightHeight = middleHeight - topMenuHeaderHeight

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
              width: leftWidth, height: leftHeight
            },
            right: {
              width: rightWidth, height: rightHeight
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
        this.$store.commit('openTab', route)
      },
      onClickTab (tabComponent) {
        this.$store.commit('switchTab', tabComponent.name)
      },
      onCloseTab (targetName) {
        this.$store.commit('removeTab', targetName)
      }
    }
  }
</script>
