<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <menu-detail ref="detail" :detail-options="detailOptions"></menu-detail>
    <menu-sql-detail ref="sql" :detail-options="sqlOptions"></menu-sql-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    DictFilterFramework,
    DictFloatingFilterComponentFramework,
    DictRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    OperationRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import MenuDetail from './detail'
  import MenuSqlDetail from './sql'
  //  import {MenuDetail, MenuSqlDetail} from 'views'

  export default {
    name: 'menuView',
    mixins: [ViewlMixin],
    components: {
      MenuDetail,
      MenuSqlDetail
    },
    data () {
      return {
        sqlOptions: {
          size: 'middle',
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          }
        },
        detailOptions: {
          context: {
            featureComponent: this,
            getGridComponent (options) {
              return options.context.featureComponent.$refs['grid']
            }
          },
          submitted (result) {
            this.options.context.featureComponent._loadMenuList()
          }
        },
        gridOptions: this.$grid.buildOptions({
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
          },
          context: {
            name: '菜单',
            url: 'api/platform/sys/menus',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'f_is_preset,f_name'
            }
          }
        })
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
          cellRendererParams: {
            operation: {
              render (params, entity) {
                if (entity.f_type < 3) {
                  let icon = entity.f_icon ? entity.f_icon : (entity.f_type < 2 ? 'fa fa-list' : 'fa fa-file-o')
                  return '<i class="' + icon + '" style="min-width:14px;"></i> ' + entity.f_name
                } else {
                  return '<div style="min-width:14px;display: inline-block;">&nbsp;</div> ' + entity.f_name
                }
              }
            }
          },
          width: 150
        },
        {
          headerName: '类型',
          field: 'f_type',
          suppressFilter: true,
          filterFramework: DictFilterFramework,
          filterParams: {
            type: 'in',
            doesFilterPass (params, value, filterPassParams) {
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
          cellRendererParams: {dict: 'YesNo2'},
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
          hide: this.mode === 'selector',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: OperationRendererFramework,
          cellRendererParams: {
            operations: [{
              id: 'add',
              title: '增加子菜单',
              type: 'primary',
              icon: 'fa fa-plus',
              permission: 'add',
              isDisabled (params, entity) {
                return entity.f_type > 2
              }
            }, {
              id: 'edit',
              permission: 'edit'
            }, {
              id: 'remove',
              title: '删除菜单及其子菜单',
              permission: 'remove',
              isDisabled (params, entity) {
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
                params.context.featureComponent.$refs['sql'].open({
                  operation: 'sql',
                  title: 'SQL脚本',
                  params: entity
                })
              }
            }]
          },
          width: 96
        }
      ]

      this._loadMenuList()
    },
    methods: {
      _loadMenuList () {
        this.$http.get(this.gridOptions.context.url, {params: {orderBy: 'f_parent_path,f_order'}}).then((response) => {
          if (response.body.success) {
            this.gridOptions.api.setRowData(response.body.data)
          } else {
            this.gridOptions.api.setRowData([])
          }
        })
      },
      onRemove (entity) {
        this.$confirm('确定要删除所选的菜单及其子菜单吗?', '删除菜单', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(function () {
          this.$http.delete(this.featureOptions.url + '/' + entity.f_id).then((response) => {
            if (response.body.success) {
              this._loadMenuList()
            }
          })
        })
      }
    }
  }
</script>
