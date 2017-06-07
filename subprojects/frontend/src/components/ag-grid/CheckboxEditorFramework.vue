<style scoped>
</style>

<template>
  <el-checkbox :ref="'input'" v-model="value" :true-label="1" :false-label="2" @change="onChange"
    style="line-height: 24px;">
  </el-checkbox>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: 2
      }
    },
    created () {
      this.value = this.params.value
    },
    methods: {
      onChange (event) {
        if (this.params.onChange) {
          this.params.onChange(this.params, this.value)
        } else {
          this.params.node.data[this.params.colDef.field] = this.value
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
