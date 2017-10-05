<template>
  <jw-form ref="form" :form-options="options" :entity="entity">
    <template slot="fieldset">
      <div class="jw-field jw-field-4">
        <el-input v-model="entity.sql" type="textarea" class="jw-textarea-full-width jw-textarea-nowrap"
                  :autosize="{minRows: 4, maxRows: 18}">
        </el-input>
      </div>
    </template>
  </jw-form>
</template>


<script>
  import Vue from 'vue'
  import {DetailMixin} from 'mixins'

  export default {
    name: 'menuSqlDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '菜单SQL',
            url: 'api/platform/sys/menus',
            detailComponent: this
          },
          loadRemoteEntity (options, cb) {
            options.context.detailComponent._loadEntity(cb)
          }
        },
        entity: {sql: null}
      }
    },
    methods: {
      _loadEntity (cb) {
        this.$http.get(this.options.context.url + '/' + this.options.params.f_id + '/sql').then((response) => {
          let data = response.body.success ? response.body.data : {}
          let menuStr = ''
          menuStr += this._appendMenus(data.menuList)

          let menuUrlStr = ''
          if (data.menuUrlList && data.menuUrlList.length > 0) {
            data.menuUrlList.forEach((menuUrl) => {
              menuUrlStr += 'INSERT INTO `t_sys_menu_url`(`f_menu_id`,`f_url_id`) VALUES (\'' + menuUrl.f_menu_id + '\',\'' + menuUrl.f_url_id + '\');\n'
            })
          }

          let roleMenuStr = ''
          if (data.roleMenuList && data.roleMenuList.length > 0) {
            data.roleMenuList.forEach((roleMenu) => {
              roleMenuStr += 'INSERT INTO `t_sys_role_menu`(`f_role_id`,`f_menu_id`) VALUES (' + roleMenu.f_role_id + ',\'' + roleMenu.f_menu_id + '\');\n'
            })
          }

          let sql = '-- changeset 请修改为自己的姓名:' + Vue.moment(new Date().getTime()).format('YYYYMMDDHHmmss') + ' runOnChange:true\n'
          sql += '-- comment: ' + this.options.params.f_id + ' ' + this.options.params.f_name + '\n'
          sql += 'DELETE FROM t_sys_role_menu WHERE f_menu_id LIKE \'' + this.options.params.f_id + '%\';\n'
          sql += 'DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE \'' + this.options.params.f_id + '%\';\n'
          sql += 'DELETE FROM t_sys_menu WHERE f_id LIKE \'' + this.options.params.f_id + '%\';\n'

          sql += '\n'
          sql += menuStr + '\n'
          sql += menuUrlStr + '\n'
          sql += roleMenuStr + '\n'
          sql += '-- rollback DELETE FROM t_sys_role_menu WHERE f_menu_id LIKE \'' + this.options.params.f_id + '%\';\n'
          sql += '-- rollback DELETE FROM t_sys_menu_url WHERE f_menu_id LIKE \'' + this.options.params.f_id + '%\';\n'
          sql += '-- rollback DELETE FROM t_sys_menu WHERE f_id LIKE \'' + this.options.params.f_id + '%\';\n'
          sql += '\n'

          // this.entity.sql = sql
          cb({sql: sql})
        })
      },
      _appendMenus (menus) {
        let menuStr = ''
        if (menus && menus.length > 0) {
          menus.forEach((menu) => {
            menuStr += 'INSERT INTO `t_sys_menu`(`f_id`,`f_parent_id`,`f_parent_path`,`f_order`,`f_name`,`f_desc`,`f_icon`,`f_type`,`f_route_path`,`f_is_web`,`f_is_android`,`f_is_ios`,`f_status`,`f_remark`) VALUES '
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
      }
    }
  }
</script>
