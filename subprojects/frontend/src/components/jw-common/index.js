import Vue from 'vue'

// 注册全局组件
import Dict from './Dict'
Vue.component(Dict.name, Dict)

import Form from './Form'
Vue.component(Form.name, Form)

import InputSelector from './InputSelector'
Vue.component(InputSelector.name, InputSelector)

import Layout from './Layout'
Vue.component(Layout.name, Layout)

import Selector from './Selector'
Vue.component(Selector.name, Selector)
