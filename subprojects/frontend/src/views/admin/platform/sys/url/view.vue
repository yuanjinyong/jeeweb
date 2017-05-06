<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>
  </div>
</template>


<script type="text/ecmascript-6">
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'

  export default {
    name: 'url',
    components: {
      'ag-grid-vue': AgGridVue
    },
    props: {
      mode: {type: String, default: ''}
    },
    data () {
      return {
        url: 'api/platform/sys/urls',
        gridOptions: null,
        entities: []
      }
    },
    computed: {
      contentStyle () {
        if (this.mode === 'selector') {
          return {'padding': '0px', 'height': (this.$store.state.layout.body.height - 111) + 'px'}
        } else {
          return {'padding': '20px', 'height': (this.$store.state.layout.body.height - 35) + 'px'}
        }
      }
    },
    beforeMount () {
      var vm = this
      vm.gridOptions = {
        debug: true,
        context: {
          parentComponent: this,
          url: vm.url,
          params: {
            orderBy: 'f_url,f_methods',
            totalCount: 0
          }
        },
        enableFilter: true,
        floatingFilter: true,
        enableServerSideFilter: true,
        enableServerSideSorting: true,
        suppressContextMenu: true,
        suppressMenuMainPanel: true,
        suppressMenuColumnPanel: true,
        suppressCellSelection: true,
        enableColResize: true,
        headerHeight: 35,
        rowHeight: 35,
        rowSelection: 'multiple',
        rowModelType: 'infinite',
        pagination: true,
        paginationPageSize: vm.mode === 'selector' ? 10 : 20,
        paginationFirstPage: 0,
        // paginationAutoPageSize: true,
        paginationOverflowSize: 2,
        infiniteInitialRowCount: 1,
        infiniteBlockSize: 20,
        maxPagesInCache: 2,
        maxConcurrentDatasourceRequests: 1,
        getRowNodeId: function (item) {
          return item.f_id
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
        datasource: {
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
            headerName: '',
            checkboxSelection: true,
            // headerCheckboxSelection: true,
            pinned: 'left',
            cellStyle: {'text-align': 'center'},
            width: 24,
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true
          },
          {
            headerName: '#',
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
            headerName: 'URL',
            field: 'f_url',
            pinned: 'left',
            suppressMenu: true,
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
            width: 400
          },
          {
            headerName: '提交方式',
            field: 'f_methods',
            suppressMenu: true,
            filterFramework: Vue.extend({
              template: `<el-select :ref="'input'" v-model="text" clearable>
                            <el-option v-for="item in items" :key="item.f_item_code" :label="item.f_item_text" :value="item.f_item_code"></el-option>
                          </el-select>`,
              data () {
                return {
                  loading: false,
                  items: [
                    {
                      f_item_code: '[]',
                      f_item_text: '[]'
                    },
                    {
                      f_item_code: '[DELETE]',
                      f_item_text: '[DELETE]'
                    },
                    {
                      f_item_code: '[GET]',
                      f_item_text: '[GET]'
                    },
                    {
                      f_item_code: '[POST]',
                      f_item_text: '[POST]'
                    },
                    {
                      f_item_code: '[PUT]',
                      f_item_text: '[PUT]'
                    }
                  ],
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
                window.devMode && console.info('created', this.$options.name, this._uid)
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
            width: 110
          },
          {
            headerName: '后台处理方法',
            field: 'f_handler_method',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 1800
          },
          {
            headerName: '记录日志',
            field: 'f_is_log',
            pinned: 'right',
            cellStyle: {'text-align': 'center'},
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | dict({1: "是", 2: "否"}) }}</span>'
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 75
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
