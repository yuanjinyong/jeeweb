/**
 * Install plugin.
 */

import Cfg from './cfg'

const plugin = function (Vue) {
  if (plugin.installed) {
    return
  }

  Vue.prototype.$cfg = Cfg
  Vue.cfg = Cfg
}

if (typeof window !== 'undefined' && window.Vue) {
  window.Vue.use(plugin)
}

export default plugin
