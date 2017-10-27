<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <menu-detail ref="detail" :detail-options="detailOptions"></menu-detail>
    <menu-sql-detail ref="sql" :detail-options="sqlOptions"></menu-sql-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'menuView',
    mixins: [ViewlMixin],
    components: {
      MenuDetail: r => require.ensure([], () => r(require('./Detail')), 'platform-sys-menu'),
      MenuSqlDetail: r => require.ensure([], () => r(require('./Sql')), 'platform-sys-menu')
    },
    data () {
      return {
        sqlOptions: {
          size: 'middle',
          context: {
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
          getNodeChildDetails: function (rowData) {
            if (rowData.children && rowData.children.length > 0) {
              return {
                group: true,
                expanded: rowData.f_type < 2, // 展开到目录和页面
                children: rowData.children,
                field: 'f_id',
                key: rowData.f_id
              }
            } else {
              return null
            }
          },
          context: {
            name: '菜单',
            url: 'api/platform/sys/menus',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              tree: true,
              orderBy: 'f_parent_path,f_order'
            }
          }
        })
      }
    },
    computed: {
      permission () {
        return {
          sql: this.hasPermission('KFGJ-CDGL-DCSQL'),
          add: this.hasPermission('KFGJ-CDGL-ZJ'),
          edit: this.hasPermission('KFGJ-CDGL-XG'),
          remove: this.hasPermission('KFGJ-CDGL-SC')
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        headerName: '编码',
        field: 'f_id',
        pinned: 'left',
        cellRenderer: 'group',
        type: 'LikeFilter',
        width: 200
      }, {
        headerName: '名称',
        field: 'f_name',
        pinned: 'left',
        type: ['ViewRender', 'LikeFilter'],
        cellRendererParams: {
          operation: {
            render (params, entity) {
              if (entity.f_type < 3) {
                let icon = entity.f_icon ? entity.f_icon : (entity.f_type < 2 ? 'fa fa-list' : 'fa fa-file-o')
                return '<i class="' + icon + '" style="min-width:14px;"></i> ' + entity.f_name
              } else {
                return '<div style="min-width:14px;display: inline-block;">&nbsp;</div> ' + entity.f_name
              }
            }
          }
        },
        width: 150
      }, {
        headerName: '类型',
        field: 'f_type',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'MenuType'},
        filterParams: {type: 'in'},
        width: 80
      }, {
        headerName: '排序',
        field: 'f_order',
        cellStyle: {'text-align': 'right'},
        width: 40
      }, {
        headerName: '描述',
        field: 'f_desc',
        tooltipField: 'f_desc',
        suppressSizeToFit: false,
        width: 200
      }, {
        headerName: '路由路径',
        field: 'f_route_path',
        tooltipField: 'f_route_path',
        suppressSizeToFit: false,
        width: 350
      }, {
        headerName: '状态',
        field: 'f_status',
        type: ['DictRender', 'DictFilter'],
        cellRendererParams: {dict: 'YesNo2'},
        width: 64
      }, {
        headerName: '备注',
        field: 'f_remark',
        tooltipField: 'f_remark',
        suppressSizeToFit: false,
        width: 350
      }, {
        hide: this.mode === 'selector',
        type: 'OperationRender',
        cellRendererParams: {
          operations: [{
            id: 'add',
            title: '增加子菜单',
            type: 'primary',
            icon: 'fa fa-plus',
            permission: 'add',
            isDisabled (params, entity) {
              return entity.f_type > 2
            }
          }, {
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'remove',
            title: '删除菜单及其子菜单',
            permission: 'remove',
            isDisabled (params, entity) {
              return !entity.f_parent_id
            }
          }, {
            id: 'sql',
            title: 'SQL脚本',
            icon: 'fa fa-file-code-o',
            permission: 'sql',
            onClick (params, entity) {
              params.context.featureComponent.$refs['sql'].open({
                operation: 'sql',
                title: 'SQL脚本',
                params: entity
              })
            }
          }]
        },
        suppressResize: true,
        width: 96
      }]
    }
  }
</script>
