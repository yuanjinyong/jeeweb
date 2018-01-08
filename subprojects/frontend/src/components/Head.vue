<style scoped>
</style>

<template>
  <div>
    <el-row style="background-color: #324157;">
      <el-col :span="24" style="padding: 5px 20px;">
        <div style="display: inline-block;">
          <img src="assets/img/logo64.png">
        </div>
        <div style="position: absolute; right: 0px; bottom: 0px;padding: 20px;" v-if="showButton && curUser">
          <jw-company-dropdown style="display: inline-block;"></jw-company-dropdown>
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
          <el-button type="warning" @click="onLogout"><i class="fa fa-sign-out"></i>&nbsp;退 出</el-button>

          <user-detail ref="userDetail" :detail-options="userDetailOptions"></user-detail>
          <change-password-form ref="changePassword" :detail-options="changePasswordOptions"></change-password-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: 'jwHead',
    components: {
      UserDetail: r => require.ensure([], () => r(require('views/admin/platform/sys/user/Detail')), 'platform-sys-user'),
      ChangePasswordForm: r => require.ensure([], () => r(require('views/admin/platform/sys/user/Password')), 'platform-sys-user')
    },
    props: {
      showButton: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        userDetailOptions: {
          title: '修改个人信息',
          operation: 'edit',
          subOperation: 'change',
          context: {
            featureComponent: this
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
          // alert(command)
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
          this.$store.commit('logout')
          this.$router.push({path: '/admin/login'})
        })
      },
      onShowUserInfo () {
        this.$refs['userDetail'].open({
          params: this.curUser
        })
      },
      onLogout () {
        this.$http.post('api/logout', {}, {showSuccessMessage: false}).then((response) => {
          this.result = response.body
          if (this.result.success) {
            this.$store.commit('logout')
          } else {
            console && console.error(this.result.message)
          }
        })
      }
    }
  }
</script>
