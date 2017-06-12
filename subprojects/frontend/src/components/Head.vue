<style scoped>
</style>

<template>
  <div>
    <el-row style="background-color: #39f;">
      <el-col :span="24" style="padding: 5px 20px;">
        <div style="display: inline-block;">
          <img src="assets/img/logo.png">
        </div>
        <div style="position: absolute; right: 0px; bottom: 0px;padding: 20px;" v-if="curUser">
          <el-dropdown @command="onCommand">
            <el-button>
              {{curUser.f_name}}<i class="el-icon-caret-bottom el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="showUserInfo">
                <i class="fa fa-user" style="min-width:14px;"></i> 个人信息
              </el-dropdown-item>
              <el-dropdown-item command="changePassword">
                <i class="fa fa-cog" style="min-width:14px;"></i> 修改密码
              </el-dropdown-item>
              <el-dropdown-item command="logout" divided>
                <i class="fa fa-sign-out" style="min-width:14px;"></i> 退出系统
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-button type="warning" @click="onLogout"><i class="fa fa-sign-out"></i>&nbsp;退出</el-button>
        </div>
      </el-col>
    </el-row>

    <user-detail ref="userDetail" :detail-options="userDetailOptions" v-if="showUserDetail"></user-detail>
    <change-password-form ref="changePassword" :detail-options="changePasswordOptions"></change-password-form>
  </div>
</template>

<script type="text/ecmascript-6">
  import UserDetail from 'views/admin/platform/sys/user/detail'
  import ChangePasswordForm from 'views/admin/platform/sys/user/password'

  export default {
    name: 'jw-head',
    components: {
      UserDetail,
      ChangePasswordForm
    },
    data () {
      return {
        showUserDetail: false,
        userDetailOptions: {
          title: '修改个人信息',
          operation: 'edit',
          subOperation: 'change',
          context: {
            featureComponent: this
          },
          closed () {
            this.options.context.featureComponent.showUserDetail = false
          }
        },
        changePasswordOptions: {
          size: 'mini',
          title: '修改密码',
          operation: 'edit',
          context: {
            featureComponent: this
          },
          submitted (result) {
            this.options.context.featureComponent.onPasswordChanged(result)
          }
        }
      }
    },
    computed: {
      curUser () {
        return this.$store.state.user
      }
    },
    methods: {
      onCommand (command) {
        if (command === 'logout') {
          this.onLogout()
        } else if (command === 'changePassword') {
          this.onChangePassword()
        } else if (command === 'showUserInfo') {
          this.onShowUserInfo()
        } else {
          alert(command)
        }
      },
      onChangePassword () {
        this.$refs['changePassword'].open({
          params: this.curUser
        })
      },
      onPasswordChanged () {
        this.$confirm('密码修改成功，现在重新登录吗?', '修改密码', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'success'
        }).then(() => {
          this.$cookie.delete('X-REST-TOKEN')  // 服务器端token过期后会返回401，再重新登录前，清空本地cookie中的token
          this.$store.commit('backupRoute', this.$route)
          this.$store.commit('setUser', {user: null})
          this.$router.push({path: '/admin/login'})
        })
      },
      onShowUserInfo () {
        this.showUserDetail = true
        this.$nextTick(() => {
          this.$refs['userDetail'].open({
            params: this.curUser
          })
        })
      },
      onLogout () {
        this.$http.post('api/logout', {}, {showSuccessMessage: false}).then((response) => {
          this.result = response.body
          if (this.result.success) {
            this.$store.commit('logout')
            this.$router.push({path: '/'})
          } else {
            console && console.error(this.result.message)
          }
        })
      }
    }
  }
</script>
