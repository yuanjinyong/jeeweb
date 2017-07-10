import Vue from 'vue'
import './jw-common'
import './jw-business'

// 注册全局组件
import Foot from './Foot'
Vue.component(Foot.name, Foot)

import Head from './Head'
Vue.component(Head.name, Head)
