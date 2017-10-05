<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <schema-table-detail ref="detail" :detail-options="detailOptions"></schema-table-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    DictFilterFramework,
    DictFloatingFilterComponentFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    OperationRendererFramework,
    TimestampRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'

  export default {
    name: 'schemaTableView',
    mixins: [ViewlMixin],
    components: {
      SchemaTableDetail: r => require.ensure([], () => r(require('./Detail')), 'platform-tools-schema-table')
    },
    data () {
      return {
        detailOptions: {
          size: 'large',
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '数据库表',
            url: 'api/schema/information/tables',
            featureComponent: this,
            getPermissions (params, operation) {
              return {rows: true, add: true, edit: true, remove: true}
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'TABLE_SCHEMA,TABLE_NAME'
            }
          }
        })
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
        headerName: '数据库',
        field: 'TABLE_SCHEMA',
        tooltipField: 'TABLE_SCHEMA',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterParams: {
          dictOptions: {
            url: 'api/schema/information/schematas',
            codeFiled: 'SCHEMA_NAME',
            textFiled: 'SCHEMA_NAME'
          }
        },
        filterFramework: DictFilterFramework,
        floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
        width: 120
      }, {
        headerName: '数据库表',
        field: 'TABLE_NAME',
        tooltipField: 'TABLE_NAME',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        cellRendererFramework: ViewRendererFramework,
        width: 200
      }, {
        headerName: '类型',
        field: 'TABLE_TYPE',
        tooltipField: 'TABLE_TYPE',
        suppressFilter: false,
        filterParams: {
          dict: {
            'BASE TABLE': '表',
            'VIEW': '视图',
            'SYSTEM VIEW': '系统视图'
          }
        },
        filterFramework: DictFilterFramework,
        floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
        width: 100
      }, {
        headerName: '引擎',
        field: 'ENGINE',
        tooltipField: 'ENGINE',
        width: 80
      }, {
        headerName: '记录数',
        field: 'TABLE_ROWS',
        width: 80
      }, {
        headerName: '自增',
        field: 'AUTO_INCREMENT',
        width: 80
      }, {
        headerName: '创建时间',
        field: 'CREATE_TIME',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140 // 有filter的为190，没有的为140
      }, {
        headerName: '修改时间',
        field: 'UPDATE_TIME',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: TimestampRendererFramework,
        width: 140 // 有filter的为190，没有的为140
      }, {
        headerName: '描述',
        pinned: 'right',
        field: 'TABLE_COMMENT',
        tooltipField: 'TABLE_COMMENT',
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        width: 180
      }, {
        headerName: '操作',
        field: '',
        pinned: 'right',
        hide: this.mode === 'selector',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework,
        cellRendererParams: {
          operations: [{
            id: 'rows',
            title: '表数据',
            type: '',
            icon: 'fa fa-bars',
            permission: 'rows',
            isDisabled (params, entity) {
              return entity.TABLE_ROWS === 0
            },
            onClick (params, entity) {
              params.context.featureComponent.$refs['authorize'].open({
                operation: 'authorize',
                title: '授权可以操作的功能',
                params: entity
              })
            }
          }, {
            id: 'edit'
          }, {
            id: 'remove'
          }]
        },
        width: 76
      }]
    }
  }
</script>
