<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="名称" prop="f_name">
        <el-input class="jw-field-col-1" v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item label="系统预置" prop="f_is_preset">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_preset">
          <el-radio :label="1" disabled>是</el-radio>
          <el-radio :label="2" disabled>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="描述" prop="f_desc">
        <el-input class="jw-field-col-2" v-model="entity.f_desc" type="textarea" autosize></el-input>
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
    name: 'urlDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '角色',
            url: 'api/platform/sys/roles'
          },
          createEntity (options, cb) {
            cb({f_is_preset: 2})
          }
        },
        entity: {},
        rules: {
          f_name: [
            {required: true, message: '请输入角色名称', trigger: 'blur'},
            {max: 50, message: '长度在50个字符以内', trigger: 'blur'}
          ]
        }
      }
    }
  }
</script>
