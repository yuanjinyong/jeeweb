<template>
  <div class="ag-header-component" style="margin-top: -1px;padding: 0px 5px;">
    <el-button size="mini"
               :type="operation.type"
               :title="operation.title"
               :disabled="!hasPermission(operation) || isDisabled(operation)"
               @click.prevent="onClick(operation)">
      <i :class="operation.icon" style="min-width:12px;"></i>
    </el-button>
  </div>
</template>

<script>
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
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      this.operation = Vue.lodash.merge({}, this.defaultOperation, {title: this.defaultOperation.title + this.params.context.name}, this.params.operation)
    },
    methods: {
      hasPermission (operation) {
        if (operation.permission) {
          if (this.params.context.getPermissions) {
            return this.params.context.getPermissions.call(this, this.params, operation)[operation.permission]
          } else {
            return false
          }
        }
        return true
      },
      isDisabled (operation) {
        if (operation.isDisabled) {
          return operation.isDisabled.call(this, this.params, this.entity)
        }
        return false
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
