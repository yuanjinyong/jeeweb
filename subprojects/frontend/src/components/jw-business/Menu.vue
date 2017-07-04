<template>
  <el-menu :default-active="defaultActive" :router="router" @select="onSelectMenu" theme="dark">
    <template v-for="(menu, index) in menuList">
      <jw-submenu :menu="menu" v-if="menu.f_type === 1"></jw-submenu>
      <jw-menu-item :menu="menu" v-else-if="menu.f_type === 2"></jw-menu-item>
    </template>
  </el-menu>
</template>


<script>
  export default {
    name: 'jwMenu',
    props: {
      router: {
        type: Boolean,
        default: false
      },
      defaultActive: {
        type: String,
        default: 'AdminHome'
      },
      menuList: {
        type: Array,
        default: function () {
          return []
        }
      }
    },
    methods: {
      onSelectMenu (index, indexPath, menuItem) {
        this.$emit('select', index, indexPath, menuItem.route)
      }
    }
  }
</script>
