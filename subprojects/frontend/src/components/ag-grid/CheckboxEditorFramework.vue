<template>
  <el-checkbox ref="input" v-model="value" :true-label="101" :false-label="102" @change="onChange"
    style="line-height: 24px;">
  </el-checkbox>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        value: 102
      }
    },
    created () {
      this.$nextTick(() => {
        this.value = this.params.value
      })
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
