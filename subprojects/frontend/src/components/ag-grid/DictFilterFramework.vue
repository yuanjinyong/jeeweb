<template>
  <el-select ref="input" v-model="value" clearable :multiple="multiple" @change="onChange">
    <el-option v-for="item in dictItems" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code">
    </el-option>
  </el-select>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        defaultFilterParams: {
          type: 'equal',
          filterType: 'Integer'
        },
        dictItems: [],
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
      this.params.column.colDef.filterParams = this.$lodash.merge({},
        this.defaultFilterParams,
        this.params.column.colDef.cellRendererParams || {},
        this.params.column.colDef.filterParams || {})

      this.getDictItems(this.filterParams.dict || this.filterParams.dictOptions, (items) => {
        this.dictItems = items
      })

      if (this.multiple) {
        this.value = []
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
      doesFilterPass (filterPassParams) {
        if (this.filterParams.doesFilterPass) {
          return this.filterParams.doesFilterPass(this.params, this.value, filterPassParams)
        } else {
          if (this.filterParams.type === 'in') {
            if (this.value && this.value.length > 0) {
              return this.value.indexOf(this.params.valueGetter(filterPassParams.node) + '') > -1
            }
          } else if (this.filterParams.type === 'notIn') {
            return this.value.indexOf(this.params.valueGetter(filterPassParams.node) + '') < 0
          } else {
            return this.value === this.params.valueGetter(filterPassParams.node) + ''
          }

          return true
        }
      },
      getModel () {
        return {
          filter: this.value,
          filterType: this.filterParams.filterType,
          type: this.filterParams.type
        }
      },
      setModel (model) {
        if (model) {
          this.floatingModel = model.floating ? model : null
          this.value = model ? model.filter : (this.multiple ? [] : null)
        } else {
          this.value = null
        }
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
