<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="showFormDialog" :title="formDialogTitle" :close-on-click-modal="false" :size="'small'"
      :top="'30px'" :custom-class="'jw-dialog'">
      <menu-form :params="formParams" @cancel="showFormDialog = false" @submit="onSaved"
        v-if="showFormDialog"></menu-form>
    </el-dialog>

    <el-dialog v-model="sqlDialog.shown" :title="sqlDialog.title" :close-on-click-modal="false"
      :size="'large'" :top="'30px'" :custom-class="'jw-dialog'">
      <sql-form :params="sqlDialog.params"
        @cancel="sqlDialog.shown = false"
        @submit="sqlDialog.shown = false"
        v-if="sqlDialog.shown">
      </sql-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'
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
        url: 'api/platform/sys/menus',
        gridOptions: null,
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
      contentStyle () {
        return {'padding': '20px', 'height': (this.$store.state.layout.body.height) + 'px'}
      }
    },
    beforeMount () {
      var vm = this
      vm.gridOptions = {
        context: {
          parentComponent: this,
          permission: {
            sql: this.$jw.hasPermission('KFGJ-CDGL-DCSQL'),
            add: this.$jw.hasPermission('KFGJ-CDGL-ZJ'),
            edit: this.$jw.hasPermission('KFGJ-CDGL-XG'),
            remove: this.$jw.hasPermission('KFGJ-CDGL-SC')
          },
          url: vm.url
        },
        enableFilter: true,
        floatingFilter: true,
        suppressContextMenu: true,
        suppressMenuMainPanel: true,
        suppressMenuColumnPanel: true,
        suppressCellSelection: true,
        enableColResize: true,
        headerHeight: 35,
        rowHeight: 35,
        getRowNodeId: function (item) {
          return item.f_id
        },
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
        onGridReady: function (params) {
          // params.api.sizeColumnsToFit()
          // params.columnApi.autoSizeAllColumns()
        },
        localeTextFunc: function (key, defaultValue) {
          var localeTextMap = {
            // 公共
            loadingOoo: '加载中……',
            noRowsToShow: '无记录',

            // 过滤相关
            filterOoo: '过滤…',
            equals: '=',
            notEqual: '!=',
            startsWith: '^(起始)',
            endsWith: '$(结束)',
            contains: '*(包含)',
            notContains: '!(不包含)',
            clearFilter: '清除',
            applyFilter: '确定',

            // 翻页相关
            page: '第',
            to: '-',
            of: '共',
            first: '第一页',
            previous: '上一页',
            next: '下一页',
            last: '最末页',
            more: '未知'
          }
          var localeText = localeTextMap[key]
          if (localeText) {
            return localeText
          } else {
            console.warn(key + ': \'' + defaultValue + '\',')
            return defaultValue
          }
        },
        columnDefs: [
          {
            headerName: '编码',
            field: 'f_id',
            suppressSorting: true,
            suppressMenu: true,
            filter: 'text',
            filterParams: {applyButton: true, clearButton: true},
            pinned: 'left',
            cellRenderer: 'group',
            width: 200
          },
          {
            headerName: '名称',
            field: 'f_name',
            cellRendererFramework: Vue.extend({
              template: `<a @click.prevent="onView" style="cursor: pointer;">
                            <i style="display:inline-block;min-width:20px;" :class="params.node.data.f_icon"></i>{{ params.value }}
                          </a>`,
              methods: {
                onView () {
                  this.params.context.parentComponent.onView(this.params.node.data)
                }
              }
            }),
            suppressSorting: true,
            suppressMenu: true,
            pinned: 'left',
            width: 150
          },
          {
            headerName: '类型',
            field: 'f_type',
            suppressSorting: true,
            suppressMenu: true,
            cellStyle: {'text-align': 'center'},
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | dict({0: "根", 1: "目录", 2: "页面", 3: "按钮"}) }}</span>'
            }),
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
            colId: 'f_order',
            sortingOrder: ['asc'],
            cellStyle: {'text-align': 'right'},
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 40
          },
          {
            headerName: '描述',
            field: 'f_desc',
            colId: 'f_desc',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 200
          },
          {
            headerName: '路由路径',
            field: 'f_route_path',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 350
          },
          {
            headerName: '状态',
            field: 'f_status',
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | dict({1: "是", 2: "否"}) }}</span>'
            }),
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
            suppressSorting: true,
            suppressMenu: true,
            cellStyle: {'text-align': 'center'},
            width: 54
          },
          {
            headerName: '备注',
            field: 'f_remark',
            colId: 'f_remark',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 350
          },
          {
            headerName: '操作',
            field: '',
            cellRendererFramework: Vue.extend({
              template: `<div class="btn-group">
                            <button type="button" class="btn btn-xs btn-primary" title="增加子菜单"
                              @click.prevent="onAdd" :disabled="!params.context.permission.add || params.node.data.f_type > 2">
                              <i class="fa fa-plus"></i>
                            </button>
                            <button type="button" class="btn btn btn-xs btn-info" title="修改"
                              @click.prevent="onEdit" :disabled="!params.context.permission.edit">
                              <i class="fa fa-edit"></i>
                            </button>
                            <button type="button" class="btn btn-xs btn-danger" title="删除"
                              @click.prevent="onRemove" :disabled="!params.context.permission.remove || !params.node.data.f_parent_id">
                              <i class="fa fa-trash"></i>
                            </button>
                            <button type="button" class="btn btn-xs btn-default" title="SQL脚本"
                              @click.prevent="onSql" :disabled="!params.context.permission.sql">
                              <i class="fa fa-file-code-o"></i>
                            </button>
                          </div>`,
              methods: {
                onAdd () {
                  this.params.context.parentComponent.onAdd(this.params.node.data)
                },
                onEdit () {
                  this.params.context.parentComponent.onEdit(this.params.node.data)
                },
                onRemove () {
                  this.params.context.parentComponent.onRemove(this.params.node.data)
                },
                onSql () {
                  this.params.context.parentComponent.onSql(this.params.node.data)
                }
              }
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            pinned: 'right',
            cellStyle: {'text-align': 'center'},
            width: 96
          }
        ]
      }
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
        this.query()
      },
      query () {
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
        this.formParams.operation = 'add'
        this.formParams.parentEntity = entity
        this.formDialogTitle = '增加菜单'
        this.showFormDialog = true
      },
      onView (entity) {
        this.formParams.operation = 'view'
        this.formParams.entity = entity
        this.formDialogTitle = '查看菜单'
        this.showFormDialog = true
      },
      onEdit (entity) {
        this.formParams.operation = 'edit'
        this.formParams.entity = entity
        this.formDialogTitle = '修改菜单'
        this.showFormDialog = true
      },
      onRemove (entity) {
        var vm = this
        vm.$confirm('确定要删除所选的菜单及其子菜单吗?', '删除菜单', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          vm.$http.delete(vm.url + '/' + entity.f_id).then(function (response) {
            if (response.body.success) {
              vm.query()
            }
          })
        })
      },
      onSql (entity) {
        this.sqlDialog.shown = true
        this.sqlDialog.params.entity = entity
      },
      onSaved () {
        this.query()
        this.showFormDialog = false
      }
    }
  }
</script>
