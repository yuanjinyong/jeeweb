import Vue from 'vue'
import Clipboard from 'clipboard'

var VueClipboard = {
  copy (selector) {
    let clipboard = new Clipboard(selector)
    clipboard.on('success', (e) => {
      Vue.prototype.$message({type: 'success', duration: 1500, message: 'Sql语句已经复制到粘贴板。'})
      clipboard.destroy()
    })
    clipboard.on('error', (e) => {
      Vue.prototype.$message({type: 'error', duration: 1500, message: '浏览器不支持自动复制，请手动复制Sql语句！'})
      clipboard.destroy()
    })
  }
}

const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  Vue.prototype.$clipboard = VueClipboard
  Vue.clipboard = VueClipboard
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
