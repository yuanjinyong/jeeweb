<style scoped>
</style>

<template>
  <el-row style="background-color: #324157;color: #bfcbd9;">
    <el-col :span="24" style="padding: 5px 20px;">
      <div style="text-align: center;">
        <div style="display: inline-block; margin-bottom: 0; line-height: 22px; height: 22px;">
          {{layout.window.width}} &times; {{layout.window.height}}
        </div>
        <el-switch
          class="pull-right"
          style="margin-bottom: 0;line-height: 22px; height: 22px;"
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

<script>
  export default {
    name: 'jwFoot',
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
      },
      layout () {
        return this.$store.state.layout
      }
    }
  }
</script>
