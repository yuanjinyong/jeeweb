<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="规则编码" prop="f_code">
        <el-input v-model="entity.f_code"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="规则名称" prop="f_name">
        <el-input v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="父级菜单" prop="f_menu_parent_id">
        <el-select v-model="entity.f_menu_parent_id" @change="onParentMenuChange">
          <el-option v-for="menu in menuList"
                     :key="menu.f_id"
                     :value="menu.f_id"
                     :label="menu.f_name">
            <div :style="{'padding-left':(menu.f_parent_path.split('/').length-3)*14+'px'}">{{menu.f_name}}</div>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="菜单路径" prop="f_menu_parent_path">
        <el-input v-model="entity.f_menu_parent_path" disabled></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="菜单编码" prop="f_menu_id">
        <el-input v-model="entity.f_menu_id"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="菜单名称" prop="f_menu_name">
        <el-input v-model="entity.f_menu_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="菜单描述" prop="f_menu_remark">
        <el-input v-model="entity.f_menu_remark"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="排序" prop="f_menu_order">
        <el-input-number v-model="entity.f_menu_order" :step="5"></el-input-number>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="URL" prop="f_request_url">
        <el-input v-model="entity.f_request_url"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="模块包名" prop="f_package_name">
        <el-input v-model="entity.f_package_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-4" label="数据库表">
        <generation-rule-table-view style="height: 500px;" :operation="options.operation" :generate-rule="entity">
        </generation-rule-table-view>
      </el-form-item>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'generationRuleDetail',
    mixins: [DetailMixin],
    components: {
      GenerationRuleTableView: r => require.ensure([], () => r(require('../table/View')), 'platform-tools-code-generation')
    },
    data () {
      return {
        menuList: [],
        options: {
          context: {
            name: '代码生成规则',
            url: 'api/platform/tools/code/generate/rules',
            detailComponent: this
          },
          createEntity (options, cb) {
            cb({
              f_menu_parent_id: 'XTGL',
              f_menu_parent_path: '/ROOT/XTGL/',
              f_request_url: '/api/',
              f_package_name: 'com.jeeweb.',
              tableList: []
            })
          },
          loadRemoteEntity (options, cb) {
            this.$http.get(options.context.url + '/' + options.params.f_id).then((response) => {
              cb(response.body.success ? response.body.data : {tableList: []})
            })
          }
        },
        entity: {tableList: []},
        rules: {}
      }
    },
    mounted () {
      this._initMenuList()
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
      _initMenuList () {
        this.menuList = []
        this._addMenu(this.$store.state.menuList)
      },
      _addMenu (menuTree) {
        menuTree.forEach((menu) => {
          if (menu.f_type < 2) {
            this.menuList.push(menu)
          }
          if (menu.children && menu.children.length > 0) {
            this._addMenu(menu.children)
          }
        })
      }
    }
  }
</script>
