<style scoped>
</style>

<template>
  <el-input class="jw-floating-filter" :ref="'input'" v-model="value" @change="onChange" placeholder="支持模糊过滤">
  </el-input>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: null
      }
    },
    methods: {
      onParentModelChanged (parentModel) {
        this.value = parentModel ? parentModel.filter : null
      },
      onChange (val) {
        var vm = this
        vm.filterChangedTimer && clearTimeout(vm.filterChangedTimer)
        vm.filterChangedTimer = setTimeout(function () {
          vm.params.onFloatingFilterChanged({filter: val})
        }, 200)
      }
    }
  })
</script>
