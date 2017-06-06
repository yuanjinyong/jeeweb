<style scoped>
</style>

<template>
  <el-select :ref="'input'" v-model="value" size="mini" :clearable="false" @change="onChange"
    style="min-width:10px;width:100%;">
    <el-option v-for="item in options" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code">
      {{item.f_item_text}}
    </el-option>
  </el-select>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: '',
        options: []
      }
    },
    created () {
      this.value = this.params.value
      if (typeof (this.params.dict) === 'object') {
        if (typeof (this.params.dict.length) !== 'undefined') {
          this.options = this.params.dict
        } else {
          this.options = []
        }
      } else if (typeof (this.params.dict) === 'string') {
        this.options = Vue.store.state.dicts[this.params.dict]
      } else {
        this.options = []
      }
    },
    methods: {
      onChange (selectedValue) {
        if (this.params.onChange) {
          this.params.onChange(this.params, selectedValue)
        } else {
          this.params.node.data[this.params.colDef.field] = selectedValue
          console.debug('fieldList', this.params.context.featureComponent.entity.fieldList)
        }
      },
      getValue () {
        return this.value
      },
      isCancelBeforeStart () {
        return this.true
      }
    }
  })
</script>
