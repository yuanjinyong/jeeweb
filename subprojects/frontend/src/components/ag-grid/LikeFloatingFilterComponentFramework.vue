<style scoped>
</style>

<template>
  <div class="jw-floating-filter-cell">
    <el-input :ref="'input'" v-model="value" size="small" @change="onChange" placeholder="支持模糊过滤">
    </el-input>
  </div>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: null
      }
    },
    created () {
      if (!this.params.column.colDef.floatingFilterComponentParams) {
        this.params.column.colDef.floatingFilterComponentParams = {}
      }
      if (this.params.column.colDef.floatingFilterComponentParams.suppressFilterButton === undefined) {
        this.params.column.colDef.floatingFilterComponentParams.suppressFilterButton = true
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
        }, 500)
      }
    }
  })
</script>
