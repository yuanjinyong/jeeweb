<style scoped>
</style>

<template>
  <span>{{ params.value | timestamp(options.format || 'YYYY-MM-DD HH:mm:ss') }}</span>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        options: {
          format: 'YYYY-MM-DD HH:mm:ss'
        }
      }
    },
    created () {
      this.$lodash.merge(this.options, this.params.options || {})
    }
  })
</script>
