<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <url-detail ref="detail" :detail-options="detailOptions"></url-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    DictFilterFramework,
    DictFloatingFilterComponentFramework,
    DictRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import UrlDetail from './detail'
  //  import {UrlDetail} from 'views'

  export default {
    name: 'urlView',
    mixins: [ViewlMixin],
    components: {
      UrlDetail
    },
    data () {
      return {
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
            name: 'URL',
            url: 'api/platform/sys/urls',
            featureComponent: this,
            getDetailComponent (params) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'f_url,f_methods'
            }
          }
        })
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '',
          pinned: 'left',
          hide: this.mode !== 'selector',
          checkboxSelection: this.mode === 'selector',
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
          headerName: 'URL',
          field: 'f_url',
          tooltipField: 'f_url',
          pinned: 'left',
          suppressSorting: false,
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          cellRendererFramework: ViewRendererFramework,
          width: 300
        },
        {
          headerName: '提交方式',
          field: 'f_methods',
          suppressSorting: false,
          suppressFilter: false,
          filterParams: {dict: 'HttpMethods', filterType: 'String'},
          filterFramework: DictFilterFramework,
          floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
          width: 110
        },
        {
          headerName: '记录日志',
          field: 'f_is_log',
          suppressFilter: false,
          filterFramework: DictFilterFramework,
          floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo2'},
          width: 75
        },
        {
          headerName: '后台处理方法',
          field: 'f_handler_method',
          tooltipField: 'f_handler_method',
          width: 1800
        }
      ]
    }
  }
</script>
