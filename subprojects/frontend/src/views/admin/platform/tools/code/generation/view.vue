<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <el-row style="height: 100%;">
      <el-col :span="10" style="height: 100%;">
        <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid"
          :grid-options="gridOptions" :row-selected="onSelectedRule">
        </ag-grid-vue>
      </el-col>
      <el-col :span="14" style="height: 100%;overflow-y: auto;">
        <el-form :model="entity" :inline="true" :label-position="'right'" label-width="100px"
          style="margin-left: 20px;">
          <el-form-item label="规则编码" prop="f_code">
            <el-input v-model="entity.f_code"></el-input>
          </el-form-item>
          <el-form-item label="规则名称" prop="f_name">
            <el-input v-model="entity.f_name"></el-input>
          </el-form-item>
          <el-form-item label="父级菜单" prop="f_menu_parent_id">
            <el-input v-model="entity.f_menu_parent_id"></el-input>
          </el-form-item>
          <el-form-item label="排序" prop="f_menu_order">
            <el-input-number v-model="entity.f_menu_order" :step="5"></el-input-number>
          </el-form-item>
          <el-form-item label="菜单编码" prop="f_menu_id">
            <el-input v-model="entity.f_menu_id"></el-input>
          </el-form-item>
          <el-form-item label="菜单名称" prop="f_menu_name">
            <el-input v-model="entity.f_menu_name"></el-input>
          </el-form-item>
          <el-form-item label="菜单描述" prop="f_menu_remark">
            <el-input v-model="entity.f_menu_remark"></el-input>
          </el-form-item>
          <el-form-item label="URL" prop="f_request_url">
            <el-input v-model="entity.f_request_url"></el-input>
          </el-form-item>
          <el-form-item label="模块包名" prop="f_package_name">
            <el-input v-model="entity.f_package_name"></el-input>
          </el-form-item>
          <ag-grid-vue style="width: 99%; height: 200px;" class="ag-fresh jw-grid" :grid-options="tableGridOptions">
          </ag-grid-vue>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>


