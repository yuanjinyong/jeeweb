<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" id="generationRuleGrid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <generation-rule-detail ref="detail" :detail-options="detailOptions"></generation-rule-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    OperationRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import GenerationRuleDetail from './detail'
  //  import {GenerationRuleDetail} from 'views'

  export default {
    name: 'generationRuleView',
    mixins: [ViewlMixin],
    components: {
      GenerationRuleDetail
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
            name: '代码生成规则',
            url: 'api/platform/tools/code/generate/rules',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'f_menu_parent_id,f_menu_order'
            }
          }
        })
      }
    },
    computed: {
      permission () {
        return {
          generate: this.hasPermission('KFGJ-DMSC-SCDM'),
          add: this.hasPermission('KFGJ-DMSC-ZJ'),
          edit: this.hasPermission('KFGJ-DMSC-XG'),
          remove: this.hasPermission('KFGJ-DMSC-SC')
        }
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
          width: 38
        },
        {
          headerName: '规则编码',
          field: 'f_code',
          pinned: 'left',
          suppressSorting: false,
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          cellRendererFramework: ViewRendererFramework,
          width: 150
        },
        {
          headerName: '规则名称',
          field: 'f_name',
          suppressSorting: false,
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          width: 120
        },
        {
          headerName: '菜单编码',
          field: 'f_menu_id',
          width: 120
        },
        {
          headerName: '菜单名称',
          field: 'f_menu_name',
          width: 120
        },
        {
          headerName: '菜单路径',
          field: 'f_menu_parent_path',
          suppressSorting: false,
          width: 240
        },
        {
          headerName: '菜单排序',
          field: 'f_menu_order',
          suppressSorting: false,
          width: 82
        },
        {
          headerName: 'Java包名',
          field: 'f_package_name',
          tooltipField: 'f_package_name',
          width: 300
        },
        {
          headerName: 'API地址',
          field: 'f_request_url',
          tooltipField: 'f_request_url',
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
          width: 80
        }
      ]
    },
    methods: {
      onGenerate (entity) {
        let loading = this.$loading({text: '代码生成中……', target: '#generationRuleGrid'})
        this.$http.put(this.gridOptions.context.url + '/' + entity.f_id + '/generate').then(() => {
          loading.close()
        }).catch(() => {
          loading.close()
        })
      }
    }
  }
</script>
