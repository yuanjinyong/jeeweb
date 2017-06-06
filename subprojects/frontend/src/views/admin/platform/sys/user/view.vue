<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false" :modal="true"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <user-form :form-options="formOptions" v-if="formOptions.isShow"></user-form>
    </el-dialog>

    <el-dialog v-model="authorizeFormOptions.isShow" :title="authorizeFormOptions.title" :close-on-click-modal="false"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <user-authorize-form :form-options="authorizeFormOptions"
        v-if="authorizeFormOptions.isShow">
      </user-authorize-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import { AgGridVue } from 'ag-grid-vue'
  import AddHeaderComponenetFramework from 'components/ag-grid/AddHeaderComponenetFramework'
  import DictFilterFramework from 'components/ag-grid/DictFilterFramework'
  import DictFloatingFilterComponentFramework from 'components/ag-grid/DictFloatingFilterComponentFramework'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import LikeFloatingFilterComponentFramework from 'components/ag-grid/LikeFloatingFilterComponentFramework'
  import DictRendererFramework from 'components/ag-grid/DictRendererFramework'
  import IndexRendererFramework from 'components/ag-grid/IndexRendererFramework'
  import OperationRendererFramework from 'components/ag-grid/OperationRendererFramework'
  import TimestampFilterFramework from 'components/ag-grid/TimestampFilterFramework'
  import TimestampFloatingFilterComponentFramework from 'components/ag-grid/TimestampFloatingFilterComponentFramework'
  import TimestampRendererFramework from 'components/ag-grid/TimestampRendererFramework'
  import ViewRendererFramework from 'components/ag-grid/ViewRendererFramework'
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
        featureOptions: {
          name: '用户',
          url: 'api/platform/sys/users'
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
              orderBy: 'f_is_preset,f_account',
              totalCount: 0
            }
          }
        }),
        authorizeFormOptions: {
          isShow: false,
          operation: 'edit',
          title: '修改授权',
          params: {},
          context: {
            featureComponent: this
          }
        }
      }
    },
    computed: {
      permission () {
        return {
          unlock: this.hasPermission('XTGL-YHGL-JS'),
          resetPassword: this.hasPermission('XTGL-YHGL-CZMM'),
          authorize: this.hasPermission('XTGL-YHGL-SQ'),
          add: this.hasPermission('XTGL-YHGL-ZJ'),
          edit: this.hasPermission('XTGL-YHGL-XG'),
          remove: this.hasPermission('XTGL-YHGL-SC')
        }
      },
      contentStyle () {
        return {'padding': '20px', 'height': (this.$store.state.layout.body.height) + 'px'}
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '',
          checkboxSelection: true,
          pinned: 'left',
          cellStyle: {'text-align': 'center'},
          width: 24
        },
        {
          headerName: '#',
          pinned: 'left',
          headerComponentFramework: AddHeaderComponenetFramework,
          cellStyle: {'text-align': 'right'},
          cellRendererFramework: IndexRendererFramework,
          width: 38
        },
        {
          headerName: '账号',
          field: 'f_account',
          pinned: 'left',
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          cellRendererFramework: ViewRendererFramework,
          width: 100
        },
        {
          headerName: '姓名',
          field: 'f_name',
          pinned: 'left',
          suppressSorting: false,
          colId: 'f_name',
          sortingOrder: ['asc'],
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          width: 100
        },
        {
          headerName: '创建时间',
          field: 'f_created_time',
          suppressFilter: false,
          filterFramework: TimestampFilterFramework,
          floatingFilterComponentFramework: TimestampFloatingFilterComponentFramework,
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: TimestampRendererFramework,
          width: 190 // 有filter的为190，没有的为140
        },
        {
          headerName: '创建人',
          field: 'f_creator_name',
          width: 100
        },
        {
          headerName: '最近登录时间',
          field: 'f_last_login_time',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: TimestampRendererFramework,
          width: 140
        },
        {
          headerName: '系统预置',
          field: 'f_is_preset',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          width: 75
        },
        {
          headerName: '状态',
          field: 'f_status',
          suppressFilter: false,
          filterParams: {type: 'in'},
          filterFramework: DictFilterFramework,
          floatingFilterComponentFramework: DictFloatingFilterComponentFramework,
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'UserStatus'},
          width: 88
        },
        {
          headerName: '备注',
          field: 'f_remark',
          tooltipField: 'f_remark',
          width: 300
        },
        {
          headerName: '操作',
          field: '',
          pinned: 'right',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: OperationRendererFramework,
          cellRendererParams: {
            operations: [{
              id: 'authorize',
              title: '授权可以操作的功能',
              type: 'warning',
              icon: 'fa fa-key',
              permission: 'authorize',
              isDisabled: function (entity) {
                return entity.f_status === 3
              },
              onClick (params, entity) {
                params.context.featureComponent.onAuthorize(entity)
              }
            }, {
              id: 'edit',
              permission: 'edit',
              isDisabled: function (entity) {
                return entity.f_status === 3
              }
            }, {
              id: 'remove',
              title: '注销' + this.featureOptions.name,
              permission: 'remove',
              isDisabled: function (entity) {
                return entity.f_status === 3 || entity.f_is_preset === 1
              }
            }, {
              id: 'unlock',
              title: '解锁' + this.featureOptions.name,
              type: 'success',
              icon: 'fa fa-unlock',
              permission: 'unlock',
              isDisabled: function (entity) {
                return entity.f_status !== 2
              },
              onClick (params, entity) {
                params.context.featureComponent.onUnlock(entity)
              }
            }, {
              id: 'resetPassword',
              title: '重置密码',
              type: 'warning',
              icon: 'fa fa-cog',
              permission: 'resetPassword',
              isDisabled: function (entity) {
                return entity.f_status === 3 || entity.f_is_preset === 1
              },
              onClick (params, entity) {
                params.context.featureComponent.onResetPassword(entity)
              }
            }]
          },
          width: 120
        }
      ]
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      onAuthorize (entity) {
        this.authorizeFormOptions.params = entity
        this.authorizeFormOptions.isShow = true
      },
      onUnlock (entity) {
        var vm = this
        vm.$confirm('确定要解锁所选的用户吗?', '解锁用户', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          vm.$http.post(vm.featureOptions.url + '/' + entity.f_id + '/unlock').then((response) => {
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
          vm.$http.delete(vm.featureOptions.url + '/' + entity.f_id + '/reset/password').then((response) => {
            if (response.body.success) {
              vm._refreshGrid()
            }
          })
        })
      },
      _refreshGrid () {
        this.gridOptions.context.params.totalCount = 0
        this.gridOptions.api.setDatasource(this.gridOptions.datasource)
      }
    }
  }
</script>
