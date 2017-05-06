<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" style="max-height: 500px;overflow-y: auto;">
      <el-form label-width="100px" ref="menuForm" :inline="true" :model="entity" :rules="rules">
        <el-form-item label="父菜单编码" prop="f_parent_id">
          <el-input v-model="entity.f_parent_id" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="父菜单名称" prop="f_parent_name">
          <el-input v-model="entity.f_parent_name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="编码" prop="f_id">
          <el-input v-model="entity.f_id" :disabled="params.operation !== 'add'"></el-input>
        </el-form-item>
        <el-form-item label="名称" prop="f_name">
          <el-input v-model="entity.f_name"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="f_desc">
          <el-input v-model="entity.f_desc" type="textarea" autosize style="width: 496px;"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="f_type">
          <el-select v-model="entity.f_type" :disabled="params.operation !== 'add'">
            <el-option v-for="type in menuTypes"
              :key="type.f_item_code"
              :value="type.f_item_code"
              :label="type.f_item_text">
              {{type.f_item_text}}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="f_order">
          <el-input-number v-model="entity.f_order" :step="5"></el-input-number>
        </el-form-item>
        <el-form-item label="图标" prop="f_icon">
          <el-input v-model="entity.f_icon"></el-input>
        </el-form-item>
        <el-form-item label="路由路径" prop="f_route_path" v-show="entity.f_type === 2">
          <el-input v-model="entity.f_route_path"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="f_status">
          <el-radio-group v-model="entity.f_status" style="width: 496px;">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="2">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="适用平台">
          <div style="width: 496px;">
            <el-checkbox v-model="entity.f_is_web" :true-label="1" :false-label="2">Web</el-checkbox>
            <el-checkbox v-model="entity.f_is_android" :true-label="1" :false-label="2">Android</el-checkbox>
            <el-checkbox v-model="entity.f_is_ios" :true-label="1" :false-label="2">IOS</el-checkbox>
          </div>
        </el-form-item>
        <el-form-item label="备注" prop="f_remark">
          <el-input v-model="entity.f_remark" type="textarea" autosize style="width: 496px;"></el-input>
        </el-form-item>
        <div style="margin-bottom: 20px;" v-show="entity.f_type === 2 || entity.f_type === 3">
          <ag-grid-vue style="width: 596px; height: 200px;" class="ag-fresh jw-grid"
            :grid-options="urlGridOptions">
          </ag-grid-vue>
        </div>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button @click="onCancelForm('menuForm')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('menuForm')">确 定</el-button>
    </div>

    <el-dialog title="选择授权的URL" v-model="showSelectUrlDialog"
      :close-on-click-modal="false" :modal="false" :size="'large'">
      <url-selector ref="urlSelector" :mode="'selector'"></url-selector>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showSelectUrlDialog = false">取 消</el-button>
        <el-button type="primary" @click="onUrlSelected">选 择</el-button>
      </div>
    </el-dialog>
  </div>
</template>


