<template>
  <jw-form ref="form" :form-options="options" :entity="entity">
    <template slot="fieldset">
      <el-tree ref="menuTree"
               show-checkbox
               node-key="f_id"
               :props="treeOptions.props"
               :check-strictly="true"
               :default-expanded-keys="treeOptions.expandedMenuIds"
               :default-checked-keys="treeOptions.checkedMenuIds"
               :data="treeOptions.nodes"
               @check-change="onCheckChange">
      </el-tree>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'roleAuthorizeDetail',
    mixins: [DetailMixin],
    data () {
      return {
        treeOptions: {
          menus: [],
          expandedMenuIds: [],
          checkedMenuIds: [],
          props: {
            children: 'children',
            label: 'f_name'
          }
        },
        options: {
          context: {
            name: '角色',
            url: 'api/platform/sys/roles',
            detailComponent: this
          },
          loadRemoteEntity (options) {
            return options.context.detailComponent._loadEntity()
          },
          submitEntity (options) {
            options.context.detailComponent._submitEntity()
          }
        },
        entity: {}
      }
    },
    methods: {
      _submitEntity () {
        let selectedMenuIds = this.$refs['menuTree'].getCheckedKeys()
        this.$http.post(this.options.context.url + '/' + this.options.params.f_id + '/menus', {f_menu_ids: selectedMenuIds.join(',')}, {emulateJSON: true}).then((response) => {
          if (response.body.success) {
            this.$refs['form'].submitted(response.body)
          }
        })
      },
      _loadEntity () {
        this.$http.get(this.options.context.url + '/' + this.options.params.f_id + '/menus').then((response) => {
          this.treeOptions.nodes = response.body.success ? response.body.data : []
          this._updateCheckedNode(this.treeOptions.nodes)
          this.entity = {f_menu_ids: this.treeOptions.checkedMenuIds.join(',')}
        })
      },
      _updateCheckedNode (nodes) {
        nodes.forEach((node) => {
          if (node.f_menu_id) {
            this.treeOptions.checkedMenuIds.push(node.f_menu_id)
          }
          if (node.f_type < 2) {
            this.treeOptions.expandedMenuIds.push(node.f_id)
          }
          if (node.children && node.children.length > 0) {
            this._updateCheckedNode(node.children)
          }
        })
      },
      _getParent (treeNodes, child) {
        let parent = null
        treeNodes.every((node) => {
          let parentPath = node.f_parent_path + node.f_id + '/'
          if (node.f_id === child.f_parent_id) {
            parent = node
            return false
          }
          if (child.f_parent_path.substr(0, parentPath.length) === parentPath) {
            parent = this._getParent(node.children, child)
            return false
          }
          return true
        })

        return parent
      },
      onCheckChange (data, checked, indeterminate) {
        if (checked) {
          data.f_menu_id = data.f_id

          let node = this._getParent(this.treeOptions.nodes, data)
          if (node && !node.f_menu_id) {
            node.f_menu_id = node.f_id
            this.$refs['menuTree'].setChecked(node, checked, false) // 勾选父节点
          }
        } else {
          data.f_menu_id = null
          if (data.children && data.children.length) {
            data.children.forEach((node) => {
              this.$refs['menuTree'].setChecked(node, checked, true) // 不勾选所有的子节点
            })
          }
        }
      }
    }
  }
</script>
