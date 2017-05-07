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
      vm.gridOptions = this.$buildGridOptions({
        context: {
          parentComponent: this,
          url: vm.url,
          params: {
            orderBy: 'f_url,f_methods',
            totalCount: 0
          }
        },
        paginationAutoPageSize: false,
        paginationPageSize: vm.mode === 'selector' ? 10 : 20,
        infiniteBlockSize: 20,
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
      })
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
