<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input type="password" v-model="entity.oldPassword"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input type="password" v-model="entity.newPassword"></el-input>
      </el-form-item>
      <el-form-item label="确认新密码" prop="newPassword2">
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
            url: 'api/admin/index/change/password'
          },
          loadRemoteEntity (options, cb) {
            cb({oldPassword: null, newPassword: null, newPassword2: null})
          },
          submitEntity (options) {
            options.context.detailComponent._submitEntity()
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
      _submitEntity () {
        this.$http.post(this.options.context.url, this.entity, {emulateJSON: true}).then((response) => {
          if (response.body.success) {
            this.$refs['form'].submitted(response.body)
          }
        })
      }
    }
  }
</script>
