<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="账号" prop="f_account">
        <el-input class="jw-field-col-1" v-model="entity.f_account"
                  :disabled="options.operation !== 'add'"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="f_name">
        <el-input class="jw-field-col-1" v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item label="角色">
        <el-select class="jw-field-col-2" v-model="entity.roleIdList" placeholder="请选择角色" multiple
                   :disabled="options.subOperation === 'change'">
          <el-option v-for="role in roleList" :key="role.f_id" :value="role.f_id" :label="role.f_name">
            <div style="float: left;">{{role.f_name}}</div>
            <div style="float: right;padding-right:30px;">{{role.f_desc}}</div>
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="f_created_time">
        <el-date-picker class="jw-field-col-1" v-model="entity.f_created_time" type="datetime" disabled>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建人" prop="f_creator_name">
        <el-input class="jw-field-col-1" v-model="entity.f_creator_name" disabled></el-input>
      </el-form-item>
      <el-form-item label="最近登录时间" prop="f_last_login_time">
        <el-date-picker class="jw-field-col-1" v-model="entity.f_last_login_time" type="datetime" disabled>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="f_status">
        <el-select class="jw-field-col-1" v-model="entity.f_status" disabled>
          <el-option :value="1" :label="'正常'">正常</el-option>
          <el-option :value="2" :label="'锁定'">锁定</el-option>
          <el-option :value="3" :label="'注销'">注销</el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="锁定时间" prop="f_locked_time">
        <el-date-picker class="jw-field-col-1" v-model="entity.f_locked_time" type="datetime" disabled>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="注销时间" prop="f_unregister_time">
        <el-date-picker class="jw-field-col-1" v-model="entity.f_unregister_time" type="datetime" disabled>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="允许登录" prop="f_is_can_login">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_can_login" disabled>
          <el-radio :label="1" disabled>是</el-radio>
          <el-radio :label="2" disabled>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="系统预置" prop="f_is_preset">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_preset" disabled>
          <el-radio :label="1" disabled>是</el-radio>
          <el-radio :label="2" disabled>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="f_remark">
        <el-input class="jw-field-col-2" v-model="entity.f_remark" type="textarea" autosize></el-input>
      </el-form-item>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'userDetail',
    mixins: [DetailMixin],
    data () {
      return {
        roleList: [],
        options: {
          context: {
            name: '用户',
            url: 'api/platform/sys/users'
          },
          createEntity (options) {
            return {
              f_department_id: 0,
              f_is_can_login: 1,
              f_is_preset: 2,
              f_status: 1,
              roleIdList: []
            }
          }
        },
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
    mounted () {
      this._loadRoleList()
    },
    methods: {
      _loadRoleList () {
        this.$http.get('api/platform/sys/roles?orderBy=f_is_preset,f_name').then((response) => {
          this.roleList = response.body.success ? response.body.data.items : []
        })
      }
    }
  }
</script>
