<style scoped>
</style>

<template>
  <el-row style="background-color: #39f;">
    <el-col :span="24" style="padding: 5px 20px;">
      <div style="text-align: center;">
        <div style="display: inline-block; margin-bottom: 0px; line-height: 22px; height: 22px;">
          {{windowSize.width}} &times; {{windowSize.height}}
        </div>
        <el-switch
          class="pull-right"
          style="margin-bottom: 0px;line-height: 22px; height: 22px;"
          :width="105"
          on-color="#13ce66"
          on-text="切换为真实"
          off-color="#ff4949"
          off-text="切换为模拟"
          v-model="mock"
          v-if="devMode">
        </el-switch>
      </div>
    </el-col>
  </el-row>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'jw-foot',
    data () {
      return {
        windowSize: {width: 0, height: 0}
      }
    },
    computed: {
      devMode () {
        return this.$store.state.devMode
      },
      mock: {
        get: function () {
          return this.$store.state.mock
        },
        set: function (val) {
          this.$store.commit('setMock', val)
        }
      }
    },
    mounted () {
      window.addEventListener('resize', this.onWindowResize)
      this.onWindowResize()
    },
    beforeDestroy () {
      window.removeEventListener('resize', this.onResize)
    },
    methods: {
      onWindowResize () {
        var vm = this
        vm.windowSize.width = window.innerWidth
        vm.windowSize.height = window.innerHeight
      }
    }
  }
</script>
