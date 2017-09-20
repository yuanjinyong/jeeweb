<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    DictFilterFramework,
    DictFloatingFilterComponentFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework
  } from 'components/ag-grid'

  export default {
    name: 'informationSchemaView',
    mixins: [ViewlMixin],
    data () {
      return {
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '数据库列',
            url: 'api/schema/information/columns',
            featureComponent: this,
            params: {
              orderBy: 'TABLE_SCHEMA,TABLE_NAME,ORDINAL_POSITION'
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
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      }, {
        headerName: '数据库名',
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
        headerName: '表名',
        field: 'TABLE_NAME',
        tooltipField: 'TABLE_NAME',
        pinned: 'left',
        suppressSorting: false,
        suppressFilter: false,
        filterFramework: LikeFilterFramework,
        floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
        width: 240
      }, {
        headerName: '列名',
        field: 'COLUMN_NAME',
        suppressSorting: false,
        tooltipField: 'COLUMN_NAME',
        pinned: 'left',
        width: 180
      }, {
        headerName: '列类型',
        field: 'COLUMN_TYPE',
        width: 120
      }, {
        headerName: '是否为空',
        field: 'IS_NULLABLE',
        width: 68
      }, {
        headerName: '默认值',
        field: 'COLUMN_DEFAULT',
        tooltipField: 'COLUMN_DEFAULT',
        width: 120
      }, {
        headerName: '描述',
        field: 'COLUMN_COMMENT',
        tooltipField: 'COLUMN_COMMENT',
        suppressSizeToFit: false,
        minWidth: 40,
        width: 300
      }]
    }
  }
</script>
