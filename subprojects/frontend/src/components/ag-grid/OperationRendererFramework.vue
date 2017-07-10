<template>
  <el-button-group>
    <el-button v-for="(operation, index) in operations" :key="operation.id"
               size="mini"
               :type="operation.type"
               :title="operation.title"
               :disabled="!hasPermission(operation) || isDisabled(operation)"
               @click.prevent="onClick(operation)">
      <i :class="operation.icon" style="min-width:12px;"></i>{{operation.text ? (' ' + operation.text) : ''}}
    </el-button>
  </el-button-group>
</template>

<script>
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
          },
          audit: {
            title: '审核',
            type: 'success',
            icon: 'fa fa-check',
            permission: 'audit'
          }
        }
      }
    },
    computed: {
      entity () {
        return this.params.node && this.params.node.data ? this.params.node.data : {}
      }
    },
    created () {
      this.operations = []
      this.params.operations.forEach((operation) => {
        let defaultOperation = this.defaultOperations[operation.id] || {title: ''}
        this.operations.push(Vue.lodash.merge({}, defaultOperation, {title: defaultOperation.title + this.params.context.name}, operation))
      })
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

        if (operation.id !== 'remove') {
          let detailComponent = this.params.context.getDetailComponent && this.params.context.getDetailComponent.call(this, this.params, operation)
          if (detailComponent) {
            detailComponent.open({
              operation: operation.id,
              title: operation.title,
              params: this.entity
            })
            return
          }

          return
        }

        this.$confirm('确定要删除所选的' + operation.title.substring(2) + '吗?', operation.title, {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http.delete(this.params.context.url + '/' + this.entity.f_id).then((response) => {
            if (response.body.success) {
              this._refreshGrid()
            }
          })
        }).catch((e) => {
          // console && console.error(e)
        })
      },
      _refreshGrid () {
        let gridOptions = this.params.api.gridOptionsWrapper.gridOptions
        if (gridOptions.rowModelType === 'normal') {
          gridOptions.getRows4Normal()
        } else {
          gridOptions.context.params.totalCount = 0
          gridOptions.api.setDatasource(gridOptions.datasource)
        }
      }
    }
  })
</script>
