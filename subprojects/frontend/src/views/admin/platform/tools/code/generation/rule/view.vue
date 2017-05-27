<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false" :modal="true"
      :size="'large'" :top="'30px'" :custom-class="'jw-dialog jw-dialog-large'">
      <generation-rule-form :form-options="formOptions" v-if="formOptions.isShow"></generation-rule-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import { AgGridVue } from 'ag-grid-vue'
  import AddHeaderComponenetFramework from 'components/ag-grid/AddHeaderComponenetFramework'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import IndexRendererFramework from 'components/ag-grid/IndexRendererFramework'
  import OperationRendererFramework from 'components/ag-grid/OperationRendererFramework'
  import ViewRendererFramework from 'components/ag-grid/ViewRendererFramework'
  import GenerationRuleForm from './form'

  export default {
    name: 'generationRule',
    components: {
      'ag-grid-vue': AgGridVue,
      GenerationRuleForm
    },
    data () {
      return {
        featureOptions: {
          name: '生成规则',
          url: 'api/platform/tools/code/generate/rules',
          permission: {
            generate: this.$jw.hasPermission('KFGJ-DMSC-SCDM'),
            add: this.$jw.hasPermission('KFGJ-DMSC-ZJ'),
            edit: this.$jw.hasPermission('KFGJ-DMSC-XG'),
            remove: this.$jw.hasPermission('KFGJ-DMSC-SC')
          }
        },
        formOptions: {
          isShow: false,
          operation: 'view',
          title: '查看详情',
          params: {},
          context: {
            featureComponent: this
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            featureComponent: this,
            params: {
              orderBy: 'f_menu_parent_id,f_menu_order',
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
          headerName: '#',
          field: 'f_id',
          pinned: 'left',
          headerComponentFramework: AddHeaderComponenetFramework,
          cellStyle: {'text-align': 'right'},
          cellRendererFramework: IndexRendererFramework,
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 38
        },
        {
          headerName: '规则编码',
          field: 'f_code',
          pinned: 'left',
          filterFramework: LikeFilterFramework,
          cellRendererFramework: ViewRendererFramework,
          width: 150
        },
        {
          headerName: '规则名称',
          field: 'f_name',
          filterFramework: LikeFilterFramework,
          width: 120
        },
        {
          headerName: '菜单编码',
          field: 'f_menu_id',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 120
        },
        {
          headerName: '菜单名称',
          field: 'f_menu_name',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 120
        },
        {
          headerName: '菜单排序',
          field: 'f_menu_order',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 80
        },
        {
          headerName: 'Java包名',
          field: 'f_package_name',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 300
        },
        {
          headerName: 'API地址',
          field: 'f_request_url',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 300
        },
        {
          headerName: '操作',
          field: '',
          pinned: 'right',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: OperationRendererFramework,
          cellRendererParams: {
            operations: [{
              id: 'edit',
              permission: 'edit'
            }, {
              id: 'remove',
              permission: 'remove'
            }, {
              id: 'generate',
              title: '生成代码',
              icon: 'fa fa-file-code-o',
              permission: 'generate',
              onClick (params, entity) {
                params.context.featureComponent.onGenerate(entity)
              }
            }]
          },
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 80
        }
      ]
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      onGenerate (entity) {
        alert('onGenerate')
      }
    }
  }
</script>