<script type="text/ecmascript-6">
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'

  export default {
    name: 'codeGeneration',
    components: {
      'ag-grid-vue': AgGridVue
    },
    data () {
      return {
        url: 'api/platform/tools/code/generate/rules',
        gridOptions: null,
        schematas: [],
        entity: {}
      }
    },
    computed: {
      contentStyle () {
        return {'padding': '20px', 'height': (this.$store.state.layout.body.height) + 'px'}
      }
    },
    beforeMount () {
      var vm = this
      vm.gridOptions = this.$buildGridOptions({
        context: {
          parentComponent: this,
          url: vm.url,
          params: {
            orderBy: 'f_menu_parent_id,f_menu_order',
            totalCount: 0
          }
        },
        datasource: {
          // rowCount: null,
          getRows (gridParams) {
            console.debug('getRows', 'datasource', this, 'gridParams', gridParams)

            var page = {
              pageSize: gridParams.endRow - gridParams.startRow,
              pageNo: gridParams.startRow / (gridParams.endRow - gridParams.startRow)
            }
            if (gridParams.sortModel && gridParams.sortModel.length > 0) {
              page.orderBy = ''
              gridParams.sortModel.forEach(function (order, idx, orders) {
                page.orderBy += page.orderBy + order.colId + ' ' + order.sort + ', '
              })
              page.orderBy = page.orderBy.substr(0, page.orderBy.length - 2)
            }

            var filters = {}
            for (var key in gridParams.filterModel) {
              var where = gridParams.filterModel[key]
              if (!where.filter) {
                continue
              }

              gridParams.context.params.totalCount = 0
              if (where.filterType === 'text' && where.type === 'equals') {
                filters[key] = where.filter
              } else if (where.filterType === 'text' && where.type === 'contains') {
                filters[key + '_like'] = where.filter
              } else if (where.filterType === 'text' && where.type === 'startsWith') {
                filters[key + '_rightLike'] = where.filter
              } else if (where.filterType === 'text' && where.type === 'endsWith') {
                filters[key + '_leftLike'] = where.filter
              } else {
                filters[key] = where.filter ? where.filter : null
              }
            }

            vm.$http.get(gridParams.context.url, {params: Object.assign({}, gridParams.context.params, page, filters)}).then(function (response) {
              if (response.body.success) {
                gridParams.context.params.totalCount = response.body.data.totalCount
                gridParams.successCallback(response.body.data.items, response.body.data.totalCount)
              } else {
                gridParams.failCallback()
              }
            })
          }
        },
        columnDefs: [
          {
            headerName: '#',
            headerComponentFramework: Vue.extend({
              template: `<div class="ag-header-component" style="padding: 5px;">
                              <button type="button" class="btn btn-xs btn-primary" title="增加" @click.prevent="onAdd">
                                <i class="fa fa-plus"></i>
                              </button>
                            </div>`,
              methods: {
                onAdd () {
                  this.params.context.parentComponent.onAddRule()
                }
              }
            }),
            pinned: 'left',
            cellStyle: {'text-align': 'right'},
            width: 38,
            cellRenderer: function (params) {
              return params.rowIndex + 1
            },
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true
          },
          {
            headerName: '规则编码',
            field: 'f_code',
            // floatingFilterComponentFramework: Vue.extend({
            filterFramework: Vue.extend({
              template: `<el-input :ref="'input'" v-model="text" placeholder="支持模糊过滤"></el-input>`,
              data () {
                return {
                  text: '',
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
                  window.devMode && console.info('doesFilterPass', this.$options.name, params)
                },
                getModel () {
                  return {filter: this.text, filterType: 'text', type: 'contains'}
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
            pinned: 'left',
            width: 150
          },
          {
            headerName: '规则名称',
            field: 'f_name',
            // floatingFilterComponentFramework: Vue.extend({
            filterFramework: Vue.extend({
              template: `<el-input :ref="'input'" v-model="text" placeholder="支持模糊过滤"></el-input>`,
              data () {
                return {
                  text: '',
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
                  window.devMode && console.info('doesFilterPass', this.$options.name, params)
                },
                getModel () {
                  return {filter: this.text, filterType: 'text', type: 'contains'}
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
            width: 120
          },
          {
            headerName: '菜单编码',
            field: 'f_menu_id',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 150
          }
        ]
      })

      vm.tableGridOptions = this.$buildGridOptions({
        context: {
          parentComponent: this
        },
        rowModelType: 'normal',
        pagination: false,
        enableFilter: false,
        floatingFilter: false,
        enableServerSideFilter: false,
        enableServerSideSorting: false,
        suppressContextMenu: true,
        suppressMenuMainPanel: true,
        suppressMenuColumnPanel: true,
        rowData: [],
        columnDefs: [
          {
            headerName: '序号',
            headerComponentFramework: Vue.extend({
              template: `<div class="ag-header-component" style="padding: 5px;">
                              <button type="button" class="btn btn-xs btn-primary" title="增加" @click.prevent="onAdd">
                                <i class="fa fa-plus"></i>
                              </button>
                            </div>`,
              methods: {
                onAdd () {
                  this.params.context.parentComponent.onAddTable()
                }
              }
            }),
            field: 'f_order',
            pinned: 'left',
            cellStyle: {'text-align': 'right'},
            width: 40
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
            width: 180
          },
          {
            headerName: '主表',
            field: 'f_is_main',
            cellStyle: {'text-align': 'center'},
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | dict({1: "是", 2: "否"}) }}</span>'
            }),
            width: 40
          },
          {
            headerName: '实体类名',
            field: 'f_entity_class',
            width: 400
          }
        ]
      })
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
      this._loadSchematas()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      onSelectedRule (params) {
        window.devMode && console.info('onSelectedRule', params)
        var vm = this
        vm.$http.get('api/platform/tools/code/generate/rules/' + params.node.data.f_id).then(function (response) {
          if (response.body.success) {
            vm.entity = response.body.data
            vm.tableGridOptions.api.setRowData(vm.entity.tableList)
          } else {
            vm.entity = {}
            vm.tableGridOptions.api.setRowData([])
          }
        })
      },
      onAddRule () {
        alert('onAddRule')
      },
      onAddTable () {
        alert('onAddTable')
      },
      _loadSchematas () {
        var vm = this
        vm.$http.get('api/platform/schema/information/schematas', {params: {orderBy: 'SCHEMA_NAME'}}).then(function (response) {
          if (response.body.success) {
            vm.schematas = response.body.data.items
          } else {
            vm.schematas = []
          }
        })
      }
    }
  }
</script>
