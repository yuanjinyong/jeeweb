<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <url-detail ref="detail" :detail-options="detailOptions"></url-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'urlView',
    mixins: [ViewlMixin],
    components: {
      UrlDetail: r => require.ensure([], () => r(require('./Detail')), 'platform-sys-url')
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
      this.gridOptions.columnDefs = [{
        hide: this.mode !== 'selector',
        type: 'Checkbox'
      }, {
        type: ['IndexRender']
      }, {
        headerName: 'URL',
        field: 'f_url',
        tooltipField: 'f_url',
        pinned: 'left',
        suppressSorting: false,
        type: ['ViewRender', 'LikeFilter'],
        width: 300
      }, {
        headerName: '提交方式',
        field: 'f_methods',
        suppressSorting: false,
        filterParams: {dict: 'HttpMethods', filterType: 'String'},
        type: ['DictFilter'],
        width: 110
      }, {
        headerName: '记录日志',
        field: 'f_is_log',
        cellRendererParams: {dict: 'YesNo2'},
        type: ['DictRender', 'DictFilter'],
        width: 75
      }, {
        headerName: '后台处理方法',
        field: 'f_handler_method',
        tooltipField: 'f_handler_method',
        suppressSizeToFit: false,
        width: 300
      }]
    }
  }
</script>
