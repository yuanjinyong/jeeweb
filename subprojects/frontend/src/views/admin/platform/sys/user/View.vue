<template>
  <div :style="contentStyle">
    <ag-grid ref="grid" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <user-detail ref="detail" :detail-options="detailOptions"></user-detail>
    <jw-authorize ref="authorize" :detail-options="authorizeOptions"></jw-authorize>
  </div>
</template>


<script>
  import Vue from 'vue'
  import {ViewlMixin} from 'mixins'

  export default {
    name: 'userView',
    mixins: [ViewlMixin],
    components: {
      UserDetail: r => require.ensure([], () => r(require('./Detail')), 'platform-sys-user')
    },
    data () {
      return {
        authorizeOptions: {
          context: {
            url: 'api/platform/sys/users',
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
      this.gridOptions.columnDefs = [{
        hide: this.mode !== 'selector',
        type: 'Checkbox'
      }, {
        type: ['IndexRender', this.mode !== 'selector' ? 'AddHeader' : 'Null']
      }, {
        headerName: '账号',
        field: 'f_account',
        pinned: 'left',
        suppressSorting: false,
        type: ['ViewRender', 'LikeFilter'],
        width: 100
      }, {
        headerName: '姓名',
        field: 'f_name',
        pinned: 'left',
        suppressSorting: false,
        sortColId: 'convert(f_name USING gbk)',
        type: 'LikeFilter',
        width: 100
      }, {
        headerName: '创建时间',
        field: 'f_created_time',
        suppressSorting: false,
        type: ['TimestampRender', 'TimestampFilter']
      }, {
        headerName: '创建人',
        field: 'f_creator_name',
        width: 100
      }, {
        headerName: '最近登录时间',
        field: 'f_last_login_time',
        suppressSorting: false,
        type: 'TimestampRender'
      }, {
        headerName: '系统预置',
        field: 'f_is_preset',
        type: 'DictRender',
        cellRendererParams: {dict: 'TrueFalse'},
        width: 75
      }, {
        headerName: '状态',
        field: 'f_status',
        type: ['DictRender', 'DictFilter'],
        filterParams: {type: 'in'},
        cellRendererParams: {dict: 'UserStatus'},
        width: 88
      }, {
        headerName: '备注',
        field: 'f_remark',
        tooltipField: 'f_remark',
        suppressSizeToFit: false,
        width: 300
      }, {
        headerComponentFramework: Vue.extend({
          template: `
            <div class="ag-header-component" style="margin-top: -1px;padding: 0px 5px;">
              <el-button size="mini" title="服务端导出" @click.stop="onExport(true)">
                <i class="fa fa-file-text-o" style="min-width: 12px;"></i>
              </el-button>
              <el-button size="mini" title="客户端导出" @click.stop="onExport(false)">
                <i class="fa fa-file-text-o" style="min-width: 12px;"></i>
              </el-button>
            </div>
            `,
          methods: {
            onExport (server) {
              this.params.api.gridOptionsWrapper.gridOptions.export({}, {serverSide: server})
            }
          }
        }),
        hide: this.mode === 'selector',
        type: 'OperationRender',
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
              return entity.f_status === 103 || entity.f_is_preset === 101
            }
          }, {
            id: 'unlock',
            title: '解锁用户',
            type: 'success',
            icon: 'fa fa-unlock',
            permission: 'unlock',
            isDisabled (params, entity) {
              return entity.f_status !== 102
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
              return entity.f_status === 103 || entity.f_is_preset === 101
            },
            onClick (params, entity) {
              params.context.featureComponent.onResetPassword(entity)
            }
          }]
        },
        width: 120
      }]
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
        this.gridOptions.context.params.totalCount = null
        this.gridOptions.api.setDatasource(this.gridOptions.datasource)
      }
    }
  }
</script>
