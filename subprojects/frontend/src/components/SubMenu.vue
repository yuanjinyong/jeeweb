<template>
  <el-submenu :index="menu.f_id">
    <div slot="title" class="jw-menu-item-text">
      <i :class="menu.f_icon || 'fa fa-list'"></i>{{menu.f_name}}
    </div>
    <template v-for="(submenu, index) in menu.children">
      <jw-submenu :menu="submenu" v-if="submenu.f_type === 1"></jw-submenu>
      <jw-menu-item :menu="submenu" v-else-if="submenu.f_type === 2"></jw-menu-item>
    </template>
  </el-submenu>
</template>


<script>
  export default {
    name: 'jw-submenu',
    props: {
      menu: {
        type: Object,
        default: function () {
          return {}
        }
      }
    }
  }
</script>
