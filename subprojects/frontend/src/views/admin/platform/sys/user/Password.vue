<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="旧密码" prop="oldPassword">
        <el-input type="password" v-model="entity.oldPassword"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="新密码" prop="newPassword">
        <el-input type="password" v-model="entity.newPassword"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="确认新密码" prop="newPassword2">
        <el-input type="password" v-model="entity.newPassword2"></el-input>
      </el-form-item>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'changePasswordForm',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '用户密码',
            url: 'api/admin/index/user/change/password',
            detailComponent: this
          },
          loadRemoteEntity (options, cb) {
            cb({oldPassword: null, newPassword: null, newPassword2: null})
          },
          submitEntity (options, cb) {
            options.context.detailComponent._submitEntity(cb)
          }
        },
        entity: {oldPassword: null, newPassword: null, newPassword2: null},
        rules: {
          oldPassword: [
            {required: true, message: '请输入旧密码', trigger: 'blur'}
          ],
          newPassword: [
            {required: true, message: '请输入新密码', trigger: 'blur'},
            {
              validator: (rule, value, callback) => {
                if (!value) {
                  callback(new Error('请输入新密码'))
                } else if (value !== this.entity.newPassword2 && this.entity.newPassword2) {
                  callback(new Error('两次输入的新密码不一致!'))
                } else {
                  callback()
                }
              },
              trigger: 'blur'
            }
          ],
          newPassword2: [
            {required: true, message: '请输入确认新密码', trigger: 'blur'},
            {
              validator: (rule, value, callback) => {
                if (!value) {
                  callback(new Error('请再次输入新密码'))
                } else if (value !== this.entity.newPassword && this.entity.newPassword) {
                  callback(new Error('两次输入的新密码不一致!'))
                } else {
                  callback()
                }
              },
              trigger: 'blur'
            }
          ]
        }
      }
    },
    methods: {
      _submitEntity (cb) {
        this.$http.post(this.options.context.url, this.entity, {emulateJSON: true}).then((response) => {
          if (response.body.success) {
            cb(response.body.data)
          }
        })
      }
    }
  }
</script>
