<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <dict-detail ref="detail" :detail-options="detailOptions"></dict-detail>
    <dict-sql-detail ref="sql" :detail-options="sqlOptions"></dict-sql-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'settingView',
    mixins: [ViewlMixin],
    components: {
      DictDetail: r => require.ensure([], () => r(require('./Detail')), 'platform-sys-setting'),
      DictSqlDetail: r => require.ensure([], () => r(require('./Sql')), 'platform-sys-setting')
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
          context: {
            name: '系统设置项',
            url: 'api/platform/sys/settings',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'f_order'
            }
          }
        })
      }
    },
    computed: {
      permission () {
        return {
          sql: this.hasPermission('XTGL-XTSZ-DCSQL'),
          add: this.hasPermission('XTGL-XTSZ-ZJ'),
          edit: this.hasPermission('XTGL-XTSZ-XG'),
          remove: this.hasPermission('XTGL-XTSZ-SC')
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [{
        type: ['IndexRender', this.mode !== 'selector' ? 'AddHeader' : 'Null']
      }, {
        headerName: '编码',
        field: 'f_code',
        tooltipField: 'f_code',
        pinned: 'left',
        suppressSorting: false,
        type: ['ViewRender', 'LikeFilter'],
        width: 350
      }, {
        headerName: '名称',
        field: 'f_name',
        tooltipField: 'f_name',
        pinned: 'left',
        suppressSorting: false,
        sortColId: 'convert(f_name USING gbk)',
        type: 'LikeFilter',
        width: 160
      }, {
        headerName: '取值',
        field: 'f_value',
        tooltipField: 'f_value',
        width: 150
      }, {
        headerName: '描述',
        field: 'f_desc',
        tooltipField: 'f_desc',
        suppressSizeToFit: false,
        width: 200
      }, {
        headerName: '备注',
        field: 'f_remark',
        tooltipField: 'f_remark',
        suppressSizeToFit: false,
        width: 200
      }, {
        hide: this.mode === 'selector',
        type: 'OperationRender',
        cellRendererParams: {
          operations: [{
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'remove',
            permission: 'remove',
            isDisabled (params, entity) {
              return entity.f_is_preset === 101
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
        width: 80
      }]
    }
  }
</script>
