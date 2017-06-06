<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>
  </div>
</template>


<script type="text/ecmascript-6">
  import { AgGridVue } from 'ag-grid-vue'
  import DictFilterFramework from 'components/ag-grid/DictFilterFramework'
  import DictFloatingFilterComponentFramework from 'components/ag-grid/DictFloatingFilterComponentFramework'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import LikeFloatingFilterComponentFramework from 'components/ag-grid/LikeFloatingFilterComponentFramework'
  import IndexRendererFramework from 'components/ag-grid/IndexRendererFramework'

  export default {
    name: 'informationSchema',
    components: {
      'ag-grid-vue': AgGridVue
    },
    data () {
      return {
        featureOptions: {
          name: '数据库列',
          url: 'api/schema/information/columns'
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            featureComponent: this,
            params: {
              orderBy: 'TABLE_SCHEMA,TABLE_NAME,ORDINAL_POSITION',
              totalCount: 0
            }
          }
        })
      }
    },
    computed: {
      contentStyle () {
        return {'padding': '20px', 'height': (this.$store.state.layout.body.height) + 'px'}
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '',
          pinned: 'left',
          checkboxSelection: true,
          // headerCheckboxSelection: true,
          cellStyle: {'text-align': 'center'},
          width: 24
        },
        {
          headerName: '#',
          pinned: 'left',
          cellStyle: {'text-align': 'right'},
          cellRendererFramework: IndexRendererFramework,
          width: 38
        },
        {
          headerName: '数据库名',
          field: 'TABLE_SCHEMA',
          tooltipField: 'TABLE_SCHEMA',
          pinned: 'left',
          suppressFilter: false,
          filterParams: {
            url: 'api/schema/information/schematas',
            codeFiled: 'SCHEMA_NAME',
            textFiled: 'SCHEMA_NAME'
          },
          filterFramework: DictFilterFramework,
          floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
          width: 120
        },
        {
          headerName: '表名',
          field: 'TABLE_NAME',
          tooltipField: 'TABLE_NAME',
          pinned: 'left',
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          width: 240
        },
        {
          headerName: '列名',
          field: 'COLUMN_NAME',
          tooltipField: 'COLUMN_NAME',
          pinned: 'left',
          width: 180
        },
        {
          headerName: '列类型',
          field: 'COLUMN_TYPE',
          width: 120
        },
        {
          headerName: '是否为空',
          field: 'IS_NULLABLE',
          width: 68
        },
        {
          headerName: '默认值',
          field: 'COLUMN_DEFAULT',
          tooltipField: 'COLUMN_DEFAULT',
          width: 120
        },
        {
          headerName: '描述',
          field: 'COLUMN_COMMENT',
          tooltipField: 'COLUMN_COMMENT',
          width: 300
        }
      ]
    }
  }
</script>
