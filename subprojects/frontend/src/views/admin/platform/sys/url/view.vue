<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false"
      :modal="mode !== 'selector'" :size="'small'" :top="mode !== 'selector' ? '30px': '20px'"
      :custom-class="mode !== 'selector' ? 'jw-dialog' : 'jw-dialog jw-sub-dialog'">
      <url-form :form-options="formOptions" v-if="formOptions.isShow"></url-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  // import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import DictFilterFramework from 'components/ag-grid/DictFilterFramework'
  import IndexRendernerFramework from 'components/ag-grid/IndexRendernerFramework'
  import ViewRendernerFramework from 'components/ag-grid/ViewRendernerFramework'
  import DictRendernerFramework from 'components/ag-grid/DictRendernerFramework'
  import UrlForm from './form'

  export default {
    name: 'url',
    components: {
      'ag-grid-vue': AgGridVue,
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
          infiniteBlockSize: 20,
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
          return {'padding': '0px', 'height': (this.$store.state.layout.body.height - 111) + 'px'}
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
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 24
        },
        {
          headerName: '#',
          pinned: 'left',
          cellStyle: {'text-align': 'right'},
          cellRendererFramework: IndexRendernerFramework,
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 38
        },
        {
          headerName: 'URL',
          field: 'f_url',
          pinned: 'left',
          suppressMenu: true,
          cellRendererFramework: ViewRendernerFramework,
          filterFramework: LikeFilterFramework,
          width: 400
        },
        {
          headerName: '提交方式',
          field: 'f_methods',
          filterFramework: DictFilterFramework,
          filterParams: {dict: 'HttpMethods'},
          suppressMenu: true,
          width: 110
        },
        {
          headerName: '后台处理方法',
          field: 'f_handler_method',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 1800
        },
        {
          headerName: '记录日志',
          field: 'f_is_log',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendernerFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 75
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
