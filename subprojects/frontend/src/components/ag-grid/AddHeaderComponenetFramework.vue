<style scoped>
</style>

<template>
  <div class="ag-header-component" style="padding: 3px 5px;">
    <el-button size="mini"
               :type="operation.type"
               :title="operation.title"
               :disabled="!hasPermission(operation) || isDisabled(operation)"
               @click.prevent="onClick(operation)">
      <i :class="operation.icon"></i>
    </el-button>
  </div>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        operation: {},
        defaultOperation: {
          id: 'add',
          title: '增加',
          type: 'primary',
          icon: 'fa fa-plus',
          permission: 'add'
        }
      }
    },
    computed: {
      featureComponent () {
        return this.params.context.featureComponent
      },
      permissions () {
        return this.params.context.featureComponent.permission
      },
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      this.operation = Vue.lodash.merge({}, this.defaultOperation, {title: this.defaultOperation.title + this.featureComponent.featureOptions.name}, this.params.operation)
    },
    methods: {
      hasPermission (operation) {
        if (operation.permission && this.permissions) {
          return this.permissions[operation.permission]
        }
        return true
      },
      isDisabled (operation) {
        if (operation.isDisabled) {
          return operation.isDisabled(this.params, this.entity)
        }
        return false
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
