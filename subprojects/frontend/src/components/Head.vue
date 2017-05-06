<style scoped>
</style>

<template>
  <el-row style="background-color: #39f;">
    <el-col :span="24" style="padding: 5px 20px;">
      <div style="display: inline-block;">
        <img src="assets/img/logo.png">
      </div>
      <div style="position: absolute; right: 0px; bottom: 0px;padding: 20px;" v-if="curUser">
        <el-button type="warning" @click="onLogout"><i class="fa fa-power-off"></i>&nbsp;退出</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<script type="text/ecmascript-6">
  export default {
    name: 'jw-head',
    computed: {
      curUser () {
        return this.$store.state.user
      }
    },
    methods: {
      onLogout () {
        var vm = this
        vm.$http.post('api/logout').then(function (response) {
          vm.result = response.body
          if (vm.result.success) {
            vm.$store.commit('logout')
            vm.$router.push({path: '/'})
          } else {
            console && console.error(vm.result.message)
          }
        })
      }
    }
  }
</script>