<script type="text/ecmascript-6">
  import Vue from 'vue'
  import { AgGridVue } from 'ag-grid-vue'
  import Url from 'views/admin/platform/sys/url/view'

  export default {
    name: 'menuForm',
    components: {
      'ag-grid-vue': AgGridVue,
      'url-selector': Url
    },
    props: {
      params: {
        type: Object,
        default: function () {
          return {
            operation: 'view',
            entity: {}
          }
        }
      }
    },
    data () {
      return {
        url: 'api/platform/sys/menus',
        urlGridOptions: null,
        showSelectUrlDialog: false,
        menuTypes: [
          {f_item_code: 1, f_item_text: '目录'},
          {f_item_code: 2, f_item_text: '页面'},
          {f_item_code: 3, f_item_text: '按钮'},
          {f_item_code: 4, f_item_text: '令牌'}
        ],
        entity: {},
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
    computed: {},
    beforeMount () {
      var vm = this
      vm.urlGridOptions = this.$buildGridOptions({
        context: {
          parentComponent: this
        },
        rowModelType: 'normal',
        pagination: false,
        enableFilter: false,
        floatingFilter: false,
        enableServerSideFilter: false,
        enableServerSideSorting: false,
        suppressContextMenu: true,
        suppressMenuMainPanel: true,
        suppressMenuColumnPanel: true,
        rowData: [],
        columnDefs: [
          {
            headerName: '序号',
            headerComponentFramework: Vue.extend({
              template: `<div class="ag-header-component" style="padding: 5px;">
                              <button type="button" class="btn btn-xs btn-primary" title="增加" @click.prevent="onAdd">
                                <i class="fa fa-plus"></i>
                              </button>
                            </div>`,
              methods: {
                onAdd () {
                  this.params.context.parentComponent.onAddUrl()
                }
              }
            }),
            cellStyle: {'text-align': 'right'},
            cellRenderer: function (params) {
              return params.rowIndex + 1
            },
            width: 38
          },
          {
            headerName: 'URL',
            field: 'f_url',
            width: 400
          },
          {
            headerName: '提交方式',
            field: 'f_methods',
            width: 75
          },
          {
            headerName: '操作',
            field: '',
            cellRendererFramework: Vue.extend({
              template: `<div class="btn-group">
                            <button type="button" class="btn btn-xs btn-danger" @click.prevent="onRemove"title="删除" >
                              <i class="fa fa-trash"></i>
                            </button>
                          </div>`,
              methods: {
                onRemove () {
                  this.params.context.parentComponent.onRemoveUrl(this.params.node.data)
                }
              }
            }),
            suppressSorting: true,
            suppressMenu: true,
            suppressFilter: true,
            pinned: 'right',
            cellStyle: {'text-align': 'center'},
            width: 60
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
        this.query()
      },
      query (params) {
        var vm = this
        if (vm.params.operation === 'add') {
          var parentEntity = vm.params.parentEntity
          vm.entity = {
            f_id: parentEntity.f_parent_id ? (parentEntity.f_id + '-') : '',
            f_parent_id: parentEntity.f_id,
            f_parent_name: parentEntity.f_name,
            f_parent_path: parentEntity.f_parent_path + parentEntity.f_id + '/',
            f_type: parentEntity.f_type + 1,
            f_order: 10,
            f_status: 1,
            f_is_web: 1,
            f_is_android: 1,
            f_is_ios: 1
          }
          if (parentEntity.children && parentEntity.children.length > 0) {
            var lastChild = parentEntity.children[parentEntity.children.length - 1]
            vm.entity.f_order = lastChild.f_order + 10
          }
        } else {
          vm.$http.get(vm.url + '/' + vm.params.entity.f_id).then(function (response) {
            var result = response.body
            if (result.success) {
              vm.entity = result.data
            } else {
              vm.entity = {urlList: []}
            }
            vm.urlGridOptions.api.setRowData(vm.entity.urlList)
          })
        }
      },
      onAddUrl () {
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
      onUrlSelected () {
        var vm = this
        vm.showSelectUrlDialog = false
        var selectedUrlList = vm.$refs.urlSelector.getSelectedRows()
        console.debug('onUrlSelected', selectedUrlList)
        selectedUrlList.forEach(function (selectedUrl) {
          var exist = vm.entity.urlList.some(function (url) {
            return url.f_url_id === selectedUrl.f_id
          })

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
      },
      onCancelForm (formName) {
        this.$emit('cancel')
      },
      onSubmitForm (formName) {
        var vm = this
        vm.$refs[formName].validate(function (valid) {
          if (valid) {
            if (vm.params.operation === 'add') {
              vm.$http.post(vm.url, vm.entity).then(function (response) {
                console && console.debug(response.body)
                if (response.body.success) {
                  this.$emit('submit')
                }
              })
            } else {
              vm.$http.put(vm.url + '/' + vm.params.entity.f_id, vm.entity).then(function (response) {
                console && console.debug(response.body)
                if (response.body.success) {
                  this.$emit('submit')
                }
              })
            }

            return true
          }

          return false
        })
      }
    }
  }
</script>
