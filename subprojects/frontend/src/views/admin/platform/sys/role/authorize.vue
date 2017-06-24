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
  <jw-form ref="form" :form-options="options" :entity="entity">
    <template slot="fieldset">
      <div class="jw-form-item">
        <el-tree ref="menuTree"
                 :style="{'overflow-y': 'auto', 'max-height': maxTreeHeight + 'px'}"
                 show-checkbox
                 node-key="f_id"
                 :props="treeOptions.props"
                 :check-strictly="true"
                 :default-expanded-keys="treeOptions.expandedMenuIds"
                 :default-checked-keys="treeOptions.checkedMenuIds"
                 :data="treeOptions.nodes"
                 :render-content="renderContent"
                 @check-change="onCheckChange">
        </el-tree>
      </div>
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
          nodes: [],
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
          loadRemoteEntity (options, cb) {
            options.context.detailComponent._loadEntity(cb)
          },
          submitEntity (options, cb) {
            options.context.detailComponent._submitEntity(cb)
          }
        },
        entity: {}
      }
    },
    computed: {
      maxTreeHeight () {
        return this.$store.state.layout.window.height - 75 - 59 - 76 - 35 - 40
      }
    },
    methods: {
      _submitEntity (cb) {
        let selectedMenuIds = this.$refs['menuTree'].getCheckedKeys()
        this.$http.post(this.options.context.url + '/' + this.options.params.f_id + '/menus', {f_menu_ids: selectedMenuIds.join(',')}, {emulateJSON: true}).then((response) => {
          if (response.body.success) {
            cb(response.body.data)
          }
        })
      },
      _loadEntity (cb) {
        this.treeOptions.nodes = []
        this.treeOptions.expandedMenuIds = []
        this.treeOptions.checkedMenuIds = []
        this.$http.get(this.options.context.url + '/' + this.options.params.f_id + '/menus').then((response) => {
          this.treeOptions.nodes = response.body.success ? response.body.data : []
          this._updateCheckedNode(this.treeOptions.nodes)
          // this.entity = {f_menu_ids: this.treeOptions.checkedMenuIds.join(',')}
          cb({f_menu_ids: this.treeOptions.checkedMenuIds.join(',')})
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
      renderContent (h, {node, data, store}) {
        return (
          <span>
            <span>
              <i class={data.f_icon || (data.f_type < 2 ? 'fa fa-list' : (data.f_type === 2 ? 'fa fa-file-o' : ''))} style="min-width:16px;"></i> {data.f_type < 3 ? data.f_name : data.f_desc}
            </span>
            <span class="jw-tree-tooltip">
              {data.f_remark ? data.f_remark : (data.f_desc ? data.f_desc : data.f_name)}
            </span>
          </span>
        )
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
