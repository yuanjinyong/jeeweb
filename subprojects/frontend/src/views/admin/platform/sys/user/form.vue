<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>

<template>
  <div class="jw-form">
    <div class="jw-form-body" style="max-height: 500px;overflow-y: auto;">
      <el-form label-width="100px" ref="form" :inline="true" :model="entity" :rules="rules">
        <fieldset :disabled="params.operation === 'view'">
          <el-form-item label="账号" prop="f_account">
            <el-input v-model="entity.f_account" :disabled="params.operation !== 'add'"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="f_name">
            <el-input v-model="entity.f_name"></el-input>
          </el-form-item>
          <el-form-item label="角色">
            <el-select v-model="entity.roleIdList" placeholder="请选择角色" multiple style="width: 496px;"
              :disabled="params.subOperation === 'change'">
              <el-option v-for="role in roleList" :key="role.f_id" :value="role.f_id" :label="role.f_name">
                <div style="float: left;">{{role.f_name}}</div>
                <div style="float: right;padding-right:30px;">{{role.f_desc}}</div>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间" prop="f_created_time">
            <el-date-picker v-model="entity.f_created_time" type="datetime" disabled></el-date-picker>
          </el-form-item>
          <el-form-item label="创建人" prop="f_creator_name">
            <el-input v-model="entity.f_creator_name" disabled></el-input>
          </el-form-item>
          <el-form-item label="最近登录时间" prop="f_last_login_time">
            <el-date-picker v-model="entity.f_last_login_time" type="datetime" disabled></el-date-picker>
          </el-form-item>
          <el-form-item label="状态" prop="f_status">
            <el-select v-model="entity.f_status" disabled>
              <el-option :value="1" :label="'正常'">正常</el-option>
              <el-option :value="2" :label="'锁定'">锁定</el-option>
              <el-option :value="3" :label="'注销'">注销</el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="锁定时间" prop="f_locked_time">
            <el-date-picker v-model="entity.f_locked_time" type="datetime" disabled></el-date-picker>
          </el-form-item>
          <el-form-item label="注销时间" prop="f_unregister_time">
            <el-date-picker v-model="entity.f_unregister_time" type="datetime" disabled></el-date-picker>
          </el-form-item>
          <el-form-item label="允许登录" prop="f_is_can_login">
            <el-radio-group v-model="entity.f_is_can_login" disabled>
              <el-radio :label="1" disabled>是</el-radio>
              <el-radio :label="2" disabled>否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="系统预置" prop="f_is_preset">
            <el-radio-group v-model="entity.f_is_preset" disabled>
              <el-radio :label="1" disabled>是</el-radio>
              <el-radio :label="2" disabled>否</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="备注" prop="f_remark">
            <el-input v-model="entity.f_remark" type="textarea" autosize style="width: 496px;"></el-input>
          </el-form-item>
        </fieldset>
      </el-form>
    </div>

    <div class="jw-form-footer" style="text-align: right;">
      <el-button @click="onCancelForm('form')">取 消</el-button>
      <el-button type="primary" @click="onSubmitForm('form')" :disabled="params.operation === 'view'">确 定</el-button>
    </div>
  </div>
</template>


<script type="text/ecmascript-6">
  export default {
    name: 'userForm',
    props: {
      params: {
        type: Object,
        default: function () {
          return {
            operation: 'view',
            entity: {}
          }
        }
      }
    },
    data () {
      return {
        url: 'api/platform/sys/users',
        roleList: [],
        entity: {roleIdList: []},
        rules: {
          f_account: [
            {required: true, message: '请输入账号', trigger: 'blur'},
            {max: 30, message: '长度在30个字符以内', trigger: 'blur'}
          ],
          f_name: [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {max: 32, message: '长度在32个字符以内', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      f_role_names () {
        var f_role_names = []
        this.entity.roleList.forEach((role) => {
          f_role_names.push(role.f_name)
        })

        return f_role_names.join(',')
      }
    },
    created () {
      this._loadRoleList()
    },
    mounted () {
      window.devMode && console.info('mounted', this.$options.name, this._uid)
      this._init()
    },
    activated () {
      window.devMode && console.info('activated', this.$options.name, this._uid)
    },
    methods: {
      _init () {
        this.query()
      },
      _loadRoleList () {
        this.$http.get('api/platform/sys/roles?orderBy=f_is_preset,f_name').then((response) => {
          this.roleList = response.body.success ? response.body.data.items : []
        })
      },
      query (params) {
        var vm = this
        if (vm.params.operation === 'add') {
          vm.entity = {
            f_department_id: 0,
            f_is_can_login: 1,
            f_is_preset: 2,
            f_status: 1,
            roleIdList: []
          }
        } else {
          vm.$http.get(vm.url + '/' + vm.params.entity.f_id).then((response) => {
            vm.entity = response.body.success ? response.body.data : {roleIdList: []}
          })
        }
      },
      onCancelForm (formName) {
        this.$emit('cancel')
      },
      onSubmitForm (formName) {
        var vm = this
        vm.$refs[formName].validate(function (valid) {
          if (!valid) {
            return false
          }

          if (vm.params.operation === 'add') {
            vm.$http.post(vm.url, vm.entity).then(function (response) {
              if (response.body.success) {
                this.$emit('submit')
              }
            })
          } else {
            vm.$http.put(vm.url + '/' + vm.params.entity.f_id, vm.entity).then(function (response) {
              if (response.body.success) {
                this.$emit('submit')
              }
            })
          }

          return true
        })
      }
    }
  }
</script>
