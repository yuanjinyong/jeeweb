<template>
  <a @click.prevent="onClick(operation)" :title="operation.title" style="cursor: pointer;" v-html="render()"></a>
</template>

<script>
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
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      this.operation = Vue.lodash.merge({}, this.defaultOperation, {title: this.defaultOperation.title + this.params.context.name}, this.params.operation)
    },
    methods: {
      render () {
        if (this.operation.render) {
          return this.operation.render.call(this, this.params, this.entity)
        } else {
          return this.params.value
        }
      },
      onClick (operation) {
        if (operation.onClick) {
          operation.onClick.call(this, this.params, this.entity)
          return
        }

        let detailComponent = this.params.context.getDetailComponent && this.params.context.getDetailComponent.call(this, this.params, operation)
        if (detailComponent) {
          detailComponent.open({
            operation: operation.id,
            title: operation.title,
            params: this.entity
          })
        }
      }
    }
  })
</script>
