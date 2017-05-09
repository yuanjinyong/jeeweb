<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="showFormDialog" :title="formDialogTitle" :close-on-click-modal="false" :size="'small'"
      :top="'30px'" :custom-class="'jw-dialog'">
      <user-form :params="formParams" @cancel="showFormDialog = false" @submit="onSaved" v-if="showFormDialog">
      </user-form>
    </el-dialog>

    <el-dialog v-model="authorizeDialogShown" :title="authorizeDialogTitle" :close-on-click-modal="false"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <user-authorize-form :params="authorizeDialogOptions"
        @cancel="authorizeDialogShown = false"
        @submit="authorizeDialogShown = false"
        v-if="authorizeDialogShown">
      </user-authorize-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'
  import UserForm from './form'
  import UserAuthorizeForm from './authorize'

  export default {
    name: 'user',
    components: {
      'ag-grid-vue': AgGridVue,
      UserForm,
      UserAuthorizeForm
    },
    data () {
      return {
        url: 'api/platform/sys/users',
        gridOptions: null,
        showFormDialog: false,
        formDialogTitle: '查看用户',
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
          permission: {
            unlock: this.$jw.hasPermission('XTGL-YHGL-JS'),
            resetPassword: this.$jw.hasPermission('XTGL-YHGL-CZMM'),
            authorize: this.$jw.hasPermission('XTGL-YHGL-SQ'),
            add: this.$jw.hasPermission('XTGL-YHGL-ZJ'),
            edit: this.$jw.hasPermission('XTGL-YHGL-XG'),
            remove: this.$jw.hasPermission('XTGL-YHGL-SC')
          },
          url: vm.url,
          params: {
            orderBy: 'f_is_preset,f_account',
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
                              <button type="button" class="btn btn-xs btn-primary" title="增加"
                                @click.prevent="onAdd" :disabled="!params.context.permission.add">
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
            headerName: '账号',
            field: 'f_account',
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
            width: 100
          },
          {
            headerName: '姓名',
            field: 'f_name',
            pinned: 'left',
            suppressSorting: true,
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
                }
              }
            }),
            width: 100
          },
          {
            headerName: '创建时间',
            field: 'f_created_time',
            cellStyle: {'text-align': 'center'},
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | timestamp }}</span>'
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 140
          },
          {
            headerName: '创建人',
            field: 'f_creator_name',
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 100
          },
          {
            headerName: '最近登录时间',
            field: 'f_last_login_time',
            cellStyle: {'text-align': 'center'},
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | timestamp }}</span>'
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 140
          },
          {
            headerName: '系统预置',
            field: 'f_is_preset',
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
            headerName: '状态',
            field: 'f_status',
            cellStyle: {'text-align': 'center'},
            cellRendererFramework: Vue.extend({
              template: '<span>{{ this.params.value | dict({1: "正常", 2: "锁定", 3: "注销"}) }}</span>'
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            width: 45
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
                            <button type="button" class="btn btn-xs btn-primary" title="授权可以操作的功能"
                              @click.prevent="onAuthorize" :disabled="!params.context.permission.authorize || (params.node.data && params.node.data.f_status === 3)">
                              <i class="fa fa-key"></i>
                            </button>
                            <button type="button" class="btn btn btn-xs btn-info" title="修改"
                              @click.prevent="onEdit" :disabled="!params.context.permission.edit || (params.node.data && params.node.data.f_status === 3)">
                              <i class="fa fa-edit"></i>
                            </button>
                            <button type="button" class="btn btn-xs btn-danger" title="注销"
                              @click.prevent="onRemove" :disabled="!params.context.permission.remove || (params.node.data && (params.node.data.f_status === 3 || params.node.data.f_is_preset === 1))">
                              <i class="fa fa-recycle"></i>
                            </button>
                            <button type="button" class="btn btn-xs btn-success" title="解锁"
                              @click.prevent="onUnlock" :disabled="!params.context.permission.unlock || (params.node.data && params.node.data.f_status !== 2)">
                              <i class="fa fa-unlock"></i>
                            </button>
                            <button type="button" class="btn btn-xs btn-warning" title="重置密码"
                            @click.prevent="onResetPassword" :disabled="!params.context.permission.resetPassword || (params.node.data && (params.node.data.f_status === 3 || params.node.data.f_is_preset === 1))">
                              <i class="fa fa-cog"></i>
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
                },
                onUnlock () {
                  this.params.context.parentComponent.onUnlock(this.params.node.data)
                },
                onResetPassword () {
                  this.params.context.parentComponent.onResetPassword(this.params.node.data)
                }
              }
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            pinned: 'right',
            cellStyle: {'text-align': 'center'},
            width: 120
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
        this.formDialogTitle = '增加用户'
        this.showFormDialog = true
      },
      onView (entity) {
        this.formParams.operation = 'view'
        this.formParams.entity = entity
        this.formDialogTitle = '查看用户'
        this.showFormDialog = true
      },
      onEdit (entity) {
        this.formParams.operation = 'edit'
        this.formParams.entity = entity
        this.formDialogTitle = '修改用户'
        this.showFormDialog = true
      },
      onRemove (entity) {
        var vm = this
        vm.$confirm('确定要注销所选的用户吗?', '注销用户', {
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
      onUnlock (entity) {
        var vm = this
        vm.$confirm('确定要解锁所选的用户吗?', '解锁用户', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          vm.$http.post(vm.url + '/' + entity.f_id + '/unlock').then(function (response) {
            if (response.body.success) {
              vm._refreshGrid()
            }
          })
        })
      },
      onResetPassword (entity) {
        var vm = this
        vm.$confirm('确定要重置所选用户的密码吗?', '重置用户密码', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          vm.$http.delete(vm.url + '/' + entity.f_id + '/reset/password').then(function (response) {
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
