<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="编码" prop="f_code">
        <el-input class="jw-field-col-1" v-model="entity.f_code"></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="f_name">
        <el-input class="jw-field-col-1" v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="f_desc">
        <el-input class="jw-field-col-2" v-model="entity.f_desc" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="f_order">
        <el-input-number class="jw-field-col-1" v-model="entity.f_order" :step="1"></el-input-number>
      </el-form-item>
      <el-form-item label="显示" prop="f_is_editable">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_editable">
          <el-radio :label="1">是</el-radio>
          <el-radio :label="2">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="出厂值" prop="f_init_value">
        <el-input class="jw-field-col-1" v-model="entity.f_init_value"></el-input>
      </el-form-item>
      <el-form-item label="当前值" prop="f_value">
        <el-input class="jw-field-col-1" v-model="entity.f_value"></el-input>
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
    name: 'settingDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '系统设置项',
            url: 'api/platform/sys/settings',
            detailComponent: this
          },
          createEntity (options, cb) {
            let entity = {
              f_code: null,
              f_name: null,
              f_desc: null,
              f_order: 999999999,
              f_is_editable: 1,
              f_field_type: 'String',
              f_field_cfg: null,
              f_init_value: null,
              f_value: null,
              f_remark: null
            }

            cb(entity)
          }
        },
        entity: {},
        rules: {
          f_code: [
            {required: true, message: '请输入编码', trigger: 'blur'},
            {max: 50, message: '长度在512个字符以内', trigger: 'blur'}
          ],
          f_name: [
            {required: true, message: '请输入名称', trigger: 'blur'},
            {max: 50, message: '长度在512个字符以内', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {}
  }
</script>
