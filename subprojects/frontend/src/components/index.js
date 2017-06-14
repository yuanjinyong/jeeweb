import Vue from 'vue'
import './jw-common'

// 注册全局组件
import Foot from './Foot'
Vue.component(Foot.name, Foot)

import Head from './Head'
Vue.component(Head.name, Head)

import Loading from './Loading'
Vue.component(Loading.name, Loading)

import LoginForm from './LoginForm'
Vue.component(LoginForm.name, LoginForm)

import Menu from './Menu'
Vue.component(Menu.name, Menu)

import MenuItem from './MenuItem'
Vue.component(MenuItem.name, MenuItem)

import SubMenu from './SubMenu'
Vue.component(SubMenu.name, SubMenu)
