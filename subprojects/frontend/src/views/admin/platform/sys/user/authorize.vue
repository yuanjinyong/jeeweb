<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" :style="formBodyStyle">
      <el-form ref="form" :model="entity" :rules="rules" :inline="true" :label-width="labelWidth">
        <fieldset :disabled="formOptions.operation === 'view'">
          <el-tree ref="menuTree"
            show-checkbox
            node-key="f_id"
            :props="treeOptions"
            :check-strictly="true"
            :default-expanded-keys="expandedMenuIds"
            :default-checked-keys="checkedMenuIds"
            :data="menus"
            @check-change="onCheckChange">
          </el-tree>
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
  export default {
    name: 'userAuthorizeForm',
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
        entity: {},
        rules: {},
        menus: [],
        expandedMenuIds: [],
        checkedMenuIds: [],
        treeOptions: {
          children: 'children',
          label: 'f_name'
        }
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
      _init () {
        this.query()
      },
      query (params) {
        var vm = this
        vm.$http.get(vm.featureOptions.url + '/' + vm.formOptions.params.f_id + '/menus').then(function (response) {
          if (response.body.success) {
            vm.menus = response.body.data
            vm._updateCheckedNode(response.body.data)
          } else {
            vm.menus = []
          }
        })
      },
      _updateCheckedNode (menus) {
        var vm = this
        menus.forEach((menu) => {
          if (menu.f_menu_id) {
            // vm.$refs.menuTree.setChecked(menu, true, false)
            vm.checkedMenuIds.push(menu.f_menu_id)
          }
          if (menu.f_type < 2) {
            vm.expandedMenuIds.push(menu.f_id)
          }
          if (menu.children && menu.children.length > 0) {
            vm._updateCheckedNode(menu.children)
          }
        })
      },
      _getParent (treeNodes, child) {
        var vm = this
        var parent = null
        treeNodes.every((node) => {
          var parentPath = node.f_parent_path + node.f_id + '/'
          if (node.f_id === child.f_parent_id) {
            parent = node
            return false
          }
          if (child.f_parent_path.substr(0, parentPath.length) === parentPath) {
            parent = vm._getParent(node.children, child)
            return false
          }
          return true
        })

        return parent
      },
      onCheckChange (data, checked, indeterminate) {
        var vm = this
        if (checked) {
          // vm.$refs.menuTree.setChecked(data, checked, true) // 勾选所有的子节点
          data.f_menu_id = data.f_id

          var menu = this._getParent(this.menus, data)
          if (menu && !menu.f_menu_id) {
            menu.f_menu_id = menu.f_id
            vm.$refs.menuTree.setChecked(menu, checked, false) // 勾选父节点
          }
        } else {
          data.f_menu_id = null
          if (data.children && data.children.length) {
            data.children.forEach((menu) => {
              vm.$refs.menuTree.setChecked(menu, checked, true) // 不勾选所有的子节点
            })
          }
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

          var selectedMenuIds = vm.$refs.menuTree.getCheckedKeys()
          vm.$http.post(vm.featureOptions.url + '/' + vm.formOptions.params.f_id + '/menus', {f_menu_ids: selectedMenuIds.join(',')}, {emulateJSON: true}).then(function (response) {
            vm._submitted(response)
          })

          return true
        })
      },
      _submitted (response) {
        if (response.body.success) {
          this._closeForm()
          this.$emit('submit', {type: this.formOptions.operation, data: response.body.data})
        }
      },
      _closeForm () {
        if (this.formOptions.context.featureComponent.authorizeFormOptions) {
          this.formOptions.context.featureComponent.authorizeFormOptions.isShow = false
        }
      }
    }
  }
</script>
