<template>
  <el-menu-item :index="menu.f_id" :route="{path: menu.f_route_path, params: menu}">
    <div class="jw-menu-item-text">
      <i :class="menu.f_icon || 'fa fa-file-o'" style="min-width:20px;"></i>{{menu.f_name}}
    </div>
  </el-menu-item>
</template>


<script>
  export default {
    name: 'jw-menu-item',
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
