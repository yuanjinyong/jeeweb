<style scoped>
</style>

<template>
  <span>{{ this.params.value | dict(this.params.dict) }}</span>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    methods: {}
  })
</script>
