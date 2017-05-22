<style scoped>
</style>

<template>
  <a @click.prevent="onView" style="cursor: pointer;">{{ this.params.value }}</a>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    methods: {
      onView () {
        if (this.params.context.featureComponent.onView) {
          this.params.context.featureComponent.onView(this.params.node.data)
        } else if (this.params.context.featureComponent.formOptions) {
          this.params.context.featureComponent.formOptions.operation = 'view'
          this.params.context.featureComponent.formOptions.title = '查看' + this.params.context.featureComponent.featureOptions.name
          this.params.context.featureComponent.formOptions.params = this.params.node.data
          this.params.context.featureComponent.formOptions.isShow = true
        }
      }
    }
  })
</script>
