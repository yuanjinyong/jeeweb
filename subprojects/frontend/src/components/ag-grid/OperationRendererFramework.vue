<style scoped>
</style>

<template>
  <el-button-group>
    <el-button v-for="(operation, index) in operations" :key="operation.id"
               size="mini"
               :type="operation.type"
               :title="operation.title"
               :disabled="!hasPermission(operation) || isDisabled(operation)"
               @click.prevent="onClick(operation)">
      <i :class="operation.icon"></i>{{operation.text ? (' ' + operation.text) : ''}}
    </el-button>
  </el-button-group>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        operations: [],
        defaultOperations: {
          add: {
            title: '增加',
            type: 'primary',
            icon: 'fa fa-plus',
            permission: 'add'
          },
          edit: {
            title: '修改',
            type: 'info',
            icon: 'fa fa-edit',
            permission: 'edit'
          },
          remove: {
            title: '删除',
            type: 'danger',
            icon: 'fa fa-trash',
            permission: 'remove'
          },
          view: {
            title: '查看',
            icon: 'fa fa-file-text'
          }
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
      this.operations = []
      this.params.operations.forEach((operation) => {
        let defaultOperation = this.defaultOperations[operation.id] || {title: ''}
        this.operations.push(Vue.lodash.merge({}, defaultOperation, {title: defaultOperation.title + this.featureComponent.featureOptions.name}, operation))
      })
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

        if (operation.id !== 'remove') {
          if (this.featureComponent.formOptions) {
            Vue.lodash.merge(this.featureComponent.formOptions, {
              isShow: true,
              operation: operation.id,
              title: operation.title,
              params: this.entity
            })
          }
          return
        }

        if (this.featureComponent.gridOptions) {
          let featureName = this.featureComponent.featureOptions.name
          this.$confirm('确定要删除所选的' + featureName + '吗?', '删除' + featureName, {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            let url = this.featureComponent.gridOptions.context.url ? this.featureComponent.gridOptions.context.url : this.featureComponent.featureOptions.url
            this.$http.delete(url + '/' + this.entity.f_id).then((response) => {
              if (response.body.success) {
                this.featureComponent.gridOptions.context.params.totalCount = 0
                this.featureComponent.gridOptions.api.setDatasource(this.featureComponent.gridOptions.datasource)
              }
            })
          }).catch((e) => {
            // console && console.error(e)
          })
        }
      }
    }
  })
</script>
