<template>
  <jw-form ref="form" :form-options="options" :entity="entity">
    <template slot="fieldset">
      <el-row class="jw-field jw-field-2">
        <el-col :span="12" style="padding-right: 10px;">
          <div style="line-height: 40px;">可配权限（可以再分配给其他操作员的功能）</div>
          <jw-menu-selector ref="distMenuSelector" :style="{'overflow-y': 'auto', 'max-height': maxTreeHeight + 'px'}">
          </jw-menu-selector>
        </el-col>
        <el-col :span="12" style="padding-left: 10px;">
          <div style="line-height: 40px;">已授权限（已经获得授权可以使用的功能）</div>
          <jw-menu-selector ref="authMenuSelector" :style="{'overflow-y': 'auto', 'max-height': maxTreeHeight + 'px'}">
          </jw-menu-selector>
        </el-col>
      </el-row>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'jwAuthorize',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          size: 'middle',
          context: {
            name: '授权可以操作的功能',
            url: null,
            detailComponent: this
          },
          loadRemoteEntity (options, cb) {
            options.context.detailComponent._loadEntity(cb)
          },
          submitEntity (options, cb) {
            options.context.detailComponent._submitEntity(cb)
          }
        },
        entity: {distMenus: [], authMenus: []}
      }
    },
    computed: {
      maxTreeHeight () {
        return this.$store.state.layout.middle.height - this.$store.state.dialog.header.height - this.$store.state.dialog.footer.height - 20 - 20 - 40
      }
    },
    methods: {
      _loadEntity (cb) {
        this.$http.get(this.options.context.url + '/' + this.options.params.f_id + '/menus', {params: this.options.queryString}).then((response) => {
          let entity = response.body.success ? response.body.data : {distMenus: [], authMenus: []}
          cb(entity)
          this.$refs['distMenuSelector'].setData(entity.distMenus)
          this.$refs['authMenuSelector'].setData(entity.authMenus)
        })
      },
      _submitEntity (cb) {
        let data = {
          distMenuIds: this.$refs['distMenuSelector'].getCheckedKeys().join(','),
          authMenuIds: this.$refs['authMenuSelector'].getCheckedKeys().join(',')
        }
        this.$http.post(this.options.context.url + '/' + this.options.params.f_id + '/menus', data, {
          emulateJSON: true,
          params: this.options.queryString
        }).then((response) => {
          if (response.body.success) {
            cb(response.body.data)
          }
        })
      }
    }
  }
</script>
