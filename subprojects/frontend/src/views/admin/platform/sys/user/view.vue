<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div>
    用户管理
  </div>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'user',
    data () {
      return {}
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {}
  }
</script>
