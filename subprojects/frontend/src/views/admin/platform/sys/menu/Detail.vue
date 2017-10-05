<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="父菜单编码" prop="f_parent_id">
        <el-input v-model="entity.f_parent_id" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="父菜单名称" prop="f_parent_name">
        <el-input v-model="entity.f_parent_name" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="编码" prop="f_id">
        <el-input v-model="entity.f_id" :disabled="options.operation !== 'add'"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="名称" prop="f_name">
        <el-input v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="类型" prop="f_type">
        <jw-dict v-model="entity.f_type" :dict-code="'MenuType'" :dict-options="typeDictOptions"
                 :disabled="options.operation !== 'add'">
        </jw-dict>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="描述" prop="f_desc">
        <el-input v-model="entity.f_desc" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="排序" prop="f_order">
        <el-input-number v-model="entity.f_order" :step="5"></el-input-number>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="图标" prop="f_icon">
        <el-input v-model="entity.f_icon"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="状态" prop="f_status">
        <el-radio-group v-model="entity.f_status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="2">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="适用平台">
        <el-checkbox v-model="entity.f_is_web" :true-label="1" :false-label="2">Web</el-checkbox>
        <el-checkbox v-model="entity.f_is_android" :true-label="1" :false-label="2">Android</el-checkbox>
        <el-checkbox v-model="entity.f_is_ios" :true-label="1" :false-label="2">IOS</el-checkbox>
      </el-form-item>
      <el-form-item class="jw-field jw-field-3" label="路由路径" prop="f_route_path" v-show="entity.f_type === 2">
        <el-input v-model="entity.f_route_path"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="备注" prop="f_remark">
        <el-input v-model="entity.f_remark" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="授权的URL" v-show="entity.f_type === 2 || entity.f_type === 3">
        <ag-grid ref="grid" class="ag-fresh jw-grid" style="height: 200px;" :grid-options="gridOptions"></ag-grid>
      </el-form-item>
    </template>

    <template slot="other">
      <jw-selector ref="selector" :selector-options="selectorOptions">
        <url-view ref="urlView" :mode="'selector'" :multiple="true"></url-view>
      </jw-selector>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'
  import {
    AddHeaderComponenetFramework,
    IndexRendererFramework,
    OperationRendererFramework
  } from 'components/ag-grid'

  export default {
    name: 'menuDetail',
    mixins: [DetailMixin],
    components: {
      UrlView: r => require.ensure([], () => r(require('views/admin/platform/sys/url/View')), 'platform-sys-url')
    },
    data () {
      return {
        typeDictOptions: {
          context: {
            featureComponent: this
          },
          isDisabled (item) {
            let vm = this.options.context.featureComponent
            if (item.f_item_code === '0') {
              return true
            }

            if (vm.options.params.f_type < 2) {
              // 父级为根和目录时，下面只能挂目录和页面
              if (!(item.f_item_code === '1' || item.f_item_code === '2')) {
                return true
              }
            } else if (vm.options.params.f_type === 2) {
              // 父级为页面时，下面只能挂按钮和令牌
              if (!(item.f_item_code === '3' || item.f_item_code === '4')) {
                return true
              }
            }

            return false
          }
        },
        selectorOptions: {
          context: {
            name: '授权的URL',
            featureComponent: this,
            getViewComponent (options) {
              return options.context.featureComponent.$refs['urlView']
            }
          },
          selected (selectedRows, cb) {
            this.options.context.featureComponent.onSelected(selectedRows, cb)
          }
        },
        gridOptions: this.$grid.buildOptions({
          rowModelType: 'inMemory',
          pagination: false,
          enableFilter: false,
          floatingFilter: false,
          context: {
            featureComponent: this,
            getPermissions (params, operation) {
              return {add: true, remove: true}
            }
          }
        }),
        options: {
          context: {
            name: '菜单',
            url: 'api/platform/sys/menus',
            detailComponent: this
          },
          createEntity (options, cb) {
            let parentEntity = options.params
            let children = parentEntity.children
            let order = 10
            if (children && children.length > 0) {
              order = children[children.length - 1].f_order + order
            }

            let entity = {
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

            cb(entity)
            options.context.detailComponent.gridOptions.setData(entity.urlList)
          },
          loadRemoteEntity (options, cb) {
            this.$http.get(options.context.url + '/' + options.params.f_id).then((response) => {
              let entity = response.body.success ? response.body.data : {urlList: []}
              cb(entity)
              options.context.detailComponent.gridOptions.setData(entity.urlList)
            })
          }
        },
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
    created () {
      this.gridOptions.columnDefs = [{
        headerName: '序号',
        headerComponentFramework: AddHeaderComponenetFramework,
        headerComponentParams: {
          operation: {
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view'
            },
            onClick (params, entity) {
              params.context.featureComponent.$refs['selector'].open()
            }
          }
        },
        pinned: 'left',
        cellStyle: {'text-align': 'right'},
        cellRendererFramework: IndexRendererFramework,
        width: 38
      }, {
        headerName: 'URL',
        field: 'f_url',
        tooltipField: 'f_url',
        suppressSizeToFit: false,
        width: 300
      }, {
        headerName: '提交方式',
        field: 'f_methods',
        width: 80
      }, {
        headerName: '操作',
        field: '',
        pinned: 'right',
        cellStyle: {'text-align': 'center'},
        cellRendererFramework: OperationRendererFramework,
        cellRendererParams: {
          operations: [{
            id: 'remove',
            title: '删除URL',
            isDisabled (params, entity) {
              return params.context.featureComponent.options.operation === 'view'
            },
            onClick (params, entity) {
              params.context.featureComponent.onRemoveUrl(entity)
            }
          }]
        },
        suppressResize: true,
        width: 36
      }]
    },
    methods: {
      onRemoveUrl (url) {
        for (let i = 0; i < this.entity.urlList.length; i++) {
          if (this.entity.urlList[i].f_url_id === url.f_url_id) {
            this.entity.urlList.splice(i, 1)
            break
          }
        }
        this.gridOptions.api.setRowData(this.entity.urlList)
      },
      onSelected (selectedRows, cb) {
        selectedRows.forEach((selectedUrl) => {
          let exist = false
          if (this.entity.urlList) {
            exist = this.entity.urlList.some((url) => {
              return url.f_url_id === selectedUrl.f_id
            })
          } else {
            this.entity.urlList = []
          }

          if (!exist) {
            this.entity.urlList.push({
              f_url_id: selectedUrl.f_id,
              f_url: selectedUrl.f_url,
              f_description: selectedUrl.f_description,
              f_patterns: selectedUrl.f_patterns,
              f_methods: selectedUrl.f_methods,
              f_handler_method: selectedUrl.f_handler_method
            })
          }
        })

        this.gridOptions.api.setRowData(this.entity.urlList)
        cb(true)
      }
    }
  }
</script>
