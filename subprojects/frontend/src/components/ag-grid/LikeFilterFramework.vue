<style scoped>
</style>

<template>
  <el-input :ref="'input'" v-model="text" placeholder="支持模糊过滤"></el-input>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        text: '',
        valueGetter: null
      }
    },
    watch: {
      'text': function (val, oldVal) {
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
        return this.text !== undefined && this.text !== null && this.text !== ''
      },
      doesFilterPass (params) {
        console && console.info('doesFilterPass', this.$options.name, params)
        return this.valueGetter(params.node).contains(this.text)
      },
      getModel () {
        return {filter: this.text, filterType: 'text', type: 'contains'}
      },
      setModel (model) {
        this.text = model.filter
      }
    }
  })
</script>
