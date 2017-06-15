<style scoped>
</style>

<template>
  <div>
    <div id="layoutTop">
      <slot name="top"></slot>
    </div>


    <div id="layoutMiddle">
      <slot name="middle">
        <div id="layoutLeft" style="float: left;">
          <slot name="left"></slot>
        </div>

        <div id="layoutCenter">
          <slot name="center"></slot>
        </div>

        <div id="layoutRight">
          <slot name="right"></slot>
        </div>
      </slot>
    </div>


    <div id="layoutBottom">
      <slot name="bottom"></slot>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'jwLayout',
    computed: {
      layout: {
        get () {
          return this.$store.state.layout
        },
        set (val) {
          this.$store.commit('updateLayout', val)
        }
      }
    },
    mounted () {
      window.addEventListener('resize', this.onResize)
      this.$nextTick(() => {
        this.onResize()
      })
    },
    beforeDestroy () {
      window.removeEventListener('resize', this.onResize)
    },
    methods: {
      onResize (event) {
        var vm = this
        if (vm.resizeTimer) {
          clearTimeout(vm.resizeTimer)
        }

        vm.resizeTimer = setTimeout(() => {
          let top = document.getElementById('layoutTop')
          let bottom = document.getElementById('layoutBottom')
          let sideMenu = document.getElementById('sideMenu')
          let topMenu = document.getElementById('topMenu')
          if (!top) {
            return
          }

          let topHeight = top.clientHeight
          let bottomHeight = bottom.clientHeight
          let middleHeight = window.innerHeight - topHeight - bottomHeight - 1
          let leftWidth = 260
          let rightWidth = window.innerWidth - leftWidth
          let sideMenuHeaderHeight = sideMenu ? sideMenu.firstChild.clientHeight : 0
          let sideMenuBodyHeight = middleHeight - sideMenuHeaderHeight
          let topMenuHeaderHeight = topMenu ? topMenu.firstChild.clientHeight : 0

          this.layout = {
            window: {
              width: window.innerWidth, height: window.innerHeight
            },
            top: {
              width: window.innerWidth, height: topHeight
            },
            middle: {
              width: window.innerWidth, height: middleHeight
            },
            left: {
              width: leftWidth, height: middleHeight
            },
            right: {
              width: rightWidth, height: middleHeight
            },
            bottom: {
              width: window.innerWidth, height: bottomHeight
            },
            topMenu: {
              width: window.innerWidth, height: topMenuHeaderHeight
            },
            sideMenu: {
              header: {
                width: leftWidth, height: sideMenuHeaderHeight
              },
              body: {
                width: leftWidth, height: sideMenuBodyHeight
              }
            },
            viewTab: {
              header: {
                width: rightWidth, height: sideMenuHeaderHeight
              },
              body: {
                width: rightWidth, height: sideMenuBodyHeight
              }
            }
          }
          vm.resizeTimer = null
        }, 50)
      }
    }
  }
</script>
