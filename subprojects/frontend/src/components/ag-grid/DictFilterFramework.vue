<style scoped>
</style>

<template>
  <el-select :ref="'input'" v-model="value" clearable :multiple="multiple" @change="onChange">
    <el-option v-for="item in options" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code">
    </el-option>
  </el-select>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        options: [],
        floatingModel: null,
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
      isFilterActive () {
        if (this.multiple) {
          return this.value !== undefined && this.value !== null && this.value.length > 0
        } else {
          return this.value !== undefined && this.value !== null && this.value !== ''
        }
      },
      doesFilterPass (params) {
        console && console.info('doesFilterPass', this.$options.name, params)
        return this.params.valueGetter(params.node) === this.value
      },
      getModel () {
        return {
          filter: this.value,
          filterType: this.filterParams.filterType ? this.filterParams.filterType : 'Integer',
          type: this.filterParams.type ? this.filterParams.type : null
        }
      },
      setModel (model) {
        this.floatingModel = model
        this.value = model.filter
      },
      onChange (val) {
        if (this.floatingModel) {
          this.floatingModel = null
        } else {
          this.params.filterChangedCallback()
        }
      }
    }
  })
</script>
