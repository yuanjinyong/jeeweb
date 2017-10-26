<template>
  <span>{{ this.params.value | dict(this.params.dict) }}</span>
</template>

<script>
  import Vue from 'vue'

  export default Vue.extend({
    methods: {}
  })
</script>
