<template>
  <div :style="contentStyle">
    <ag-grid class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid>

    <el-dialog v-draggable v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false"
               :modal="true" :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <role-form :form-options="formOptions" v-if="formOptions.isShow"></role-form>
    </el-dialog>

    <el-dialog v-draggable v-model="authorizeFormOptions.isShow" :title="authorizeFormOptions.title"
               :close-on-click-modal="false" :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <role-authorize-form :form-options="authorizeFormOptions" v-if="authorizeFormOptions.isShow">
      </role-authorize-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import {
    AddHeaderComponenetFramework,
    DictRendererFramework,
    LikeFilterFramework,
    LikeFloatingFilterComponentFramework,
    IndexRendererFramework,
    OperationRendererFramework,
    ViewRendererFramework
  } from 'components/ag-grid'
  import RoleForm from './form'
  import RoleAuthorizeForm from './authorize'
  //  import {RoleForm, RoleAuthorizeForm} from 'views'

  export default {
    name: 'roleView',
    components: {
      'role-form': RoleForm,
      'RoleAuthorizeForm': RoleAuthorizeForm
    },
    data () {
      return {
        featureOptions: {
          name: '角色',
          url: 'api/platform/sys/roles'
        },
        formOptions: {
          isShow: false,
          operation: 'view',
          title: '查看详情',
          maxHeight: this.mode === 'selector' ? 400 : 500,
          params: {},
          context: {
            featureComponent: this
          }
        },
        gridOptions: this.$grid.buildOptions({
          context: {
            featureComponent: this,
            params: {
              orderBy: 'f_is_preset,f_name',
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
          authorize: this.hasPermission('XTGL-JSGL-SQ'),
          add: this.hasPermission('XTGL-JSGL-ZJ'),
          edit: this.hasPermission('XTGL-JSGL-XG'),
          remove: this.hasPermission('XTGL-JSGL-SC')
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
          headerName: '角色名称',
          field: 'f_name',
          pinned: 'left',
          suppressSorting: false,
          suppressFilter: false,
          filterFramework: LikeFilterFramework,
          floatingFilterComponentFramework: LikeFloatingFilterComponentFramework,
          cellRendererFramework: ViewRendererFramework,
          width: 160
        },
        {
          headerName: '角色描述',
          field: 'f_desc',
          tooltipField: 'f_desc',
          width: 300
        },
        {
          headerName: '是否预置',
          field: 'f_is_preset',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
          width: 75
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
              onClick (params, entity) {
                params.context.featureComponent.onAuthorize(entity)
              }
            }, {
              id: 'edit',
              permission: 'edit'
            }, {
              id: 'remove',
              permission: 'remove',
              isDisabled: function (params, entity) {
                return entity.f_is_preset === 1
              }
            }]
          },
          width: 80
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
      }
    }
  }
</script>
