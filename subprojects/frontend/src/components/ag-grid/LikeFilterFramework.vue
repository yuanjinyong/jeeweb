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
      doesFilterPass (filterPassParams) {
        if (this.filterParams.doesFilterPass) {
          return this.filterParams.doesFilterPass(this.params, this.value, filterPassParams)
        } else {
          var cellValue = this.params.valueGetter(filterPassParams.node) + ''
          if (this.filterParams.type === 'leftLike') {
            return cellValue.endsWith(this.value)
          } else if (this.filterParams.type === 'rightLike') {
            return cellValue.startsWith(this.value)
          } else {
            return cellValue.indexOf(this.value) > -1
          }
        }
      },
      getModel () {
        return {
          filter: this.value,
          filterType: this.filterParams.filterType ? this.filterParams.filterType : 'String',
          type: this.filterParams.type ? this.filterParams.type : 'like'
        }
      },
      setModel (model) {
        this.value = model ? model.filter : null
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
