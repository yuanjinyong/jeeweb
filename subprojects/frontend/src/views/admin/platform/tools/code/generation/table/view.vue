<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div>
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions">
    </ag-grid-vue>

    <el-dialog v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false" :modal="false"
      :size="'full'" :custom-class="'jw-dialog jw-dialog-full'">
      <generation-rule-table-form
        :form-options="formOptions"
        :generate-rule="generateRule"
        @submit="onSubmit"
        v-if="formOptions.isShow">
      </generation-rule-table-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import { AgGridVue } from 'ag-grid-vue'
  import AddHeaderComponenetFramework from 'components/ag-grid/AddHeaderComponenetFramework'
  import DictRendererFramework from 'components/ag-grid/DictRendererFramework'
  import OperationRendererFramework from 'components/ag-grid/OperationRendererFramework'
  import ViewRendererFramework from 'components/ag-grid/ViewRendererFramework'
  import GenerationRuleTableForm from './form'

  export default {
    name: 'generateRuleTable',
    components: {
      'ag-grid-vue': AgGridVue,
      GenerationRuleTableForm
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
        featureOptions: {
          name: '生成规则数据库表',
          url: null,
          permission: {
            add: true,
            edit: true,
            remove: true
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
            featureComponent: this
          },
          rowModelType: 'normal',
          rowData: [],
          pagination: false,
          enableFilter: false,
          floatingFilter: false,
          suppressContextMenu: true,
          suppressMenuMainPanel: true,
          suppressMenuColumnPanel: true
        })
      }
    },
    computed: {
      contentStyle () {
        return {'padding': '20px', 'height': (this.$store.state.layout.body.height) + 'px'}
      }
    },
    watch: {
      generateRule: function (val, oldVal) {
        this.gridOptions.api.setRowData(val.tableList)
      }
    },
    created () {
      var vm = this
      this.gridOptions.columnDefs = [
        {
          headerName: '#',
          field: 'f_order',
          pinned: 'left',
          headerComponentFramework: AddHeaderComponenetFramework,
          cellStyle: {'text-align': 'right'},
          // cellRendererFramework: IndexRendererFramework,
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 38
        },
        {
          headerName: '数据库名',
          field: 'f_db_name',
          pinned: 'left',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 120
        },
        {
          headerName: '表名',
          field: 'f_table_name',
          pinned: 'left',
          cellRendererFramework: ViewRendererFramework,
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 180
        },
        {
          headerName: '主表',
          field: 'f_is_main',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 60
        },
        {
          headerName: '实体类名',
          field: 'f_entity_class',
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
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
              isDisabled: function (entity) {
                return vm.operation === 'view'
              }
            }, {
              id: 'remove',
              isDisabled: function (entity) {
                return vm.operation === 'view'
              }
            }]
          },
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 60
        }
      ]
    },
    methods: {
      onSubmit (event) {
        console.debug('onSubmit', event, this.formOptions.params)
      }
    }
  }
</script>
