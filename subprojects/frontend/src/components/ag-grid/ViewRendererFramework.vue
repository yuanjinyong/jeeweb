<style scoped>
</style>

<template>
  <a @click.prevent="onClick(operation)" :title="operation.title" style="cursor: pointer;">{{ render() }}</a>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        operation: {},
        defaultOperation: {
          id: 'view',
          title: '查看'
        }
      }
    },
    computed: {
      featureComponent () {
        return this.params.context.featureComponent
      },
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      this.operation = Vue.lodash.merge({}, this.defaultOperation, {title: this.defaultOperation.title + this.featureComponent.featureOptions.name}, this.params.operation)
    },
    methods: {
      render () {
        if (this.operation.render) {
          return this.operation.render(this.params, this.entity)
        } else {
          return this.params.value
        }
      },
      onClick (operation) {
        if (operation.onClick) {
          operation.onClick.call(this.featureComponent, this.params, this.entity)
          return
        }

        if (this.featureComponent.formOptions) {
          Vue.lodash.merge(this.featureComponent.formOptions, {
            isShow: true,
            operation: operation.id,
            title: operation.title,
            params: this.entity
          })
        }
      }
    }
  })
</script>
