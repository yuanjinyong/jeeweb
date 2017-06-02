<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false" :modal="true"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <menu-form :form-options="formOptions" @submit="onSaved" v-if="formOptions.isShow"></menu-form>
    </el-dialog>

    <el-dialog v-model="sqlFormOptions.isShow" :title="sqlFormOptions.title" :close-on-click-modal="false" :modal="true"
      :size="'large'" :top="'30px'" :custom-class="'jw-dialog'">
      <sql-form :form-options="sqlFormOptions" v-if="sqlFormOptions.isShow"></sql-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import DictRendererFramework from 'components/ag-grid/DictRendererFramework'
  import OperationRendererFramework from 'components/ag-grid/OperationRendererFramework'
  import ViewRendererFramework from 'components/ag-grid/ViewRendererFramework'
  import MenuForm from './form'
  import SqlForm from './sql'

  export default {
    name: 'menu',
    components: {
      'ag-grid-vue': AgGridVue,
      MenuForm,
      SqlForm
    },
    data () {
      return {
        featureOptions: {
          name: '菜单',
          url: 'api/platform/sys/menus'
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
              orderBy: 'f_is_preset,f_name',
              totalCount: 0
            }
          },
          rowModelType: 'normal',
          rowData: [],
          pagination: false,
          getNodeChildDetails: function (rowData) {
            if (rowData.children && rowData.children.length > 0) {
              return {
                group: true,
                expanded: rowData.f_type < 2, // 展开到目录和页面
                children: rowData.children,
                field: 'f_id',
                key: rowData.f_id
              }
            } else {
              return null
            }
          }
        }),
        sqlFormOptions: {
          isShow: false,
          operation: 'view',
          title: 'SQL脚本',
          params: {},
          context: {
            featureComponent: this
          }
        },
        url: 'api/platform/sys/menus',
        showFormDialog: false,
        formDialogTitle: '查看菜单',
        formParams: {
          operation: 'view',
          entity: {}
        },
        sqlDialog: {
          shown: false,
          title: 'SQL脚本',
          params: {
            entity: {}
          }
        }
      }
    },
    computed: {
      permission () {
        return {
          sql: this.hasPermission('KFGJ-CDGL-DCSQL'),
          add: this.hasPermission('KFGJ-CDGL-ZJ'),
          edit: this.hasPermission('KFGJ-CDGL-XG'),
          remove: this.hasPermission('KFGJ-CDGL-SC')
        }
      },
      contentStyle () {
        return {'padding': '20px', 'height': (this.$store.state.layout.body.height) + 'px'}
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '编码',
          field: 'f_id',
          pinned: 'left',
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          cellRenderer: 'group',
          width: 200
        },
        {
          headerName: '名称',
          field: 'f_name',
          pinned: 'left',
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          cellRendererFramework: ViewRendererFramework,
          width: 150
        },
        {
          headerName: '类型',
          field: 'f_type',
          suppressFilter: false,
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'MenuType'},
          filterFramework: Vue.extend({
            template: `<el-select :ref="'input'" v-model="text" clearable>
                            <el-option v-for="item in items" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code"></el-option>
                         </el-select>`,
            data () {
              return {
                items: [
                  {
                    f_item_code: 0,
                    f_item_text: '根'
                  },
                  {
                    f_item_code: 1,
                    f_item_text: '目录'
                  },
                  {
                    f_item_code: 2,
                    f_item_text: '页面'
                  },
                  {
                    f_item_code: 3,
                    f_item_text: '按钮'
                  },
                  {
                    f_item_code: 4,
                    f_item_text: '令牌'
                  }
                ],
                text: null,
                valueGetter: null
              }
            },
            watch: {
              'text': function (val, oldVal) {
                if (val !== oldVal) {
                  this.params.filterChangedCallback()
                }
              }
            },
            created () {
              this.valueGetter = this.params.valueGetter
            },
            methods: {
              isFilterActive () {
                return this.text !== undefined && this.text !== null && this.text !== ''
              },
              doesFilterPass (params) {
                window.devMode && console.info('doesFilterPass', params.data.f_id, this.valueGetter(params.node), this.text)
                return this.valueGetter(params.node) === this.text
              },
              getModel () {
                return {filter: this.text, filterType: 'text', type: 'equals'}
              },
              setModel (model) {
                this.text = model.filter
              },
              afterGuiAttached () {
                // console.debug('afterGuiAttached', this.$refs.input)
                // this.$refs.input.focus()
              }
            }
          }),
          width: 54
        },
        {
          headerName: '排序',
          field: 'f_order',
          cellStyle: {'text-align': 'right'},
          width: 40
        },
        {
          headerName: '描述',
          field: 'f_desc',
          tooltipField: 'f_desc',
          width: 200
        },
        {
          headerName: '路由路径',
          field: 'f_route_path',
          tooltipField: 'f_route_path',
          width: 350
        },
        {
          headerName: '状态',
          field: 'f_status',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          suppressFilter: false,
          filterFramework: Vue.extend({
            template: `<el-select :ref="'input'" v-model="text" clearable>
                            <el-option v-for="item in items" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code"></el-option>
                          </el-select>`,
            data () {
              return {
                items: [{f_item_code: 1, f_item_text: '是'}, {f_item_code: 2, f_item_text: '否'}],
                text: null,
                valueGetter: null
              }
            },
            watch: {
              'text': function (val, oldVal) {
                if (val !== oldVal) {
                  this.params.filterChangedCallback()
                }
              }
            },
            created () {
              this.valueGetter = this.params.valueGetter
            },
            methods: {
              isFilterActive () {
                return this.text !== undefined && this.text !== null && this.text !== ''
              },
              doesFilterPass (params) {
                // window.devMode && console.info('doesFilterPass', params, this.valueGetter(params.node), this.text)
                return this.valueGetter(params.node) === this.text
              },
              getModel () {
                return {filter: this.text, filterType: 'text', type: 'equals'}
              },
              setModel (model) {
                this.text = model.filter
              },
              afterGuiAttached () {
                // console.debug('afterGuiAttached', this.$refs.input)
                // this.$refs.input.focus()
              }
            }
          }),
          width: 54
        },
        {
          headerName: '备注',
          field: 'f_remark',
          tooltipField: 'f_remark',
          width: 350
        },
        {
          headerName: '操作',
          field: '',
          pinned: 'right',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: OperationRendererFramework,
          cellRendererParams: {
            operations: [{
              id: 'add',
              title: '增加子菜单',
              type: 'primary',
              icon: 'fa fa-plus',
              permission: 'add',
              isDisabled: function (entity) {
                return entity.f_type > 2
              },
              onClick (params, entity) {
                params.context.featureComponent.onAdd(entity)
              }
            }, {
              id: 'edit',
              permission: 'edit'
            }, {
              id: 'remove',
              permission: 'remove',
              isDisabled: function (entity) {
                return !entity.f_parent_id
              }
            }, {
              id: 'sql',
              title: 'SQL脚本',
              icon: 'fa fa-file-code-o',
              permission: 'sql',
              onClick (params, entity) {
                params.context.featureComponent.onSql(entity)
              }
            }]
          },
          width: 96
        }
      ]
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
      this._init()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      _init () {
        this._query()
      },
      _query () {
        var vm = this
        vm.$http.get(vm.url, {params: {orderBy: 'f_parent_path,f_order'}}).then(function (response) {
          if (response.body.success) {
            this.gridOptions.api.setRowData(response.body.data)
          } else {
            this.gridOptions.api.setRowData([])
          }
        })
      },
      onAdd (entity) {
        this.formOptions.operation = 'add'
        this.formOptions.params = entity
        this.formOptions.title = '增加菜单'
        this.formOptions.isShow = true
      },
      onRemove (entity) {
        var vm = this
        vm.$confirm('确定要删除所选的菜单及其子菜单吗?', '删除菜单', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          vm.$http.delete(vm.featureOptions.url + '/' + entity.f_id).then(function (response) {
            if (response.body.success) {
              vm._query()
            }
          })
        })
      },
      onSql (entity) {
        this.sqlFormOptions.params = entity
        this.sqlFormOptions.isShow = true
      },
      onSaved () {
        this._query()
      }
    }
  }
</script>
