<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .jw-tree-tooltip {
    float: right;
    margin-right: 20px;
    color: #fff;
  }

  .el-tree-node__content:hover .jw-tree-tooltip {
    color: #20a0ff;
  }
</style>


<template>
  <el-tree ref="menuTree"
           show-checkbox
           node-key="f_id"
           :props="props"
           :check-strictly="true"
           :default-expanded-keys="expandedMenuIds"
           :default-checked-keys="checkedMenuIds"
           :data="nodes"
           :render-content="renderContent"
           @check-change="onCheckChange">
  </el-tree>
</template>


<script>
  export default {
    name: 'jwMenuSelector',
    data () {
      return {
        nodes: [],
        expandedMenuIds: [],
        checkedMenuIds: [],
        props: {
          children: 'children',
          label: 'f_name'
        }
      }
    },
    methods: {
      getCheckedKeys () {
        return this.$refs['menuTree'].getCheckedKeys()
      },
      setData (data) {
        this.nodes = data || []
        this.expandedMenuIds = []
        this.checkedMenuIds = []
        this._updateCheckedNode(this.nodes)
        console.log('setData', this.nodes)
      },
      _updateCheckedNode (nodes) {
        nodes.forEach((node) => {
          if (node.f_menu_id) {
            this.checkedMenuIds.push(node.f_menu_id)
          }
          if (node.f_type < 2) {
            this.expandedMenuIds.push(node.f_id)
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
      renderContent (h, {node, data, store}) {
        return (
          <span>
            <span>
              <i class={data.f_icon || (data.f_type < 2 ? 'fa fa-list' : (data.f_type === 2 ? 'fa fa-file-o' : ''))} style="min-width:16px;"></i> {data.f_type < 3 ? data.f_name : data.f_desc}
            </span>
            <span style="color: #f00;font-weight: bold;">{data.mark ? '*' : ''}</span>
            <span class="jw-tree-tooltip">
              {data.f_remark ? data.f_remark : (data.f_desc ? data.f_desc : data.f_name)}
            </span>
          </span>
        )
      },
      onCheckChange (data, checked, indeterminate) {
        if (checked) {
          let node = this._getParent(this.nodes, data)
          if (node && !node.f_menu_id) {
            node.f_menu_id = node.f_id // 这里需要先赋值
            this.$refs['menuTree'].setChecked(node, checked, false) // 勾选父节点
          }

          if (!data.f_menu_id && data.children && data.children.length) {
            this._setChildrenChecked(data.children)
          }
          data.f_menu_id = data.f_id
        } else {
          if (data.children && data.children.length) {
            data.children.forEach((node) => {
              this.$refs['menuTree'].setChecked(node, checked, true) // 不勾选所有的子节点
            })
          }
          data.f_menu_id = null
        }
      },
      _setChildrenChecked (nodes) {
        nodes.forEach((node) => {
          this.$refs['menuTree'].setChecked(node, true, false)

          if (node.children && node.children.length) {
            this._setChildrenChecked(node.children)
          }
        })
      }
    }
  }
</script>
