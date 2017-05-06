<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>


<template>
  <div class="jw-admin-login">
    <!-- 顶部 -->
    <jw-head id="headContainer"></jw-head>


    <!-- 中部 -->
    <div id="middleContainer" style="display:table;width: 100%;padding:0 20px;"
      :style="{'min-height': middleSize.height + 'px'}">
      <div style="display:table-cell;vertical-align:middle;">
        <login-form></login-form>
      </div>

    </div>


    <!-- 底部 -->
    <jw-foot id="footContainer"></jw-foot>
  </div>
</template>


<script type="text/ecmascript-6">
  import $ from 'jquery'

  export default {
    name: 'adminLogin',
    data () {
      return {
        middleSize: {width: 0, height: 0},
        resizeTimer: null
      }
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)

      window.addEventListener('resize', this.onResize)
      this.onResize()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      onResize () {
        var vm = this
        if (vm.resizeTimer) {
          clearTimeout(vm.resizeTimer)
        }

        vm.resizeTimer = setTimeout(function () {
          vm.middleSize.width = $(window).outerWidth()
          vm.middleSize.height = $(window).outerHeight() - $('#middleContainer').offset().top - $('#footContainer').outerHeight()
          vm.resizeTimer = null
        }, 50)
      }
    }
  }
</script>
