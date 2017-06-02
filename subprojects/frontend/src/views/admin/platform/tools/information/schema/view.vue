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
        featureOptions: {
          name: '数据库列',
          url: 'api/platform/schema/information/columns'
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            featureComponent: this,
            params: {
              orderBy: 'TABLE_SCHEMA,TABLE_NAME,ORDINAL_POSITION',
              totalCount: 0
            }
          }
        })
      }
    },
    computed: {
      contentStyle () {
        return {'padding': '20px', 'height': (this.$store.state.layout.body.height) + 'px'}
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '',
          pinned: 'left',
          checkboxSelection: true,
          // headerCheckboxSelection: true,
          cellStyle: {'text-align': 'center'},
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 24
        },
        {
          headerName: '#',
          pinned: 'left',
          cellStyle: {'text-align': 'right'},
          cellRenderer: function (params) {
            return params.rowIndex + 1
          },
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 38
        },
        {
          headerName: '数据库名',
          field: 'TABLE_SCHEMA',
          tooltipField: 'TABLE_SCHEMA',
          pinned: 'left',
          suppressFilter: false,
          filterFramework: Vue.extend({
            template: `<el-select :ref="'input'" v-model="text" clearable placeholder="请点击选择数据库名">
                          <el-option v-for="item in items" :key="item.SCHEMA_NAME" :label="item.SCHEMA_NAME" :value="item.SCHEMA_NAME"></el-option>
                        </el-select>`,
            data () {
              return {
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
                vm.$http.get('api/platform/schema/information/schematas', {params: {SCHEMA_NAME_like: schemaName}}).then(function (response) {
                  vm.items = response.body.success ? response.body.data.items : []
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
              }
            }
          }),
          width: 120
        },
        {
          headerName: '表名',
          field: 'TABLE_NAME',
          tooltipField: 'TABLE_NAME',
          pinned: 'left',
          suppressFilter: false,
          filterFramework: Vue.extend({
            template: `<el-autocomplete v-model="value" :fetch-suggestions="remoteMethod" placeholder="请输入内容" @select="handleSelect">
                        </el-autocomplete>`,
            data () {
              return {
                value: '',
                valueGetter: null
              }
            },
            watch: {
              'value': function (val, oldVal) {
                if (val !== oldVal) {
                  this.params.filterChangedCallback()
                }
              }
            },
            created () {
              this.valueGetter = this.params.valueGetter
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
                vm.$http.get('api/platform/schema/information/tables', {params: {TABLE_NAME_like: tableName}}).then(function (response) {
                  var tables = []
                  if (response.body.success) {
                    response.body.data.items.forEach((table) => {
                      tables.push({value: table.TABLE_NAME})
                    })
                  }
                  cb && cb(tables)
                })
              },
              handleSelect (item) {
                // console.log(item)
              },
              isFilterActive () {
                return this.value !== null && this.value !== undefined && this.value !== ''
              },
              doesFilterPass (params) {
                window.devMode && console.info('doesFilterPass', this.$options.name, params)
                return !this.value || this.value.toLowerCase()
                  .split(' ')
                  .every((filterWord) => {
                    return this.valueGetter(params.node).toString().toLowerCase().indexOf(filterWord) >= 0
                  })
              },
              getModel () {
                return {filter: this.value, filterType: 'text', type: 'contains'}
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
          width: 240
        },
        {
          headerName: '列名',
          field: 'COLUMN_NAME',
          tooltipField: 'COLUMN_NAME',
          pinned: 'left',
          width: 180
        },
        {
          headerName: '列类型',
          field: 'COLUMN_TYPE',
          width: 120
        },
        {
          headerName: '是否为空',
          field: 'IS_NULLABLE',
          width: 68
        },
        {
          headerName: '默认值',
          field: 'COLUMN_DEFAULT',
          tooltipField: 'COLUMN_DEFAULT',
          width: 120
        },
        {
          headerName: '描述',
          field: 'COLUMN_COMMENT',
          tooltipField: 'COLUMN_COMMENT',
          width: 300
        }
      ]
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
