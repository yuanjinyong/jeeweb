<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" :style="formBodyStyle">
      <el-form ref="form" :model="entity">
        <fieldset>
          <el-form-item prop="sql">
            <el-input v-model="entity.sql" type="textarea" class="jw-textarea-full-width jw-textarea-nowrap"
              :autosize="{minRows: 4, maxRows: 18}">
            </el-input>
          </el-form-item>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button type="primary" @click="onCancelForm('form')">关 闭</el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'menuSqlForm',
    props: {
      formOptions: {
        type: Object,
        default: function () {
          return {
            operation: 'view',
            title: 'SQL脚本',
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
        entity: {sql: null}
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
    methods: {
      _init () {
        this.query()
      },
      query (params) {
        var vm = this
        vm.$http.get(vm.featureOptions.url + '/' + vm.formOptions.params.f_id + '/sql').then(function (response) {
          var data = response.body.success ? response.body.data : {}
          var menuStr = '/*Data for the table `t_sys_menu` */\n'
          menuStr += vm._appendMenus(data.menuList)

          var menuUrlStr = '/*Data for the table `t_sys_menu_url` */\n'
          if (data.menuUrlList && data.menuUrlList.length > 0) {
            data.menuUrlList.forEach((menuUrl) => {
              menuUrlStr += 'insert  into `t_sys_menu_url`(`f_menu_id`,`f_url_id`) values (\'' + menuUrl.f_menu_id + '\',\'' + menuUrl.f_url_id + '\');\n'
            })
          }

          var roleMenuStr = '/*Data for the table `t_sys_role_menu` */\n'
          if (data.roleMenuList && data.roleMenuList.length > 0) {
            data.roleMenuList.forEach((roleMenu) => {
              roleMenuStr += 'insert  into `t_sys_role_menu`(`f_role_id`,`f_menu_id`) values (' + roleMenu.f_role_id + ',\'' + roleMenu.f_menu_id + '\');\n'
            })
          }

          var sql = '-- ' + vm.formOptions.params.f_id + ' ' + vm.formOptions.params.f_name + '\n'
          sql += menuStr + '\n'
          sql += menuUrlStr + '\n'
          sql += roleMenuStr + '\n'

          vm.entity.sql = sql
        })
      },
      _appendMenus (menus) {
        var menuStr = ''
        if (menus && menus.length > 0) {
          menus.forEach((menu) => {
            menuStr += 'insert  into `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) values '
            menuStr += '(\'' + menu.f_id + '\''
            menuStr += ',\'' + menu.f_parent_id + '\''
            menuStr += ',\'' + menu.f_parent_path + '\''
            menuStr += ',' + menu.f_order
            menuStr += ',\'' + menu.f_name.replace(/'/g, "\\'") + '\''
            menuStr += ',' + (menu.f_desc ? ('\'' + menu.f_desc.replace(/'/g, "\\'") + '\'') : 'NULL')
            menuStr += ',' + (menu.f_icon ? ('\'' + menu.f_icon + '\'') : 'NULL')
            menuStr += ',' + menu.f_type
            menuStr += ',' + (menu.f_route_path ? ('\'' + menu.f_route_path + '\'') : 'NULL')
            menuStr += ',' + menu.f_is_web
            menuStr += ',' + menu.f_is_android
            menuStr += ',' + menu.f_is_ios
            menuStr += ',' + menu.f_status
            menuStr += ',' + (menu.f_remark ? ('\'' + menu.f_remark.replace(/'/g, "\\'") + '\'') : 'NULL')
            menuStr += ');\n'

            menuStr += this._appendMenus(menu.children)
          })
        }

        return menuStr
      },
      onCancelForm (formName) {
        this._closeForm()
        this.$emit('cancel')
      },
      _closeForm () {
        if (this.formOptions.context.featureComponent.sqlFormOptions) {
          this.formOptions.context.featureComponent.sqlFormOptions.isShow = false
        }
      }
    }
  }
</script>
