<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <role-detail ref="detail" :detail-options="detailOptions"></role-detail>
    <jw-authorize ref="authorize" :detail-options="authorizeOptions"></jw-authorize>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'roleView',
    mixins: [ViewlMixin],
    components: {
      RoleDetail: r => require.ensure([], () => r(require('./Detail')), 'platform-sys-role')
    },
    data () {
      return {
        authorizeOptions: {
          context: {
            url: 'api/platform/sys/roles',
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        detailOptions: {
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '角色',
            url: 'api/platform/sys/roles',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'f_is_preset,f_name'
            }
          }
        })
      }
    },
    computed: {
      curUser () {
        return this.$store.state.user || {}
      },
      permission () {
        return {
          authorize: this.hasPermission('XTGL-JSGL-SQ'),
          add: this.hasPermission('XTGL-JSGL-ZJ'),
          edit: this.hasPermission('XTGL-JSGL-XG'),
          remove: this.hasPermission('XTGL-JSGL-SC')
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        hide: this.mode !== 'selector',
        type: 'Checkbox'
      }, {
        type: ['IndexRender', this.mode !== 'selector' ? 'AddHeader' : 'Null']
      }, {
        headerName: '角色名称',
        field: 'f_name',
        pinned: 'left',
        suppressSorting: false,
        sortColId: 'convert(f_name USING gbk)',
        type: ['ViewRender', 'LikeFilter'],
        width: 160
      }, {
        headerName: '角色描述',
        field: 'f_desc',
        tooltipField: 'f_desc',
        width: 300
      }, {
        headerName: '是否预置',
        field: 'f_is_preset',
        type: 'DictRender',
        cellRendererParams: {dict: 'YesNo2'},
        width: 75
      }, {
        headerName: '备注',
        field: 'f_remark',
        tooltipField: 'f_remark',
        suppressSizeToFit: false,
        width: 300
      }, {
        hide: this.mode === 'selector',
        type: 'OperationRender',
        cellRendererParams: {
          operations: [{
            id: 'authorize',
            title: '授权可以操作的功能',
            type: 'warning',
            icon: 'fa fa-key',
            permission: 'authorize',
            isDisabled (params, entity) {
              let user = params.context.featureComponent.curUser
              return entity.f_id === 1 && !user.superAdmin // 只有超级管理员账号才能给系统管理员角色授权
            },
            onClick (params, entity) {
              params.context.featureComponent.$refs['authorize'].open({
                operation: 'authorize',
                title: '授权可以操作的功能',
                params: entity
              })
            }
          }, {
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'remove',
            permission: 'remove',
            isDisabled (params, entity) {
              return entity.f_is_preset === 1
            }
          }]
        },
        width: 80
      }]
    }
  }
</script>
