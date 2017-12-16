<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .el-collapse-item__header {
    background-color: #fbfdff;
  }

  .el-collapse-item__wrap {
    background-color: #fff;
  }
</style>


<template>
  <jw-layout v-if="layout.window.width < 768">
    <jw-head slot="top"></jw-head>

    <div slot="middle" class="jw-dialog jw-dialog-mini" style="background-color: #fff;width:100%;height:100%;">
      <div class="jw-form jw-form-mini" style="width: 100%;">
        <div class="jw-form-body">
          <el-form ref="form" :model="company" :rules="rules" :inline="false">
            <el-collapse v-model="activeCollapses">
              <el-collapse-item name="creator" title="用户信息">
                <fieldset>
                  <el-form-item class="jw-field jw-field-1" label="姓名" :prop="'creator.f_name'">
                    <el-input v-model="company.creator.f_name" type="text"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="手机" :prop="'creator.f_telephone'">
                    <el-input v-model="company.creator.f_telephone" type="text"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="登录账号" :prop="'creator.f_account'">
                    <el-input v-model="company.creator.f_account" type="text"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="登录密码" :prop="'creator.f_password'">
                    <el-input v-model="company.creator.f_password" type="password"></el-input>
                  </el-form-item>
                </fieldset>
              </el-collapse-item>

              <el-collapse-item name="company" title="企业信息" v-if="type === 'company'">
                <fieldset>
                  <el-form-item class="jw-field jw-field-1" label="企业名称" prop="f_name">
                    <el-input v-model="company.f_name"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="企业类型" prop="f_type">
                    <el-select v-model="company.f_type" disabled>
                      <el-option :value="101" :label="'施工方'"></el-option>
                      <el-option :value="102" :label="'供应商'"></el-option>
                      <el-option :value="103" :label="'业主'"></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-2" label="地址" prop="f_address">
                    <el-input v-model="company.f_address"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-2" label="描述" prop="f_desc">
                    <el-input v-model="company.f_desc" type="textarea" autosize></el-input>
                  </el-form-item>
                </fieldset>
              </el-collapse-item>
            </el-collapse>
          </el-form>
        </div>

        <div class="jw-form-footer" style="text-align: center;background-color: #fff;">
          <el-button-group style="width: 100%;">
            <el-button type="warning" style="width: 50%;" @click="onReset">重 置</el-button>
            <el-button type="primary" style="width: 50%;" @click="onSubmit">提 交</el-button>
          </el-button-group>
        </div>
      </div>
    </div>
  </jw-layout>

  <jw-layout v-else>
    <jw-head slot="top"></jw-head>

    <div slot="middle" class="jw-dialog jw-dialog-small" style="background-color: #fff;width:100%;height:100%;">
      <div class="jw-form jw-form-small"
           style="position: absolute;top: 0;left: 0;bottom: 0;right: 0;margin: auto;width:776px;"
           :style="{'height': (type === 'company' ? 420 : 226) +'px'}">
        <div class="jw-form-body">
          <el-form ref="form" :model="company" :rules="rules" :inline="true">
            <el-collapse v-model="activeCollapses">
              <el-collapse-item name="creator" title="用户信息">
                <fieldset>
                  <el-form-item class="jw-field jw-field-1" label="姓名" :prop="'creator.f_name'">
                    <el-input v-model="company.creator.f_name" type="text"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="手机" :prop="'creator.f_telephone'">
                    <el-input v-model="company.creator.f_telephone" type="text"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="登录账号" :prop="'creator.f_account'">
                    <el-input v-model="company.creator.f_account" type="text"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="登录密码" :prop="'creator.f_password'">
                    <el-input v-model="company.creator.f_password" type="password"></el-input>
                  </el-form-item>
                </fieldset>
              </el-collapse-item>

              <el-collapse-item name="company" title="企业信息" v-if="type === 'company'">
                <fieldset>
                  <el-form-item class="jw-field jw-field-1" label="企业名称" prop="f_name">
                    <el-input v-model="company.f_name"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-1" label="企业类型" prop="f_type">
                    <el-select v-model="company.f_type" disabled>
                      <el-option :value="101" :label="'施工方'"></el-option>
                      <el-option :value="102" :label="'供应商'"></el-option>
                      <el-option :value="103" :label="'业主'"></el-option>
                    </el-select>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-2" label="地址" prop="f_address">
                    <el-input v-model="company.f_address"></el-input>
                  </el-form-item>
                  <el-form-item class="jw-field jw-field-2" label="描述" prop="f_desc">
                    <el-input v-model="company.f_desc" type="textarea" autosize></el-input>
                  </el-form-item>
                </fieldset>
              </el-collapse-item>
            </el-collapse>
          </el-form>
        </div>

        <div class="jw-form-footer" style="text-align: center;background-color: #fff;">
          <el-button type="warning" @click="onReset">重 置</el-button>
          <el-button type="primary" @click="onSubmit">提 交</el-button>
        </div>
      </div>
    </div>

    <jw-foot slot="bottom"></jw-foot>
  </jw-layout>
</template>


<script>
  import Vue from 'vue'

  export default {
    name: 'registerView',
    data () {
      return {
        activeCollapses: ['company', 'creator'],
        company: {
          f_name: null,
          f_type: 101, // 类型：101、施工方；102、供应商；103、业主
          f_address: null,
          f_desc: null,
          creator: {
            f_account: null,
            f_password: null,
            f_name: null,
            f_telephone: null
          }
        },
        rules: {
          f_name: [
            {required: true, message: '请输入企业名称', trigger: 'blur'},
            {max: 50, message: '长度在128个字符以内', trigger: 'blur'}
          ],
          'creator.f_name': [
            {required: true, message: '请输入姓名', trigger: 'blur'},
            {max: 50, message: '长度在32个字符以内', trigger: 'blur'}
          ],
          'creator.f_telephone': [
            {required: true, message: '请输入手机号', trigger: 'blur'}
          ],
          'creator.f_account': [
            {required: true, message: '请输入登录账号', trigger: 'blur'}
          ],
          'creator.f_password': [
            {required: true, message: '请输入登录密码', trigger: 'blur'}
          ]
        }
      }
    },
    computed: {
      layout () {
        return this.$store.state.layout
      },
      type () {
        return this.$route.params.type
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

          let loading = Vue.prototype.$loading({text: '正在创建企业……'})
          this.$http.post('api/zkpms/register/' + this.type, this.company).then((response) => {
            loading.close()
            if (response.body.success) {
              this.$router.push({path: '/', params: this.company.creator})
            }
          }).catch(e => loading.close())

          return true
        })
      }
    }
  }
</script>
