<template>
  <div>
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <generation-rule-table-detail ref="detail" :detail-options="detailOptions" :generate-rule="generateRule">
    </generation-rule-table-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    DictRendererFramework,
    OperationRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import GenerationRuleTableDetail from './detail'
  //  import {GenerationRuleTableDetail} from 'views'

  export default {
    name: 'generationRuleTableView',
    mixins: [ViewlMixin],
    components: {
      GenerationRuleTableDetail
    },
    props: {
      operation: {
        type: String,
        default: 'view'
      },
      generateRule: {
        type: Object,
        default: function () {
          return {
            tableList: []
          }
        }
      }
    },
    data () {
      return {
        detailOptions: {
          size: 'full',
          modal: false,
          draggable: false,
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          },
          submitted (result) {
            let entity = result.data
            let vm = this.options.context.featureComponent
            if (result.operation === 'add') {
              vm.generateRule.tableList.push(entity)
            } else {
              for (let i = 0; i < vm.generateRule.tableList.length; i++) {
                if (vm.generateRule.tableList[i].f_db_name === entity.f_db_name &&
                  vm.generateRule.tableList[i].f_table_name === entity.f_table_name) {
                  vm.generateRule.tableList.splice(i, 1, entity)
                  break
                }
              }
            }

            vm.gridOptions.api.setRowData(vm.generateRule.tableList)
          }
        },
        gridOptions: this.$grid.buildOptions({
          rowModelType: 'normal',
          rowData: [],
          pagination: false,
          enableFilter: false,
          context: {
            name: '生成规则数据库表',
            url: null,
            featureComponent: this,
            getPermissions (params, operation) {
              return {
                add: true,
                edit: true,
                remove: true
              }
            },
            getDetailComponent (params) {
              return params.context.featureComponent.$refs['detail']
            }
          }
        })
      }
    },
    watch: {
      generateRule: function (val, oldVal) {
        this.gridOptions.api.setRowData(val.tableList || [])
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '#',
          field: 'f_order',
          pinned: 'left',
          headerComponentFramework: AddHeaderComponenetFramework,
          headerComponentParams: {
            operation: {
              permission: '',
              isDisabled (params, entity) {
                return params.context.featureComponent.operation === 'view'
              }
            }
          },
          cellStyle: {'text-align': 'right'},
          width: 38
        },
        {
          headerName: '数据库名',
          field: 'f_db_name',
          pinned: 'left',
          width: 120
        },
        {
          headerName: '表名',
          field: 'f_table_name',
          pinned: 'left',
          cellRendererFramework: ViewRendererFramework,
          width: 180
        },
        {
          headerName: '主表',
          field: 'f_is_main',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          width: 60
        },
        {
          headerName: 'Entity类名',
          field: 'f_entity_class',
          tooltipField: 'f_entity_class',
          width: 400
        },
        {
          headerName: 'API类名',
          field: 'f_rest_class',
          tooltipField: 'f_rest_class',
          width: 400
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
              isDisabled (params, entity) {
                return params.context.featureComponent.operation === 'view'
              }
            }, {
              id: 'remove',
              isDisabled (params, entity) {
                return params.context.featureComponent.operation === 'view'
              },
              onClick (params, entity) {
                params.context.featureComponent.onRemove(entity)
              }
            }]
          },
          width: 60
        }
      ]
    },
    methods: {
      onRemove (entity) {
        let featureName = this.gridOptions.context.name
        this.$confirm('确定要删除所选的' + featureName + '吗?', '删除' + featureName, {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let tableList = this.generateRule.tableList
          for (let i = 0; i < tableList.length; i++) {
            let table = tableList[i]
            if (table.f_db_name === entity.f_db_name && table.f_table_name === entity.f_table_name) {
              tableList.splice(i, 1)
              break
            }
          }
          this.gridOptions.api.setRowData(tableList)
        })
      }
    }
  }
</script>
