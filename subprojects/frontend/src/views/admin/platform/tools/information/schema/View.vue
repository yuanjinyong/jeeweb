<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

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
        hide: this.mode !== 'selector',
        type: 'Checkbox'
      }, {
        type: 'IndexRender'
      }, {
        headerName: '数据库名',
        field: 'TABLE_SCHEMA',
        tooltipField: 'TABLE_SCHEMA',
        pinned: 'left',
        suppressSorting: false,
        type: 'DictFilter',
        filterParams: {
          dictOptions: {
            url: 'api/schema/information/schematas',
            codeFiled: 'SCHEMA_NAME',
            textFiled: 'SCHEMA_NAME'
          }
        },
        width: 120
      }, {
        headerName: '表名',
        field: 'TABLE_NAME',
        tooltipField: 'TABLE_NAME',
        pinned: 'left',
        suppressSorting: false,
        type: 'LikeFilter',
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
        width: 300
      }]
    }
  }
</script>
