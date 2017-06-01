<style scoped>
</style>

<template>
  <div class="ag-header-component" style="padding: 5px;">
    <el-button type="primary" size="mini"
      :title="'增加'+featureOptions.name"
      :disabled="!permission.add"
      @click.prevent="onClick">
      <i class="fa fa-plus"></i>
    </el-button>
  </div>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    computed: {
      featureOptions () {
        return this.params.context.featureComponent.featureOptions
      },
      permission () {
        return this.params.context.featureComponent.permission || {add: true}
      }
    },
    methods: {
      onClick () {
        if (this.params.context.featureComponent.onAdd) {
          this.params.context.featureComponent.onAdd()
        } else if (this.params.context.featureComponent.formOptions) {
          this.params.context.featureComponent.formOptions.operation = 'add'
          this.params.context.featureComponent.formOptions.title = '增加' + this.params.context.featureComponent.featureOptions.name
          this.params.context.featureComponent.formOptions.isShow = true
        }
      }
    }
  })
</script>
