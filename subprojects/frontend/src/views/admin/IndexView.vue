<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
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
    padding-top: 11px;
  }

  .jw-view-tab-header .el-tabs__header {
    margin: 0px;
  }

  .jw-view-tab-body {
    overflow: auto;
  }
</style>


<template>
  <div style="height: 100%;" :style="{'background-color':layout.window.width < 768 ? '#fff' : '#324157'}">
    <jw-layout v-if="layout.window.width < 768">
      <!-- 顶部 -->
      <jw-head slot="top" :show-button="true"></jw-head>

      <!-- 中部 -->
      <div slot="middle">
        <el-row id="topMenu" class="jw-menu jw-top-menu">
          <div class="jw-menu-header jw-top-menu-header">
            <el-button class="jw-menu-switch" type="text" @click="onshowMenu">
              <i class="fa fa-bars" aria-hidden="true"></i>
            </el-button>
            <span style="float: right;" v-if="$devMode">{{layout.window.width}} &times; {{layout.window.height}}</span>
          </div>
          <el-col style="float: left;position: absolute;" :span="16">
            <jw-menu class="jw-menu-body jw-top-menu-body" :default-active="tabs.activeName" :menu-list="menuList"
                     @select="onSelectMenu" v-show="showMenu">
            </jw-menu>
          </el-col>
        </el-row>

        <div style="background-color: #fff;overflow: auto;"
             :style="{'width':layout.right.width+'px', 'height':layout.right.height+'px'}">
          <router-view :key="$route.path"></router-view>
        </div>
      </div>

      <!-- 底部 -->
      <jw-foot slot="bottom" v-show="false"></jw-foot>
    </jw-layout>


    <jw-layout v-else>
      <!-- 顶部 -->
      <jw-head slot="top" :show-button="true"></jw-head>

      <!-- 中部 -->
      <template>
        <!-- 左部 -->
        <div slot="left">
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
        <div slot="center" style="background-color: #fff;">
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
              <router-view :key="$route.path"></router-view>
            </div>
          </div>
        </div>
      </template>

      <!-- 底部 -->
      <jw-foot slot="bottom"></jw-foot>
    </jw-layout>
  </div>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'adminIndex',
    beforeRouteEnter (to, from, next) { // 在渲染该组件的对应路由被 confirm 前调用，不能获取组件实例`this`，因为当钩子执行前，组件实例还没被创建。
      Vue.store.commit('backupRoute', to)

      if (Vue.store.state.menuList.length > 0) {
        next()
      } else {
        let loading = Vue.prototype.$loading({text: '加载中……'})
        Promise.all([
          Vue.http.get('api/admin/index'),
          Vue.http.get('api/platform/data/dicts')
        ]).then((responses) => {
          let response = responses[0]
          Vue.store.commit('index', response.body.success ? response.body.data : {})

          response = responses[1]
          Vue.store.commit('setDicts', response.body.success ? response.body.data : {})

          loading.close()
          next(false)
          let menu = Vue.prototype.findMenuByRoutePath(to.path)
          Vue.store.commit('openTab', {path: menu.f_route_path, params: menu})
        }).catch(e => loading.close())
      }
    },
    beforeRouteLeave (to, from, next) { // 导航离开该组件的对应路由时调用，可以访问组件实例 `this`。
      next()
    },
    data () {
      return {
        showMenu: false,
        resizeTimer: null
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
    watch: {
      curUser (val, oldVal) {
        // 登出后，重新调用后台接口加载主页数据
        if (!val) {
          Vue.http.get('api/admin/index').catch(e => console && console.warn(e))
        }
      }
    },
    methods: {
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
