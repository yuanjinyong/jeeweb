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
    name: 'informationSchema',
    components: {
      'ag-grid-vue': AgGridVue
    },
    data () {
      return {
        url: 'api/platform/schema/information/columns',
        gridOptions: null
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
        context: {url: vm.url, params: {orderBy: 'TABLE_SCHEMA,TABLE_NAME,ORDINAL_POSITION', totalCount: 0}},
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
            headerName: '数据库名',
            field: 'TABLE_SCHEMA',
            // filter: 'text',
            // filterParams: {applyButton: true, clearButton: true},
            // floatingFilterComponentFramework: Vue.extend({
            filterFramework: Vue.extend({
              name: 'schemaSelect',
              template: `<el-select :ref="'input'" v-model="text" clearable placeholder="请点击选择数据库名">
                            <el-option v-for="item in items" :key="item.SCHEMA_NAME" :label="item.SCHEMA_NAME" :value="item.SCHEMA_NAME"></el-option>
                          </el-select>`,
              data () {
                return {
                  loading: false,
                  items: [],
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
                this.loadSchematas(null)
              },
              methods: {
                remoteMethod (query) {
                  var vm = this
                  setTimeout(() => {
                    vm.loadSchematas(query)
                  }, 200)
                },
                loadSchematas (schemaName) {
                  var vm = this
                  vm.loading = true
                  vm.$http.get('api/platform/schema/information/schematas', {params: {SCHEMA_NAME_like: schemaName}}).then(function (response) {
                    vm.loading = false
                    if (response.body.success) {
                      vm.items = response.body.data.items
                    } else {
                      vm.items = []
                    }
                  })
                },
                isFilterActive () {
                  return this.text !== undefined && this.text !== null && this.text !== ''
                },
                doesFilterPass (params) {
                  window.devMode && console.info('doesFilterPass', this.$options.name, params)
                  return !this.text || this.text.toLowerCase()
                    .split(' ')
                    .every((filterWord) => {
                      return this.valueGetter(params.node).toString().toLowerCase().indexOf(filterWord) >= 0
                    })
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
                },
                onParentModelChanged (model) {

                }
              }
            }),
            pinned: 'left',
            width: 160
          },
          {
            headerName: '表名',
            field: 'TABLE_NAME',
            filterFramework: Vue.extend({
              name: 'schemaSelect',
              template: `<el-autocomplete v-model="text" :fetch-suggestions="remoteMethod" placeholder="请输入内容" @select="handleSelect">
                          </el-autocomplete>`,
              data () {
                return {
                  loading: false,
                  items: [],
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
                // this.loadTables(null)
              },
              methods: {
                remoteMethod (query, cb) {
                  var vm = this
                  setTimeout(() => {
                    vm.loadTables(query, cb)
                  }, 200)
                },
                loadTables (tableName, cb) {
                  var vm = this
                  vm.loading = true
                  vm.$http.get('api/platform/schema/information/tables', {params: {TABLE_NAME_like: tableName}}).then(function (response) {
                    vm.loading = false
                    if (response.body.success) {
                      vm.items = response.body.data.items
                      cb && cb(response.body.data.items)
                    } else {
                      vm.items = []
                      cb && cb(response.body.data.items)
                    }
                  })
                },
                handleSelect (item) {
                  console.log(item)
                },
                isFilterActive () {
                  return this.text !== null && this.text !== undefined && this.text !== ''
                },
                doesFilterPass (params) {
                  window.devMode && console.info('doesFilterPass', this.$options.name, params)
                  return !this.text || this.text.toLowerCase()
                    .split(' ')
                    .every((filterWord) => {
                      return this.valueGetter(params.node).toString().toLowerCase().indexOf(filterWord) >= 0
                    })
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
                },
                onParentModelChanged (model) {

                }
              }
            }),
            width: 300
          },
          {
            headerName: '字段名',
            field: 'COLUMN_NAME',
            filter: 'text',
            filterParams: {applyButton: true, clearButton: true},
            suppressSorting: true,
            suppressMenu: true,
            width: 240
          },
          {
            headerName: '字段说明',
            field: 'COLUMN_COMMENT',
            filter: 'text',
            filterParams: {applyButton: true, clearButton: true},
            suppressSorting: true,
            suppressMenu: true,
            width: 300
          }
        ]
      })
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {}
  }
</script>
