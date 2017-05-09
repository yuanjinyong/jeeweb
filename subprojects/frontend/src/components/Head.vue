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
                <i class="fa fa-user" style="min-width:14px;"></i> 我的信息
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

    <el-dialog v-model="userFormDialog.shown" :title="userFormDialog.title" :close-on-click-modal="false"
      :size="'small'" :top="'30px'" :custom-class="'jw-dialog'">
      <user-form :params="userFormDialog.params"
        @cancel="userFormDialog.shown = false"
        @submit="userFormDialog.shown = false"
        v-if="userFormDialog.shown">
      </user-form>
    </el-dialog>

    <el-dialog v-model="changePasswordDialog.shown" :title="changePasswordDialog.title" :close-on-click-modal="false"
      :size="'tiny'" :top="'30px'" :custom-class="'jw-dialog'">
      <change-password-form :params="changePasswordDialog.params"
        @cancel="changePasswordDialog.shown = false"
        @submit="onPasswordChanged"
        v-if="changePasswordDialog.shown">
      </change-password-form>
    </el-dialog>
  </div>
</template>

<script type="text/ecmascript-6">
  import UserForm from 'views/admin/platform/sys/user/form'
  import ChangePasswordForm from 'views/admin/platform/sys/user/password'

  export default {
    name: 'jw-head',
    components: {
      UserForm,
      ChangePasswordForm
    },
    data () {
      return {
        userFormDialog: {
          shown: false,
          title: '修改个人信息',
          params: {
            operation: 'edit',
            subOperation: 'change',
            entity: {}
          }
        },
        changePasswordDialog: {
          shown: false,
          title: '修改密码',
          params: {
            entity: {}
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
        this.changePasswordDialog.shown = true
        this.changePasswordDialog.params.entity = this.curUser
      },
      onPasswordChanged () {
        this.changePasswordDialog.shown = false
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
        this.userFormDialog.shown = true
        this.userFormDialog.params.entity = this.curUser
      },
      onLogout () {
        var vm = this
        vm.$http.post('api/logout', {}, {showSuccessMessage: false}).then(function (response) {
          vm.result = response.body
          if (vm.result.success) {
            vm.$store.commit('logout')
            vm.$router.push({path: '/'})
          } else {
            console && console.error(vm.result.message)
          }
        })
      }
    }
  }
</script>
