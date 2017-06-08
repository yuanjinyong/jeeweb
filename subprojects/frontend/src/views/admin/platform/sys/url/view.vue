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
  import DictFilterFramework from 'components/ag-grid/DictFilterFramework'
  import DictFloatingFilterComponentFramework from 'components/ag-grid/DictFloatingFilterComponentFramework'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import LikeFloatingFilterComponentFramework from 'components/ag-grid/LikeFloatingFilterComponentFramework'
  import IndexRendererFramework from 'components/ag-grid/IndexRendererFramework'
  import ViewRendererFramework from 'components/ag-grid/ViewRendererFramework'
  import DictRendererFramework from 'components/ag-grid/DictRendererFramework'
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
          width: 400
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
