<style scoped>
</style>

<template>
  <el-button-group>
    <el-button v-for="(operation, index) in params.operations" size="mini"
      :key="operation.id"
      :type="operation.type ? operation.type : (operations[operation.id] ? operations[operation.id].type : null)"
      :title="operation.title ? operation.title : (operations[operation.id].title + featureOptions.name)"
      :disabled="(operation.permission && !permission[operation.permission]) || (operation.isDisabled && operation.isDisabled(entity))"
      @click.prevent="onClick(operation)">
      <i :class="operation.icon ? operation.icon : operations[operation.id].icon"></i>
      {{operation.text ? (' ' + operation.text) :''}}
    </el-button>
  </el-button-group>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'

  export default Vue.extend({
    data () {
      return {
        operations: {
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
      featureOptions () {
        return this.params.context.featureComponent.featureOptions
      },
      permission () {
        return this.params.context.featureComponent.permission
      },
      entity () {
        return this.params.node.data ? this.params.node.data : {}
      }
    },
    methods: {
      onClick (operation) {
        if (operation.onClick) {
          operation.onClick(this.params, this.entity)
          return
        }

        if (operation.id === 'view') {
          if (this.params.context.featureComponent.onView) {
            this.params.context.featureComponent.onView(this.entity)
          } else if (this.params.context.featureComponent.formOptions) {
            this.params.context.featureComponent.formOptions.operation = 'view'
            this.params.context.featureComponent.formOptions.title = '查看' + this.params.context.featureComponent.featureOptions.name
            this.params.context.featureComponent.formOptions.params = this.entity
            this.params.context.featureComponent.formOptions.isShow = true
          }
        } else if (operation.id === 'edit') {
          if (this.params.context.featureComponent.onEdit) {
            this.params.context.featureComponent.onEdit(this.entity)
          } else if (this.params.context.featureComponent.formOptions) {
            this.params.context.featureComponent.formOptions.operation = 'edit'
            this.params.context.featureComponent.formOptions.title = '修改' + this.params.context.featureComponent.featureOptions.name
            this.params.context.featureComponent.formOptions.params = this.entity
            this.params.context.featureComponent.formOptions.isShow = true
          }
        } else if (operation.id === 'remove') {
          if (this.params.context.featureComponent.onRemove) {
            this.params.context.featureComponent.onRemove(this.entity)
          } else if (this.params.context.featureComponent.gridOptions) {
            var featureName = this.params.context.featureComponent.featureOptions.name
            this.$confirm('确定要删除所选的' + featureName + '吗?', '删除' + featureName, {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              this.$http.delete(this.params.context.featureComponent.featureOptions.url + '/' + this.entity.f_id).then((response) => {
                if (response.body.success && this.params.context.featureComponent.gridOptions) {
                  this.params.context.featureComponent.gridOptions.context.params.totalCount = 0
                  this.params.context.featureComponent.gridOptions.api.setDatasource(this.params.context.featureComponent.gridOptions.datasource)
                }
              })
            })
          }
        }
      }
    }
  })
</script>
