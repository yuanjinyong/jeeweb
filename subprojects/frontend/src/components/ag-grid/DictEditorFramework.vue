<template>
  <el-select :ref="'input'" v-model="value" size="mini" :clearable="false" :disabled="isDisabled()" @change="onChange"
             style="min-width:10px;width:100%;">
    <el-option v-for="item in options" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code">
      {{item.f_item_text}}
    </el-option>
  </el-select>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        options: [],
        editorOptions: {rule: [], isDisabled: null},
        value: null
      }
    },
    computed: {
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      Vue.lodash.merge(this.editorOptions, this.params.editorOptions)

      this.getDictItems(this.params.dict || this.filterParams.dictOptions, (items) => {
        this.options = items
      })

      this.$nextTick(() => {
        this.value = this.params.value + ''
      })
    },
    methods: {
      isDisabled () {
        if (this.editorOptions.isDisabled) {
          return this.editorOptions.isDisabled.call(this, this.params, this.entity)
        }
        return false
      },
      onChange (selectedValue) {
        if (this.params.onChange) {
          this.params.onChange.call(this, this.params, selectedValue)
        } else {
          this.params.node.data[this.params.colDef.field] = selectedValue
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
