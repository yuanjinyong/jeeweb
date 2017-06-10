<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" :style="formBodyStyle">
      <el-form ref="form" :model="entity" :rules="rules" :inline="true" :label-width="labelWidth">
        <fieldset :disabled="formOptions.operation === 'view'">
          <el-form-item label="父菜单编码" prop="f_parent_id">
            <el-input class="jw-field-col-1" v-model="entity.f_parent_id" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="父菜单名称" prop="f_parent_name">
            <el-input class="jw-field-col-1" v-model="entity.f_parent_name" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="编码" prop="f_id">
            <el-input class="jw-field-col-1" v-model="entity.f_id" :disabled="formOptions.operation !== 'add'">
            </el-input>
          </el-form-item>
          <el-form-item label="名称" prop="f_name">
            <el-input class="jw-field-col-1" v-model="entity.f_name"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="f_desc">
            <el-input class="jw-field-col-2" v-model="entity.f_desc" type="textarea" autosize></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="f_type">
            <el-select class="jw-field-col-1" v-model="entity.f_type" :disabled="formOptions.operation !== 'add'">
              <el-option v-for="type in menuTypes"
                         :key="type.f_item_code"
                         :value="type.f_item_code"
                         :label="type.f_item_text"
                         :disabled="type.f_item_code === 0">
                {{type.f_item_text}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="排序" prop="f_order">
            <el-input-number class="jw-field-col-1" v-model="entity.f_order" :step="5"></el-input-number>
          </el-form-item>
          <el-form-item label="图标" prop="f_icon">
            <el-input class="jw-field-col-1" v-model="entity.f_icon"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="f_status">
            <el-radio-group class="jw-field-col-1" v-model="entity.f_status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="2">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="适用平台">
            <div class="jw-field-col-2">
              <el-checkbox v-model="entity.f_is_web" :true-label="1" :false-label="2">Web</el-checkbox>
              <el-checkbox v-model="entity.f_is_android" :true-label="1" :false-label="2">Android</el-checkbox>
              <el-checkbox v-model="entity.f_is_ios" :true-label="1" :false-label="2">IOS</el-checkbox>
            </div>
          </el-form-item>
          <el-form-item label="路由路径" prop="f_route_path" v-show="entity.f_type === 2">
            <el-input class="jw-field-col-2" v-model="entity.f_route_path"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="f_remark">
            <el-input class="jw-field-col-2" v-model="entity.f_remark" type="textarea" autosize></el-input>
          </el-form-item>

          <div style="height: 200px;" v-show="entity.f_type === 2 || entity.f_type === 3">
            <ag-grid class="ag-fresh jw-grid" :grid-options="urlGridOptions"></ag-grid>
          </div>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')" :disabled="formOptions.operation === 'view'">确 定
      </el-button>
    </div>

    <el-dialog title="选择授权的URL" v-model="showSelectUrlDialog"
               :close-on-click-modal="false" :modal="false" :size="'large'" :top="'20px'"
               :custom-class="'jw-dialog jw-sub-dialog'">
      <url-view ref="urlSelector" :mode="'selector'"></url-view>
      <div slot="footer">
        <el-button @click="showSelectUrlDialog = false">取 消</el-button>
        <el-button type="primary" @click="onSelected">选 择</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import {
    AddHeaderComponenetFramework,
    IndexRendererFramework,
    OperationRendererFramework
  } from 'components/ag-grid'
  import {UrlView} from 'views'

  export default {
    name: 'menuForm',
    components: {
      UrlView
    },
    props: {
      formOptions: {
        type: Object,
        default: function () {
          return {
            operation: 'view',
            title: '查看详情',
            maxHeight: 500,
            labelWidth: 100,
            params: {},
            context: {
              featureComponent: {}
            }
          }
        }
      }
    },
    data () {
      return {
        urlGridOptions: this.$grid.buildOptions({
          context: {
            featureComponent: this
          },
          rowModelType: 'normal',
          rowData: [],
          pagination: false,
          enableFilter: false,
          floatingFilter: false
        }),
        showSelectUrlDialog: false,
        menuTypes: [
          {f_item_code: 0, f_item_text: '根'},
          {f_item_code: 1, f_item_text: '目录'},
          {f_item_code: 2, f_item_text: '页面'},
          {f_item_code: 3, f_item_text: '按钮'},
          {f_item_code: 4, f_item_text: '令牌'}
        ],
        url: 'api/platform/sys/menus',
        entity: {urlList: []},
        rules: {
          f_id: [
            {required: true, message: '请输入菜单编码', trigger: 'blur'},
            {max: 64, message: '长度在64个字符以内', trigger: 'blur'}
          ],
          f_name: [
            {required: true, message: '请输入菜单名称', trigger: 'blur'},
            {max: 256, message: '长度在256个字符以内', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      formBodyStyle () {
        return {
          'max-height': (this.formOptions.maxHeight ? this.formOptions.maxHeight : this.$store.state.layout.dialog.height) + 'px',
          'overflow-y': 'auto'
        }
      },
      labelWidth () {
        return (this.formOptions.labelWidth ? this.formOptions.labelWidth : 150) + 'px'
      },
      featureOptions () {
        return this.formOptions.context.featureComponent.featureOptions
      }
    },
    created () {
      var vm = this
      vm.urlGridOptions.columnDefs = [
        {
          headerName: '序号',
          headerComponentFramework: AddHeaderComponenetFramework,
          headerComponentParams: {
            operation: {
              onClick (params, entity) {
                vm.onAdd()
              }
            }
          },
          cellStyle: {'text-align': 'right'},
          cellRendererFramework: IndexRendererFramework,
          width: 38
        },
        {
          headerName: 'URL',
          field: 'f_url',
          width: 535 - 15
        },
        {
          headerName: '提交方式',
          field: 'f_methods',
          width: 92
        },
        {
          headerName: '操作',
          field: '',
          cellStyle: {'text-align': 'center'},
          cellRendererFramework: OperationRendererFramework,
          cellRendererParams: {
            operations: [{
              id: 'remove',
              title: '删除URL',
              isDisabled: function (entity) {
                return vm.formOptions.operation === 'view'
              },
              onClick (params, entity) {
                params.context.featureComponent.onRemoveUrl(entity)
              }
            }]
          },
          width: 48
        }
      ]
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
        this.query()
      },
      query (params) {
        var vm = this
        if (vm.formOptions.operation === 'add') {
          vm.entity = vm._createEntity(vm.formOptions.params)
          vm.urlGridOptions.api.setRowData(vm.entity.urlList)
        } else {
          vm.$http.get(vm.featureOptions.url + '/' + vm.formOptions.params.f_id).then((response) => {
            vm.entity = response.body.success ? response.body.data : {urlList: []}
            vm.urlGridOptions.api.setRowData(vm.entity.urlList)
          })
        }
      },
      onCancelForm (formName) {
        this._closeForm()
        this.$emit('cancel')
      },
      onSubmitForm (formName) {
        var vm = this
        vm.$refs[formName].validate(function (valid) {
          if (!valid) {
            return false
          }

          if (vm.formOptions.operation === 'add') {
            vm.$http.post(vm.featureOptions.url, vm.entity).then((response) => {
              vm._submitted(response)
            })
          } else {
            vm.$http.put(vm.featureOptions.url + '/' + vm.formOptions.params.f_id, vm.entity).then((response) => {
              vm._submitted(response)
            })
          }

          return true
        })
      },
      _createEntity (parentEntity) {
        var order = 10
        if (parentEntity.children && parentEntity.children.length > 0) {
          var lastChild = parentEntity.children[parentEntity.children.length - 1]
          order = lastChild.f_order + order
        }

        return {
          f_id: parentEntity.f_parent_id ? (parentEntity.f_id + '-') : '',
          f_parent_id: parentEntity.f_id,
          f_parent_name: parentEntity.f_name,
          f_parent_path: parentEntity.f_parent_path + parentEntity.f_id + '/',
          f_type: parentEntity.f_type + 1,
          f_order: order,
          f_status: 1,
          f_is_web: 1,
          f_is_android: 1,
          f_is_ios: 1,
          urlList: []
        }
      },
      _submitted (response) {
        if (response.body.success) {
          this._closeForm()
          this.$emit('submit', {type: this.formOptions.operation, data: response.body.data})
        }
      },
      _closeForm () {
        if (this.formOptions.context.featureComponent.formOptions) {
          this.formOptions.context.featureComponent.formOptions.isShow = false
        }
      },
      onAdd () {
        this.showSelectUrlDialog = true
      },
      onRemoveUrl (url) {
        var vm = this
        for (var i = 0; i < vm.entity.urlList.length; i++) {
          if (vm.entity.urlList[i].f_url_id === url.f_url_id) {
            vm.entity.urlList.splice(i, 1)
            break
          }
        }
        vm.urlGridOptions.api.setRowData(vm.entity.urlList)
      },
      onSelected () {
        var vm = this
        vm.showSelectUrlDialog = false
        var selectedUrlList = vm.$refs.urlSelector.getSelectedRows()
        selectedUrlList.forEach(function (selectedUrl) {
          var exist = false
          if (vm.entity.urlList) {
            exist = vm.entity.urlList.some(function (url) {
              return url.f_url_id === selectedUrl.f_id
            })
          } else {
            vm.entity.urlList = []
          }

          if (!exist) {
            vm.entity.urlList.push({
              f_url_id: selectedUrl.f_id,
              f_url: selectedUrl.f_url,
              f_description: selectedUrl.f_description,
              f_patterns: selectedUrl.f_patterns,
              f_methods: selectedUrl.f_methods,
              f_handler_method: selectedUrl.f_handler_method
            })
          }
        })

        vm.$refs.urlSelector.clearSelectedRows()
        vm.urlGridOptions.api.setRowData(vm.entity.urlList)
      }
    }
  }
</script>
