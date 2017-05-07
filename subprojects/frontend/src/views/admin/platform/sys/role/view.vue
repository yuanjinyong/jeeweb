<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="showFormDialog" :title="formDialogTitle" :close-on-click-modal="false" :size="'small'"
      :top="'30px'" :custom-class="'jw-dialog'">
      <role-form :params="formParams" @cancel="showFormDialog = false" @submit="onSaved" v-if="showFormDialog">
      </role-form>
    </el-dialog>

    <el-dialog v-model="authorizeDialogShown" :title="authorizeDialogTitle" :close-on-click-modal="false"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <role-authorize-form :params="authorizeDialogOptions"
        @cancel="authorizeDialogShown = false"
        @submit="authorizeDialogShown = false"
        v-if="authorizeDialogShown">
      </role-authorize-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'
  import RoleForm from './form'
  import RoleAuthorizeForm from './authorize'

  export default {
    name: 'role',
    components: {
      'ag-grid-vue': AgGridVue,
      RoleForm,
      RoleAuthorizeForm
    },
    data () {
      return {
        url: 'api/platform/sys/roles',
        gridOptions: null,
        showFormDialog: false,
        formDialogTitle: '查看角色',
        formParams: {
          operation: 'view',
          entity: {}
        },
        authorizeDialogShown: false,
        authorizeDialogTitle: '查看授权',
        authorizeDialogOptions: {
          operation: 'view',
          entity: {}
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
      vm.gridOptions = this.$buildGridOptions({
        context: {
          parentComponent: this,
          url: vm.url,
          params: {
            orderBy: 'f_is_sys,f_name',
            totalCount: 0
          }
        },
        columnDefs: [
          {
            headerName: '',
            checkboxSelection: true,
            pinned: 'left',
            cellStyle: {'text-align': 'center'},
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 24
          },
          {
            headerName: '#',
            pinned: 'left',
            headerComponentFramework: Vue.extend({
              template: `<div class="ag-header-component" style="padding: 5px;">
                              <button type="button" class="btn btn-xs btn-primary" title="增加" @click.prevent="onAdd">
                                <i class="fa fa-plus"></i>
                              </button>
                            </div>`,
              methods: {
                onAdd () {
                  this.params.context.parentComponent.onAdd()
                }
              }
            }),
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
            headerName: '角色名称',
            field: 'f_name',
            pinned: 'left',
            suppressSorting: true,
            suppressMenu: true,
            cellRendererFramework: Vue.extend({
              template: '<a @click.prevent="onView" style="cursor: pointer;">{{ this.params.value }}</a>',
              methods: {
                onView () {
                  this.params.context.parentComponent.onView(this.params.node.data)
                }
              }
            }),
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
                }
              }
            }),
            width: 160
          },
          {
            headerName: '角色描述',
            field: 'f_desc',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 300
          },
          {
            headerName: '是否预置',
            field: 'f_is_sys',
            cellStyle: {'text-align': 'center'},
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | dict({1: "是", 2: "否"}) }}</span>'
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 75
          },
          {
            headerName: '备注',
            field: 'f_remark',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 300
          },
          {
            headerName: '操作',
            field: '',
            cellRendererFramework: Vue.extend({
              template: `<div class="btn-group">
                            <button type="button" class="btn btn-xs btn-primary" @click.prevent="onAuthorize" title="授权可以操作的功能">
                              <i class="fa fa-key"></i>
                            </button>
                            <button type="button" class="btn btn btn-xs btn-info" @click.prevent="onEdit"title="修改" >
                              <i class="fa fa-edit"></i>
                            </button>
                            <button type="button" class="btn btn-xs btn-danger" @click.prevent="onRemove"title="删除" :disabled="params.node.data && params.node.data.f_is_sys === 1">
                              <i class="fa fa-trash"></i>
                            </button>
                          </div>`,
              methods: {
                onAuthorize () {
                  this.params.context.parentComponent.onAuthorize(this.params.node.data)
                },
                onEdit () {
                  this.params.context.parentComponent.onEdit(this.params.node.data)
                },
                onRemove () {
                  this.params.context.parentComponent.onRemove(this.params.node.data)
                }
              }
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            pinned: 'right',
            cellStyle: {'text-align': 'center'},
            width: 80
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
    methods: {
      onAdd () {
        this.formParams.operation = 'add'
        this.formDialogTitle = '增加角色'
        this.showFormDialog = true
      },
      onView (entity) {
        this.formParams.operation = 'view'
        this.formParams.entity = entity
        this.formDialogTitle = '查看角色'
        this.showFormDialog = true
      },
      onEdit (entity) {
        this.formParams.operation = 'edit'
        this.formParams.entity = entity
        this.formDialogTitle = '修改角色'
        this.showFormDialog = true
      },
      onRemove (entity) {
        var vm = this
        vm.$confirm('确定要删除所选的角色吗?', '删除菜单', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          vm.$http.delete(vm.url + '/' + entity.f_id).then(function (response) {
            if (response.body.success) {
              vm._refreshGrid()
            }
          })
        })
      },
      onSaved () {
        this._refreshGrid()
        this.showFormDialog = false
      },
      _refreshGrid () {
        this.gridOptions.context.params.totalCount = 0
        this.gridOptions.api.setDatasource(this.gridOptions.datasource)
      },
      onAuthorize (entity) {
        this.authorizeDialogOptions.operation = 'edit'
        this.authorizeDialogOptions.entity = entity
        this.authorizeDialogTitle = '修改授权'
        this.authorizeDialogShown = true
      }
    }
  }
</script>
