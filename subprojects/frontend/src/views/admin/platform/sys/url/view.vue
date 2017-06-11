<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <el-dialog v-draggable v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false"
               :modal="mode !== 'selector'" :size="'small'" :top="mode !== 'selector' ? '30px': '20px'"
               :custom-class="mode !== 'selector' ? 'jw-dialog' : 'jw-dialog jw-sub-dialog'">
      <url-form :form-options="formOptions" v-if="formOptions.isShow"></url-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import {
    DictFilterFramework,
    DictFloatingFilterComponentFramework,
    DictRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import UrlForm from './form'
  //  import {UrlForm} from 'views'

  export default {
    name: 'urlView',
    components: {
      UrlForm
    },
    props: {
      mode: {type: String, default: ''}
    },
    data () {
      return {
        featureOptions: {
          name: 'URL',
          url: 'api/platform/sys/urls'
        },
        formOptions: {
          isShow: false,
          operation: 'view',
          title: '查看URL',
          maxHeight: this.mode === 'selector' ? 400 : 500,
          params: {},
          context: {
            featureComponent: this
          }
        },
        gridOptions: this.$grid.buildOptions({
          paginationAutoPageSize: false,
          paginationPageSize: this.mode === 'selector' ? 10 : 20,
          cacheBlockSize: 20,
          context: {
            featureComponent: this,
            params: {
              orderBy: 'f_url,f_methods',
              totalCount: 0
            }
          }
        })
      }
    },
    computed: {
      contentStyle () {
        if (this.mode === 'selector') {
          return {'padding': '20px', 'height': '457px'}
        } else {
          return {'padding': '20px', 'height': this.$store.state.layout.body.height + 'px'}
        }
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '',
          checkboxSelection: true,
          // headerCheckboxSelection: true,
          pinned: 'left',
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
          cellRendererParams: {dict: 'YesNo'},
          width: 75
        },
        {
          headerName: '后台处理方法',
          field: 'f_handler_method',
          tooltipField: 'f_handler_method',
          width: 1800
        }
      ]
    },
    mounted () {
      window.devMode && console && console.info('mounted', this.$options.name, this._uid)
      this._init()
    },
    activated () {
      window.devMode && console && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      _init () {
      },
      getSelectedRows () {
        return this.gridOptions.api.getSelectedRows()
      },
      clearSelectedRows () {
        return this.gridOptions.api.deselectAll()
      }
    }
  }
</script>
