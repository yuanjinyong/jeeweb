import Vue from 'vue'
import Router from 'vue-router'
import views from 'views'

Vue.use(Router)

const router = new Router({
  routes: views
})
// 注册全局导航钩子
router.beforeEach((to, from, next) => {
  // console && console.log('beforeEach', 'to', to, 'from', from)
  next()
})
router.afterEach((route) => { // 不知道哪里的问题，貌似这个没有调用进来
  // console && console.log('afterEach', 'route', route)
})
Vue.router = router

export default router
