<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>


<template>
  <jw-layout>
    <jw-head slot="top"></jw-head>

    <div slot="middle" class="jw-dialog jw-dialog-mini" style="background-color: #fff;width:100%;height:100%;">
      <div class="jw-form jw-form-mini"
           style="position: absolute;top: 0;left: 0;bottom: 0;right: 0;margin: auto;height:310px;">
        <div class="jw-form-body">
          <el-form ref="form" :model="entity" :rules="rules">
            <fieldset>
              <el-form-item class="jw-field jw-field-1" prop="f_account">
                <el-input v-model="entity.f_account" type="text" placeholder="请输入账号">
                  <template slot="prepend"><i class="fa fa-user-o" style="min-width: 14px;"></i></template>
                </el-input>
              </el-form-item>
              <el-form-item class="jw-field jw-field-1" prop="f_password">
                <el-input v-model="entity.f_password" type="password" placeholder="请输入密码" @keyup.enter.native="onSubmit">
                  <template slot="prepend"><i class="fa fa-key" style="min-width: 14px;"></i></template>
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button-group style="width: 100%;">
                  <el-button type="text" style="width: 33%;" @click="$router.push({name:'register', params: {type: 'company'}})">企业注册</el-button>
                  <el-button type="text" style="width: 33%;" @click="$router.push({name:'register', params: {type: 'user'}})">个人注册</el-button>
                  <el-button type="text" style="width: 33%;">忘记密码</el-button>
                </el-button-group>
              </el-form-item>
              <el-form-item style="text-align: center;" v-if="layout.window.width < 768">
                <el-button-group style="width: 100%;">
                  <el-button type="warning" style="width: 50%;" @click="onReset">重 置</el-button>
                  <el-button type="primary" style="width: 50%;" @click="onSubmit">登 录</el-button>
                </el-button-group>
              </el-form-item>
              <el-form-item style="text-align: center;" v-else>
                <el-button type="warning" @click="onReset">重 置</el-button>
                <el-button type="primary" @click="onSubmit">登 录</el-button>
              </el-form-item>

              <div class="jw-field jw-field-1">
                <el-alert type="error" :closable="false" :title="errorMessage" v-if="errorMessage"></el-alert>
              </div>
            </fieldset>
          </el-form>
        </div>
      </div>
    </div>

    <jw-foot slot="bottom" v-if="layout.window.width >= 768"></jw-foot>
  </jw-layout>
</template>


<script>
  export default {
    name: 'adminLoginView',
    data () {
      return {
        errorMessage: null,
        entity: {
          f_account: process.env.NODE_ENV === 'development' ? 'SuperAdmin' : null,
          f_password: process.env.NODE_ENV === 'development' ? '12345678' : null
        },
        rules: {
          f_account: [
            {required: true, message: '请输入账号', trigger: 'blur'}
          ],
          f_password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      originalRoute () {
        return this.$store.state.originalRoute ? this.$store.state.originalRoute : {path: '/'}
      },
      layout () {
        return this.$store.state.layout
      }
    },
    methods: {
      onReset () {
        this.$refs['form'].resetFields()
      },
      onSubmit () {
        this.$refs['form'].validate((valid) => {
          if (!valid) {
            return false
          }

          this.errorMessage = null
          this.$http.post('api/platform/security/token', {}, {
            showSuccessMessage: false,
            headers: {
              Authorization: 'Basic ' + btoa(this.entity.f_account + ':' + this.entity.f_password)
            }
          }).then((response) => {
            if (response.body.success) {
              this.$router.push(this.originalRoute)
            } else {
              this.errorMessage = response.body.message
            }
          }).catch((e) => {
            console && console.error(e) // 打印一下错误
            this.errorMessage = '账号密码错误，请重新输入！'
          })

          return true
        })
      }
    }
  }
</script>
