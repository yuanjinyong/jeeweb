<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <el-row>
    <el-col :xs="24" :sm="{offset:8, span:8}" :md="{offset:9, span:6}" :lg="{offset:10, span:4}">
      <el-form ref="loginForm" label-width="60px" :model="entity" :rules="rules">
        <el-form-item label="账号" prop="f_account">
          <el-input type="text" v-model="entity.f_account"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="f_password">
          <el-input type="password" v-model="entity.f_password"></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" @click="onSubmitForm('loginForm')">登 录</el-button>
          <el-button type="warning" @click="onResetForm('loginForm')">重 置</el-button>
        </el-form-item>
      </el-form>
      <div style="margin: 20px 0px;min-height:40px;text-align: center;">
        <el-alert type="error" :closable="false" :title="errorMessage" v-if="errorMessage"></el-alert>
      </div>
    </el-col>
  </el-row>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'loginForm',
    data () {
      return {
        errorMessage: null,
        entity: {
          f_account: process.env.NODE_ENV === 'development' ? 'SuperAdmin' : null,
          f_password: process.env.NODE_ENV === 'development' ? '12345678' : null
        },
        rules: {
          f_account: [
            {required: true, message: '请输入账号', trigger: 'blur'},
            {min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur'}
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
      }
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      onSubmitForm (formName) {
        var vm = this
        vm.$refs[formName].validate(function (valid) {
          vm.errorMessage = null
          if (valid) {
            vm.$http.post('api/platform/security/token', {}, {
              showSuccessMessage: false,
              headers: {
                Authorization: 'Basic ' + btoa(vm.entity.f_account + ':' + vm.entity.f_password)
              }
            }).then(function (response) {
              if (response.body.success) {
                vm.$store.commit('setUser', {user: response.body.data})
                vm.$router.push(vm.originalRoute)
              } else {
                vm.errorMessage = response.body.message
              }
            }).catch(function (response) {
              // console.error('response', response) // 打印一下错误
              vm.errorMessage = '账号密码错误，请重新输入！'
            })

            return true
          }

          return false
        })
      },
      onResetForm (formName) {
        this.$refs[formName].resetFields()
      }
    }
  }
</script>
