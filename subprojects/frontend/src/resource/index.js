import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

Vue.http.interceptors.push(function (request, next) {
  // 在请求之前可以进行一些预处理和配置
  var vm = this // 此处this为请求所在页面的Vue实例
  vm.loading = true // 正在加载
  vm.errorMessage = null
  // modify request
  if (Vue.store.state.mock) {
    request.method = 'GET'
    request.url = 'mock/' + request.url + '.json'
    console && console.debug('mock request', request)
  } else {
    request.url = Vue.cfg.apiUrl + request.url
    Vue.http.headers.common['X-REST-TOKEN'] = Vue.cookie.get('X-REST-TOKEN')
  }

  // continue to next interceptor
  next(function (response) { // 在响应之后传给then之前对response进行修改和逻辑判断。对于token时候已过期的判断，就添加在此处，页面中任何一次http请求都会先调用此处方法
    if (Vue.store.state.mock) {
      console && console.debug('mock response', response)
    } else {
      var token = response.headers.get('X-REST-TOKEN') // 登录的时候会返回token
      if (token) {
        Vue.cookie.set('X-REST-TOKEN', token)
      }
    }

    vm.loading = false // 加载结束
    if (response.status === 401) {
      if (vm.$route.path !== '/admin/login') {
        console && console.warn('请先登录验证！')
        Vue.cookie.delete('X-REST-TOKEN')  // 服务器端token过期后会返回401，再重新登录前，清空本地cookie中的token
        Vue.store.commit('backupRoute', vm.$route)
        Vue.store.commit('setUser', {user: null})
        Vue.router.push({path: '/admin/login'})
      } else {
        console && console.error('账号密码错误，请重新输入！')
      }
    }

    if (response.status === 200) {
      var result = response.body
      if (result.success === false) {
        vm.errorMessage = result.message
        console && console.error(result.message)
        alert(result.message)

        // response.ok = false
        // response.status = 501
        // response.statusText = vm.errorMessage
      }
    } else if (response.status === 404) {
      vm.errorMessage = 'URL [ ' + response.url + ' ] 不存在，请联系系统技术支持人员！'
      alert(vm.errorMessage)
      console.error(vm.errorMessage)
    } else if (response.status === 504) {
      vm.errorMessage = '请求超时，请稍后重试！'
      alert(vm.errorMessage)
      console.error(vm.errorMessage)
    }

    return response
  })
})
