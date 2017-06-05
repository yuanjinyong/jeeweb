<style scoped>
</style>

<template>
  <el-input :ref="'input'" v-model="value" @change="onChange" placeholder="支持模糊过滤"></el-input>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: null
      }
    },
    computed: {
      filterParams () {
        return this.params.column.colDef.filterParams
      }
    },
    created () {
      if (!this.params.column.colDef.filterParams) {
        this.params.column.colDef.filterParams = {}
      }
    },
    methods: {
      isFilterActive () {
        return this.value !== undefined && this.value !== null && this.value !== ''
      },
      doesFilterPass (params) {
        console && console.info('doesFilterPass', this.$options.name, params)
        return this.params.valueGetter(params.node).contains(this.value)
      },
      getModel () {
        return {
          filter: this.value,
          filterType: this.filterParams.filterType ? this.filterParams.filterType : 'String',
          type: this.filterParams.type ? this.filterParams.type : 'like'
        }
      },
      setModel (model) {
        this.value = model.filter
      },
      onChange (val) {
        var vm = this
        vm.filterChangedTimer && clearTimeout(vm.filterChangedTimer)
        vm.filterChangedTimer = setTimeout(function () {
          vm.params.filterChangedCallback()
        }, 500)
      }
    }
  })
</script>
