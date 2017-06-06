<style scoped>
</style>

<template>
  <div class="jw-floating-filter-cell">
    <el-select :ref="'input'" v-model="value" size="small" clearable :multiple="multiple" @change="onChange">
      <el-option v-for="item in options" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code">
      </el-option>
    </el-select>
  </div>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        options: [],
        value: null
      }
    },
    computed: {
      filterParams () {
        return this.params.column.colDef.filterParams
      },
      multiple () {
        return this.params.column.colDef.filterParams.type === 'in' || this.params.column.colDef.filterParams.type === 'notIn'
      }
    },
    created () {
      if (!this.params.column.colDef.filterParams) {
        this.params.column.colDef.filterParams = {}
      }
      if (!this.params.column.colDef.filterParams.dict) {
        this.params.column.colDef.filterParams.dict = this.params.column.colDef.cellRendererParams.dict
      }

      if (!this.params.column.colDef.floatingFilterComponentParams) {
        this.params.column.colDef.floatingFilterComponentParams = {}
      }
      if (this.params.column.colDef.floatingFilterComponentParams.suppressFilterButton === undefined) {
        this.params.column.colDef.floatingFilterComponentParams.suppressFilterButton = true
      }

      if (this.multiple) {
        this.value = []
      }

      var items = this.filterParams.dict
      if (typeof (this.filterParams.dict) === 'string') {
        items = Vue.store.state.dicts[this.filterParams.dict]
      }
      if (typeof (items.length) !== 'undefined') {
        this.options = items
      } else {
        this.options = []
        for (var p in items) {
          this.options.push({f_item_code: p, f_item_text: items[p]})
        }
      }
    },
    methods: {
      onParentModelChanged (parentModel) {
        this.value = parentModel ? parentModel.filter : null
      },
      onChange (val) {
        this.params.onFloatingFilterChanged({filter: val})
      }
    }
  })
</script>
