<style scoped>
</style>

<template>
  <el-input :ref="'input'" v-model="value" placeholder="支持模糊过滤"></el-input>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: '',
        valueGetter: null
      }
    },
    watch: {
      'value': function (val, oldVal) {
        var vm = this
        if (val !== oldVal) {
          vm.filterChangedTimer && clearTimeout(vm.filterChangedTimer)
          vm.filterChangedTimer = setTimeout(function () {
            vm.params.filterChangedCallback()
          }, 200)
        }
      }
    },
    created () {
      this.valueGetter = this.params.valueGetter
    },
    methods: {
      isFilterActive () {
        return this.value !== undefined && this.value !== null && this.value !== ''
      },
      doesFilterPass (params) {
        console && console.info('doesFilterPass', this.$options.name, params)
        return this.valueGetter(params.node).contains(this.value)
      },
      getModel () {
        return {filter: this.value, filterType: 'text', type: 'contains'}
      },
      setModel (model) {
        this.value = model.filter
      }
    }
  })
</script>
