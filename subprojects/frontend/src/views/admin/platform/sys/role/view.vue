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
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'
  import AddHeaderComponenetFramework from 'components/ag-grid/AddHeaderComponenetFramework'
  import LikeFilterFramework from 'components/ag-grid/LikeFilterFramework'
  import IndexRendernerFramework from 'components/ag-grid/IndexRendernerFramework'
  import ViewRendernerFramework from 'components/ag-grid/ViewRendernerFramework'
  import DictRendernerFramework from 'components/ag-grid/DictRendernerFramework'
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
          name: 'URL',
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
          title: '查看URL',
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
      var vm = this
      vm.gridOptions.columnDefs = [
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
          cellRendererFramework: IndexRendernerFramework,
          suppressSorting: true,
          suppressMenu: true,
          suppressFilter: true,
          width: 38
        },
        {
          headerName: '角色名称',
          field: 'f_name',
          pinned: 'left',
          cellRendererFramework: ViewRendernerFramework,
          filterFramework: LikeFilterFramework,
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
          cellRendererFramework: DictRendernerFramework,
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
          cellRendererFramework: Vue.extend({
            template: `<el-button-group>
                          <el-button type="warning" size="mini" title="授权可以操作的功能"
                            @click.prevent="onAuthorize" :disabled="!permission.authorize">
                            <i class="fa fa-key"></i>
                          </el-button>
                          <el-button type="info" size="mini" title="修改"
                            @click.prevent="onEdit" :disabled="!permission.edit">
                            <i class="fa fa-edit"></i>
                          </el-button>
                          <el-button type="danger" size="mini" title="删除"
                            @click.prevent="onRemove" :disabled="!permission.remove || (entity.f_is_preset === 1)">
                              <i class="fa fa-trash"></i>
                          </el-button>
                        </el-button-group>`,
            computed: {
              permission () {
                return this.params.context.featureComponent.featureOptions.permission
              },
              entity () {
                return this.params.node.data ? this.params.node.data : {}
              }
            },
            methods: {
              onAuthorize () {
                this.params.context.featureComponent.onAuthorize(this.params.node.data)
              },
              onEdit () {
                this.params.context.featureComponent.onEdit(this.params.node.data)
              },
              onRemove () {
                this.params.context.featureComponent.onRemove(this.params.node.data)
              }
            }
          }),
          pinned: 'right',
          cellStyle: {'text-align': 'center'},
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
      onEdit (entity) {
        this.formOptions.operation = 'edit'
        this.formOptions.title = '修改' + this.featureOptions.name
        this.formOptions.params = entity
        this.formOptions.isShow = true
      },
      onRemove (entity) {
        var vm = this
        vm.$confirm('确定要删除所选的角色吗?', '删除菜单', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          vm.$http.delete(vm.featureOptions.url + '/' + entity.f_id).then(function (response) {
            if (response.body.success) {
              this.gridOptions.context.params.totalCount = 0
              this.gridOptions.api.setDatasource(this.gridOptions.datasource)
            }
          })
        })
      },
      onAuthorize (entity) {
        this.authorizeFormOptions.params = entity
        this.authorizeFormOptions.isShow = true
      }
    }
  }
</script>
