<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <dict-detail ref="detail" :detail-options="detailOptions"></dict-detail>
    <dict-sql-detail ref="sql" :detail-options="sqlOptions"></dict-sql-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    DictRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    OperationRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import DictDetail from './detail'
  import DictSqlDetail from './sql'
  //  import {DictDetail, DictSqlDetail} from 'views'

  export default {
    name: 'dictView',
    mixins: [ViewlMixin],
    components: {
      DictDetail,
      DictSqlDetail
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
        headerName: '',
        pinned: 'left',
        hide: this.mode !== 'selector',
        checkboxSelection: this.mode === 'selector',
        cellStyle: {'text-align': 'center'},
        width: 24
      }, {
        headerName: '#',
        pinned: 'left',
        headerComponentFramework: this.mode !== 'selector' ? AddHeaderComponenetFramework : null,
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      }, {
        headerName: '编码',
        field: 'f_code',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        cellRendererFramework: ViewRendererFramework,
        width: 160
      }, {
        headerName: '名称',
        field: 'f_name',
        tooltipField: 'f_name',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
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
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: DictRendererFramework,
        cellRendererParams: {dict: 'YesNo2'},
        width: 75
      }, {
        headerName: '备注',
        field: 'f_remark',
        tooltipField: 'f_remark',
        width: 300
      }, {
        headerName: '操作',
        field: '',
        pinned: 'right',
        hide: this.mode === 'selector',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework,
        cellRendererParams: {
          operations: [{
            id: 'edit',
            permission: 'edit'
          }, {
            id: 'remove',
            permission: 'remove',
            isDisabled (params, entity) {
              return entity.f_is_preset === 1
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
