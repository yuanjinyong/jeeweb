import Vue from 'vue'
import VueResource from 'vue-resource'

Vue.use(VueResource)

Vue.http.interceptors.push(function (request, next) {
  // 在请求之前可以进行一些预处理和配置
  var vm = this || {} // 此处this为请求所在页面的Vue实例
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
  next((response) => { // 在响应之后传给then之前对response进行修改和逻辑判断。对于token时候已过期的判断，就添加在此处，页面中任何一次http请求都会先调用此处方法
    vm.loading = false // 加载结束
    vm.errorMessage = null

    if (Vue.store.state.mock) {
      console && console.debug('mock response', response)
    } else {
      var token = response.headers.get('X-REST-TOKEN') // 登录的时候会返回token
      if (token) {
        Vue.cookie.set('X-REST-TOKEN', token)
      }
    }

    if (response.status === 200) {
      if (response.body.success === true) {
        if (request.showSuccessMessage !== false && (request.method === 'POST' || request.method === 'PUT' || request.method === 'DELETE')) {
          Vue.prototype.$message({type: 'success', duration: 1500, message: response.body.message})
        }
      }
      /*
      new Promise((resolve, reject) => {
        if (request.responseType === 'blob') {
          if (response.body.type === 'application/json') {
            let fr = new FileReader()
            fr.onload = function (event) {
              resolve(JSON.parse(event.target.result))
            }
            fr.readAsText(response.body)
          } else {
            resolve({success: true, message: '处理成功。', data: response.body})
          }
        } else {
          resolve(response.body)
        }
      }).then(result => {
        if (result.success === true) {
          if (request.showSuccessMessage !== false && (request.method === 'POST' || request.method === 'PUT' || request.method === 'DELETE')) {
            Vue.prototype.$message({type: 'success', duration: 1500, message: result.message})
          }
        } else {
          Vue.prototype.$alert(result.message, '错误', {
            confirmButtonText: '关闭',
            type: 'error'
          })
        }
      })
      */
    } else if (response.status === 201) {
      Vue.prototype.$message({type: 'success', duration: 1500, message: response.body.message})
    } else if (response.status === 401) {
      if (vm.$route && vm.$route.path === '/admin/login') {
        console && console.error('账号密码错误，请重新输入！')
      } else {
        console && console.warn('请先登录验证！')
        Vue.cookie.delete('X-REST-TOKEN')  // 服务器端token过期后会返回401，再重新登录前，清空本地cookie中的token
        Vue.store.commit('setUser', null)
        Vue.store.commit('backupRoute', vm.$route)
        Vue.router.push({path: '/admin/login'})
      }
    } else if (response.status === 403) {
      vm.errorMessage = response.body.message || '无访问 [' + request.method + '][' + response.url + '] 的权限，请联系系统管理人员！'
    } else if (response.status === 404) {
      vm.errorMessage = 'URL [' + request.method + '][' + response.url + '] 不存在，请联系系统技术支持人员！'
    } else if (response.status === 500) {
      vm.errorMessage = response.body.message
    } else if (response.status === 504) {
      vm.errorMessage = '请求超时，请稍后重试！'
    } else {
      console && console.error('request', request, 'response', response)
      vm.errorMessage = '访问 [' + request.method + '][' + response.url + '] 失败，请联系系统技术支持人员！'
    }

    if (vm.errorMessage) {
      Vue.prototype.$alert(vm.errorMessage, '错误', {
        confirmButtonText: '关闭',
        type: 'error'
      })
    }

    return response
  })
})
