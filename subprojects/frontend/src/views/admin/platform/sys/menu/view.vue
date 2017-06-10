<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <el-dialog v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false" :modal="true"
               :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <menu-form :form-options="formOptions" @submit="onSaved" v-if="formOptions.isShow"></menu-form>
    </el-dialog>

    <el-dialog v-model="sqlFormOptions.isShow" :title="sqlFormOptions.title" :close-on-click-modal="false" :modal="true"
               :size="'large'" :top="'30px'" :custom-class="'jw-dialog'">
      <menu-sql-form :form-options="sqlFormOptions" v-if="sqlFormOptions.isShow"></menu-sql-form>
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
    OperationRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import MenuForm from './form'
  import MenuSqlForm from './sql'
  //  import {MenuForm, MenuSqlForm} from 'views'

  export default {
    name: 'menuView',
    components: {
      MenuForm,
      MenuSqlForm
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
          enableServerSideFilter: false,
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
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          cellRenderer: 'group',
          width: 200
        },
        {
          headerName: '名称',
          field: 'f_name',
          pinned: 'left',
          suppressFilter: true,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          cellRendererFramework: ViewRendererFramework,
          width: 150
        },
        {
          headerName: '类型',
          field: 'f_type',
          suppressFilter: true,
          filterFramework: DictFilterFramework,
          filterParams: {
            type: 'in',
            doesFilterPass: function (params, value, filterPassParams) {
              console && console.info('类型 doesFilterPass', filterPassParams.data.f_name, filterPassParams.node)
              return true
            }
          },
          floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'MenuType'},
          width: 80
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
          suppressFilter: false,
          filterFramework: DictFilterFramework,
          floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          width: 64
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
              isDisabled: function (params, entity) {
                return entity.f_type > 2
              }
            }, {
              id: 'edit',
              permission: 'edit'
            }, {
              id: 'remove',
              title: '删除菜单及其子菜单',
              permission: 'remove',
              isDisabled: function (params, entity) {
                return !entity.f_parent_id
              },
              onClick (params, entity) {
                params.context.featureComponent.onRemove(entity)
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
