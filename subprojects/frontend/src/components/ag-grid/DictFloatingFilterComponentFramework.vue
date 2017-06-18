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
      if (!this.params.column.colDef.filterParams.dict && this.params.column.colDef.cellRendererParams) {
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

      if (this.filterParams.dict) {
        var items = this.filterParams.dict
        if (typeof (this.filterParams.dict) === 'string') {
          items = Vue.store.state.dicts[this.filterParams.dict]
          if (!items) {
            console && console.error('请先配置字典', this.filterParams.dict)
          }
        }
        if (typeof (items.length) !== 'undefined') {
          this.options = items
        } else {
          this.options = []
          for (var p in items) {
            this.options.push({f_item_code: p, f_item_text: items[p]})
          }
        }
      } else {
        Vue.http.get(this.filterParams.url).then((response) => {
          this.options = []
          var codeFiled = this.filterParams.codeFiled ? this.filterParams.codeFiled : 'f_item_code'
          var textFiled = this.filterParams.textFiled ? this.filterParams.textFiled : 'f_item_text'
          var items = response.body.success ? response.body.data.items : []
          items.forEach((item) => {
            this.options.push({f_item_code: item[codeFiled], f_item_text: item[textFiled]})
          })
        })
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
