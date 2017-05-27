<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div :style="contentStyle">
    <ag-grid-vue style="width: 100%; height: 100%;" class="ag-fresh jw-grid" :grid-options="gridOptions"></ag-grid-vue>

    <el-dialog v-model="formOptions.isShow" :title="formOptions.title" :close-on-click-modal="false" :modal="true"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <role-form :form-options="formOptions" v-if="formOptions.isShow"></role-form>
    </el-dialog>

    <el-dialog v-model="authorizeFormOptions.isShow" :title="authorizeFormOptions.title" :close-on-click-modal="false"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <role-authorize-form :form-options="authorizeFormOptions"
        v-if="authorizeFormOptions.isShow">
      </role-authorize-form>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import { AgGridVue } from 'ag-grid-vue'
  import AddHeaderComponenetFramework from 'components/ag-grid/AddHeaderComponenetFramework'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import IndexRendererFramework from 'components/ag-grid/IndexRendererFramework'
  import ViewRendererFramework from 'components/ag-grid/ViewRendererFramework'
  import DictRendererFramework from 'components/ag-grid/DictRendererFramework'
  import OperationRendererFramework from 'components/ag-grid/OperationRendererFramework'
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
        featureOptions: {
          name: '角色',
          url: 'api/platform/sys/roles',
          permission: {
            authorize: this.$jw.hasPermission('XTGL-JSGL-SQ'),
            add: this.$jw.hasPermission('XTGL-JSGL-ZJ'),
            edit: this.$jw.hasPermission('XTGL-JSGL-XG'),
            remove: this.$jw.hasPermission('XTGL-JSGL-SC')
          }
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
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 24
        },
        {
          headerName: '#',
          pinned: 'left',
          headerComponentFramework: AddHeaderComponenetFramework,
          cellStyle: {'text-align': 'right'},
          cellRendererFramework: IndexRendererFramework,
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 38
        },
        {
          headerName: '角色名称',
          field: 'f_name',
          pinned: 'left',
          filterFramework: LikeFilterFramework,
          cellRendererFramework: ViewRendererFramework,
          suppressSorting: true,
          suppressMenu: true,
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
          field: 'f_is_preset',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: DictRendererFramework,
          cellRendererParams: {dict: 'YesNo'},
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
              isDisabled: function (entity) {
                return entity.f_is_preset === 1
              }
            }]
          },
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
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
