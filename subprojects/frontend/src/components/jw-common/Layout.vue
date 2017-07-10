<style scoped>
</style>

<template>
  <div>
    <div id="layoutTop">
      <slot name="top"></slot>
    </div>


    <div id="layoutMiddle"
         :style="{'width':layout.middle.width+'px', 'height':layout.middle.height+'px'}">
      <slot name="middle">
        <div id="layoutLeft" style="float: left;"
             :style="{'width':layout.left.width+'px', 'height':layout.left.height+'px'}">
          <slot name="left"></slot>
        </div>

        <div id="layoutCenter"
             :style="{'width':layout.right.width+'px', 'height':layout.right.height+'px', 'margin-left':layout.left.width+'px'}">
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
    created () {
      this.onWindowResize = this.$lodash.debounce(() => {
        this._updateLayout()
      }, 50)
      window.addEventListener('resize', this.onWindowResize)
      this.$nextTick(() => {
        this.onWindowResize()
      })
    },
    beforeDestroy () {
      window.removeEventListener('resize', this.onWindowResize)
    },
    methods: {
      _updateLayout () {
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
      }
    }
  }
</script>
