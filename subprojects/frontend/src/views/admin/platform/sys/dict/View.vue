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
    name: 'dictView',
    mixins: [ViewlMixin],
    components: {
      DictDetail: r => require.ensure([], () => r(require('./Detail')), 'platform-sys-dict'),
      DictSqlDetail: r => require.ensure([], () => r(require('./Sql')), 'platform-sys-dict')
    },
    data () {
      return {
        sqlOptions: {
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
            name: '字典组',
            url: 'api/platform/sys/dicts',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'f_code'
            }
          }
        })
      }
    },
    computed: {
      permission () {
        return {
          sql: this.hasPermission('XTGL-ZDGL-DCSQL'),
          add: this.hasPermission('XTGL-ZDGL-ZJ'),
          edit: this.hasPermission('XTGL-ZDGL-XG'),
          remove: this.hasPermission('XTGL-ZDGL-SC')
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
        headerName: '编码',
        field: 'f_code',
        pinned: 'left',
        suppressSorting: false,
        type: ['ViewRender', 'LikeFilter'],
        width: 160
      }, {
        headerName: '名称',
        field: 'f_name',
        tooltipField: 'f_name',
        pinned: 'left',
        sortColId: 'convert(f_name USING gbk)',
        type: 'LikeFilter',
        width: 200
      }, {
        headerName: '数据库名',
        field: 'f_db_name',
        tooltipField: 'f_db_name',
        width: 150
      }, {
        headerName: '数据表名',
        field: 'f_table_name',
        tooltipField: 'f_table_name',
        width: 200
      }, {
        headerName: '字典项编码字段',
        field: 'f_code_column',
        tooltipField: 'f_code_column',
        width: 120
      }, {
        headerName: '字典项名称字段',
        field: 'f_name_column',
        tooltipField: 'f_name_column',
        width: 120
      }, {
        headerName: '字典项排序字段',
        field: 'f_order_column',
        tooltipField: 'f_order_column',
        width: 120
      }, {
        headerName: '查询Where条件',
        field: 'f_where_clause',
        tooltipField: 'f_where_clause',
        width: 200
      }, {
        headerName: '是否预置',
        field: 'f_is_preset',
        type: 'DictRender',
        cellRendererParams: {dict: 'TrueFalse'},
        width: 75
      }, {
        headerName: '备注',
        field: 'f_remark',
        tooltipField: 'f_remark',
        suppressSizeToFit: false,
        minWidth: 40,
        width: 300
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
