import Vue from 'vue'
import Router from 'vue-router'
import views from 'views'

Vue.use(Router)

const router = new Router({
  routes: views
})
// 注册全局导航钩子
router.beforeEach((to, from, next) => {
  console && console.info('Route:', from.path, '=>', to.path)
  next()
})
router.afterEach((route) => {
  // console && console.log('afterEach', 'route', route)
  let menu = Vue.prototype.findMenuByRoutePath(route.path)
  if (menu) {
    Vue.store.commit('openTab', {path: menu.f_route_path, params: menu, status: 'afterRoute'})
  }
})
Vue.router = router

export default router
