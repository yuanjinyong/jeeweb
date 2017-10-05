<template>
  <jw-form ref="form" :form-options="options" :entity="entity">
    <template slot="fieldset">
      <div class="jw-field jw-field-4">
        <el-input v-model="entity.sql" type="textarea" class="jw-textarea-nowrap"
                  :autosize="{minRows: 4, maxRows: 18}">
        </el-input>
      </div>
      <div class="jw-form-item">
      </div>
    </template>
  </jw-form>
</template>


<script>
  import Vue from 'vue'
  import {DetailMixin} from 'mixins'

  export default {
    name: 'dictSqlDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '字典组',
            url: 'api/platform/sys/dicts',
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
        this.$http.get(this.options.context.url + '/' + this.options.params.f_id).then((response) => {
          let entity = response.body.success ? response.body.data : {}
          let sql = '-- changeset 请修改为自己的姓名:' + Vue.moment(new Date().getTime()).format('YYYYMMDDHHmmss') + ' runOnChange:true\n'
          sql += '-- comment: 字典 ' + entity.f_code + ' ' + entity.f_name + '\n'
          sql += '-- ' + entity.f_code + ' ' + entity.f_name + ' BEGIN ************************\n'
          sql += 'DELETE FROM t_sys_dict_item WHERE f_dict_code = \'' + entity.f_code + '\';\n'
          sql += 'DELETE FROM t_sys_dict WHERE f_code = \'' + entity.f_code + '\';\n'
          sql += '\n'
          sql += 'INSERT INTO `t_sys_dict`(`f_code`,`f_name`,`f_db_name`,`f_table_name`,`f_tenant_column`,`f_code_column`,`f_name_column`,`f_order_column`,`f_where_clause`,`f_is_preset`,`f_remark`) VALUES '
          sql += '(' + (entity.f_code ? ('\'' + entity.f_code.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_name ? ('\'' + entity.f_name.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_db_name ? ('\'' + entity.f_db_name.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_table_name ? ('\'' + entity.f_table_name.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_tenant_column ? ('\'' + entity.f_tenant_column.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_code_column ? ('\'' + entity.f_code_column.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_name_column ? ('\'' + entity.f_name_column.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_order_column ? ('\'' + entity.f_order_column.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_where_clause ? ('\'' + entity.f_where_clause.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + entity.f_is_preset
          sql += ',' + (entity.f_remark ? ('\'' + entity.f_remark.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ');\n'

          if (entity.itemList && entity.itemList.length > 0) {
            sql += '\n'
            entity.itemList.forEach((item) => {
              sql += 'INSERT INTO `t_sys_dict_item`(`f_tenant_id`,`f_dict_code`,`f_item_order`,`f_item_code`,`f_item_text`,`f_is_preset`) VALUES '
              sql += '(' + item.f_tenant_id
              sql += ',\'' + item.f_dict_code + '\''
              sql += ',' + item.f_item_order
              sql += ',\'' + item.f_item_code + '\''
              sql += ',\'' + item.f_item_text + '\''
              sql += ',' + item.f_is_preset
              sql += ');\n'
            })
          }
          sql += '\n'
          sql += '-- rollback DELETE FROM t_sys_dict_item WHERE f_dict_code = \'' + entity.f_code + '\';\n'
          sql += '-- rollback DELETE FROM t_sys_dict WHERE f_code = \'' + entity.f_code + '\';\n'
          sql += '-- ' + entity.f_code + ' ' + entity.f_name + ' END **************************\n'

          cb({sql: sql})
        })
      }
    }
  }
</script>
