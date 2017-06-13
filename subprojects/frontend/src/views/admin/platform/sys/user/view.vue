<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <user-detail ref="detail" :detail-options="detailOptions"></user-detail>
    <user-authorize-detail ref="authorize" :detail-options="authorizeOptions"></user-authorize-detail>
  </div>
</template>


<script>
  import {ViewlMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    DictFilterFramework,
    DictFloatingFilterComponentFramework,
    DictRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    OperationRendererFramework,
    TimestampFilterFramework,
    TimestampFloatingFilterComponentFramework,
    TimestampRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import UserDetail from './detail'
  import UserAuthorizeDetail from './authorize'
  //  import {UserDetail, UserAuthorizeDetail} from 'views'

  export default {
    name: 'userView',
    mixins: [ViewlMixin],
    components: {
      UserDetail,
      UserAuthorizeDetail
    },
    data () {
      return {
        authorizeOptions: {
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
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            name: '用户',
            url: 'api/platform/sys/users',
            featureComponent: this,
            getPermissions (params, operation) {
              return params.context.featureComponent.permission
            },
            getDetailComponent (params, operation) {
              return params.context.featureComponent.$refs['detail']
            },
            params: {
              orderBy: 'f_is_preset,f_account'
            }
          }
        })
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
      }
    },
    created () {
      this.gridOptions.columnDefs = [
        {
          headerName: '',
          pinned: 'left',
          hide: this.mode !== 'selector',
          checkboxSelection: this.mode === 'selector',
          cellStyle: {'text-align': 'center'},
          width: 24
        },
        {
          headerName: '#',
          pinned: 'left',
          headerComponentFramework: this.mode !== 'selector' ? AddHeaderComponenetFramework : null,
          cellStyle: {'text-align': 'right'},
          cellRendererFramework: IndexRendererFramework,
          width: 38
        },
        {
          headerName: '账号',
          field: 'f_account',
          pinned: 'left',
          suppressSorting: false,
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
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          width: 100
        },
        {
          headerName: '创建时间',
          field: 'f_created_time',
          suppressSorting: false,
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
          suppressSorting: false,
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
          hide: this.mode === 'selector',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: OperationRendererFramework,
          cellRendererParams: {
            operations: [{
              id: 'authorize',
              title: '授权可以操作的功能',
              type: 'warning',
              icon: 'fa fa-key',
              permission: 'authorize',
              isDisabled (params, entity) {
                return entity.f_status === 3
              },
              onClick (params, entity) {
                params.context.featureComponent.$refs['authorize'].open({
                  operation: 'authorize',
                  title: '授权可以操作的功能',
                  params: entity
                })
              }
            }, {
              id: 'edit',
              permission: 'edit',
              isDisabled (params, entity) {
                return entity.f_status === 3
              }
            }, {
              id: 'remove',
              title: '注销用户',
              permission: 'remove',
              isDisabled (params, entity) {
                return entity.f_status === 3 || entity.f_is_preset === 1
              }
            }, {
              id: 'unlock',
              title: '解锁用户',
              type: 'success',
              icon: 'fa fa-unlock',
              permission: 'unlock',
              isDisabled (params, entity) {
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
              isDisabled (params, entity) {
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
    methods: {
      onUnlock (entity) {
        this.$confirm('确定要解锁所选的用户吗?', '解锁用户', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http.post(this.gridOptions.context.url + '/' + entity.f_id + '/unlock').then((response) => {
            if (response.body.success) {
              this._refreshGrid()
            }
          })
        }).catch((e) => {
          // console && console.error(e)
        })
      },
      onResetPassword (entity) {
        this.$confirm('确定要重置所选用户的密码吗?', '重置用户密码', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http.post(this.gridOptions.context.url + '/' + entity.f_id + '/reset/password').then((response) => {
            if (response.body.success) {
              this._refreshGrid()
            }
          })
        }).catch((e) => {
          // console && console.error(e)
        })
      },
      _refreshGrid () {
        this.gridOptions.context.params.totalCount = 0
        this.gridOptions.api.setDatasource(this.gridOptions.datasource)
      }
    }
  }
</script>
