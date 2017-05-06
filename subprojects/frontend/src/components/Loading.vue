<style scoped>
</style>

<template>
  <div class="alert text-center" style="margin-bottom: 0px;">
    <i :class="icon"></i> <span class="sr-only">{{message}}</span>
  </div>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'loading',
    props: {
      icon: {
        type: String,
        default: 'fa fa-spinner fa-spin fa-3x fa-fw'
      },
      message: {
        type: String,
        default: '加载中……'
      }
    }
  }
</script>
