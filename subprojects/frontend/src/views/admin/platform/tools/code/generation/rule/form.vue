<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" :style="formBodyStyle">
      <el-form ref="form" :model="entity" :rules="rules" :inline="true" :label-width="labelWidth">
        <fieldset :disabled="formOptions.operation === 'view'">
          <el-form-item label="规则编码" prop="f_code">
            <el-input class="jw-field-col-1" v-model="entity.f_code"></el-input>
          </el-form-item>
          <el-form-item label="规则名称" prop="f_name">
            <el-input class="jw-field-col-1" v-model="entity.f_name"></el-input>
          </el-form-item>
          <el-form-item label="父级菜单" prop="f_menu_parent_id">
            <el-select class="jw-field-col-1" v-model="entity.f_menu_parent_id" @change="onParentMenuChange">
              <el-option v-for="menu in menuList"
                :key="menu.f_id"
                :value="menu.f_id"
                :label="menu.f_name">
                {{menu.f_name}}
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="菜单路径" prop="f_menu_parent_path">
            <el-input class="jw-field-col-1" v-model="entity.f_menu_parent_path" disabled></el-input>
          </el-form-item>
          <el-form-item label="菜单编码" prop="f_menu_id">
            <el-input class="jw-field-col-1" v-model="entity.f_menu_id"></el-input>
          </el-form-item>
          <el-form-item label="菜单名称" prop="f_menu_name">
            <el-input class="jw-field-col-1" v-model="entity.f_menu_name"></el-input>
          </el-form-item>
          <el-form-item label="菜单描述" prop="f_menu_remark">
            <el-input class="jw-field-col-1" v-model="entity.f_menu_remark"></el-input>
          </el-form-item>
          <el-form-item label="排序" prop="f_menu_order">
            <el-input-number class="jw-field-col-1" v-model="entity.f_menu_order" :step="5"></el-input-number>
          </el-form-item>
          <el-form-item label="URL" prop="f_request_url">
            <el-input class="jw-field-col-2" v-model="entity.f_request_url"></el-input>
          </el-form-item>
          <el-form-item label="模块包名" prop="f_package_name">
            <el-input class="jw-field-col-2" v-model="entity.f_package_name"></el-input>
          </el-form-item>

          <generation-rule-table style="height: 302px;" :operation="formOptions.operation" :generate-rule="entity">
          </generation-rule-table>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')" :disabled="formOptions.operation === 'view'">确 定
      </el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  import GenerationRuleTable from '../table/view'

  export default {
    name: 'generationRuleForm',
    components: {
      GenerationRuleTable
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
        menuList: [],
        entity: {},
        rules: {}
      }
    },
    computed: {
      formBodyStyle () {
        return {
          'max-height': (this.formOptions.maxHeight ? this.formOptions.maxHeight : 500) + 'px',
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
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
      this._init()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      onParentMenuChange (val) {
        var vm = this
        vm.menuList.forEach((menu) => {
          if (menu.f_id === val) {
            if (!vm.entity.f_menu_id || vm.entity.f_menu_id.indexOf(menu.f_id) !== 0) {
              vm.entity.f_menu_id = menu.f_id + '-'
              vm.entity.f_menu_name = vm.entity.f_name ? (vm.entity.f_name + '管理') : null
              vm.entity.f_menu_remark = vm.entity.f_name ? (vm.entity.f_name + '管理页面') : null
              vm.entity.f_menu_order = (menu.children && menu.children.length > 0) ? (menu.children[menu.children.length - 1].f_order + 10) : 10
              vm.entity.f_menu_parent_path = menu.f_parent_path + menu.f_id + '/'
            }
            return true // forEach中，返回true为跳出循环
          }
        })
      },
      _addMenu (menuTree) {
        var vm = this
        menuTree.forEach(function (menu) {
          if (menu.f_type < 2) {
            vm.menuList.push(menu)
          }
          if (menu.children && menu.children.length > 0) {
            vm._addMenu(menu.children)
          }
        })
      },
      _init () {
        this.menuList = []
        this._addMenu(this.$store.state.menuList)
        this.query()
      },
      query (params) {
        var vm = this
        if (vm.formOptions.operation === 'add') {
          vm.entity = vm._createEntity()
        } else {
          vm.$http.get(vm.featureOptions.url + '/' + vm.formOptions.params.f_id).then((response) => {
            vm.entity = response.body.success ? response.body.data : {}
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

          console.debug('onSubmitForm', vm.entity)
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
      _createEntity () {
        return {
          f_menu_parent_id: 'XTGL',
          f_menu_parent_path: '/ROOT/XTGL/',
          f_request_url: '/api/',
          f_package_name: 'com.jeeweb.',
          tableList: []
        }
      },
      _submitted (response) {
        if (response.body.success) {
          this._closeForm()
          this._refreshGrid()

          this.$emit('submit', {type: this.formOptions.operation, data: response.body.data})
        }
      },
      _closeForm () {
        if (this.formOptions.context.featureComponent.formOptions) {
          this.formOptions.context.featureComponent.formOptions.isShow = false
        }
      },
      _refreshGrid () {
        if (this.formOptions.context.featureComponent.gridOptions) {
          this.formOptions.context.featureComponent.gridOptions.context.params.totalCount = 0
          this.formOptions.context.featureComponent.gridOptions.api.setDatasource(this.formOptions.context.featureComponent.gridOptions.datasource)
        }
      }
    }
  }
</script>
