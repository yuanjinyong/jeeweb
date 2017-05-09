<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" style="max-height: 500px;overflow-y: auto;">
      <el-form label-width="100px" ref="form" :model="entity" :rules="rules">
        <fieldset>
          <el-form-item label="旧密码" prop="oldPassword">
            <el-input type="password" v-model="entity.oldPassword"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPassword">
            <el-input type="password" v-model="entity.newPassword"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="newPassword2">
            <el-input type="password" v-model="entity.newPassword2"></el-input>
          </el-form-item>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')">确 定</el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'changePasswordForm',
    props: {
      params: {
        type: Object,
        default: function () {
          return {
            operation: 'edit',
            entity: {}
          }
        }
      }
    },
    data () {
      return {
        url: 'api/admin/index/change/password',
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
      onCancelForm (formName) {
        this.$emit('cancel')
      },
      onSubmitForm (formName) {
        var vm = this
        vm.$refs[formName].validate(function (valid) {
          if (!valid) {
            return false
          }

          vm.$http.post(vm.url, vm.entity, {emulateJSON: true}).then(function (response) {
            if (response.body.success) {
              this.$emit('submit')
            }
          })

          return true
        })
      }
    }
  }
</script>
