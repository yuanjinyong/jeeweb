<template>
  <jw-form ref="form" :form-options="options" :entity="entity">
    <template slot="fieldset">
      <div class="jw-field jw-field-4">
        <el-input v-model="entity.sql" class="jw-textarea jw-textarea-nowrap" type="textarea"
                  :autosize="{minRows: 4, maxRows: 18}" @focus="$clipboard.copy('.jw-textarea');"
                  :data-clipboard-text="entity.sql">
        </el-input>
      </div>
    </template>
  </jw-form>
</template>


<script>
  import Vue from 'vue'
  import {DetailMixin} from 'mixins'

  export default {
    name: 'settingSqlDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '系统设置项',
            url: 'api/platform/sys/settings',
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
          let sql = '-- changeset 请修改为自己的姓名:' + Vue.moment(new Date().getTime()).format('YYYYMMDDHHmmss') + ' runOnChange:true'
          sql += '\n-- comment: 系统设置 ' + entity.f_code + ' ' + entity.f_name
          sql += '\n-- ' + entity.f_code + ' ' + entity.f_name + ' BEGIN ************************'
          sql += '\nDELETE FROM `t_sys_setting` WHERE f_code = \'' + entity.f_code + '\';'
          sql += '\n'
          sql += '\nINSERT INTO `t_sys_setting`(`f_code`,`f_name`,`f_desc`,`f_order`,`f_is_editable`,`f_field_type`,`f_field_cfg`,`f_init_value`,`f_value`,`f_remark`) VALUES '
          sql += '(' + (entity.f_code ? ('\'' + entity.f_code.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_name ? ('\'' + entity.f_name.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_desc ? ('\'' + entity.f_desc.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + entity.f_order
          sql += ',' + entity.f_is_editable
          sql += ',' + (entity.f_field_type ? ('\'' + entity.f_field_type.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_field_cfg ? ('\'' + entity.f_field_cfg.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_init_value ? ('\'' + entity.f_init_value.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_value ? ('\'' + entity.f_value.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ',' + (entity.f_remark ? ('\'' + entity.f_remark.replace(/'/g, "\\'") + '\'') : 'NULL')
          sql += ');'

          sql += '\n-- rollback DELETE FROM `t_sys_setting` WHERE f_code = \'' + entity.f_code + '\';'
          sql += '\n-- ' + entity.f_code + ' ' + entity.f_name + ' END **************************'

          cb({sql: sql})
        })
      }
    }
  }
</script>
