import Vue from 'vue'
import Router from 'vue-router'
import views from 'views'

Vue.use(Router)

const router = new Router({
  routes: views
})
router.beforeEach = function (to, from, next) { // 不知道哪里的问题，貌似这个没有调用进来
  console && console.debug('beforeEach', 'to', to, 'from', from)
  next()
}
router.afterEach = function (route) { // 不知道哪里的问题，貌似这个没有调用进来
  console && console.debug('afterEach', 'route', route)
}
Vue.router = router

export default router
