<template>
  <el-input :ref="'input'" v-model="value" size="mini" :disabled="isDisabled()" @change="onChange" placeholder="请输入文本">
  </el-input>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
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
      this.value = this.params.value
    },
    methods: {
      isDisabled () {
        if (this.editorOptions.isDisabled) {
          return this.editorOptions.isDisabled.call(this, this.params, this.entity)
        }
        return false
      },
      onChange (val) {
        if (this.params.onChange) {
          this.params.onChange.call(this, this.params, val)
        } else {
          this.params.node.data[this.params.colDef.field] = val
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
