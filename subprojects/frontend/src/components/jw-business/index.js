import Vue from 'vue'

// 注册全局组件
import Authorize from './Authorize'
Vue.component(Authorize.name, Authorize)

import CompanyDropdown from './CompanyDropdown'
Vue.component(CompanyDropdown.name, CompanyDropdown)

import Menu from './Menu'
Vue.component(Menu.name, Menu)

import MenuItem from './MenuItem'
Vue.component(MenuItem.name, MenuItem)

import SubMenu from './SubMenu'
Vue.component(SubMenu.name, SubMenu)
