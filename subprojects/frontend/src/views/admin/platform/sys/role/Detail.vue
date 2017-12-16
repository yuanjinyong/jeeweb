<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="名称" prop="f_name">
        <el-input v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="系统预置" prop="f_is_preset">
        <el-radio-group v-model="entity.f_is_preset">
          <el-radio :label="101" disabled>是</el-radio>
          <el-radio :label="102" disabled>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="描述" prop="f_desc">
        <el-input v-model="entity.f_desc" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="备注" prop="f_remark">
        <el-input v-model="entity.f_remark" type="textarea" autosize></el-input>
      </el-form-item>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'roleDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '角色',
            url: 'api/platform/sys/roles'
          },
          createEntity (options, cb) {
            cb({f_is_preset: 102})
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
