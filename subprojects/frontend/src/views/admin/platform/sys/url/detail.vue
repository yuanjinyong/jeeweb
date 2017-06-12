<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="URL" prop="f_url">
        <el-input class="jw-field-col-2" v-model="entity.f_url"></el-input>
      </el-form-item>
      <el-form-item label="Patterns" prop="f_patterns">
        <el-input class="jw-field-col-2" v-model="entity.f_patterns"></el-input>
      </el-form-item>
      <el-form-item label="Methods" prop="f_methods">
        <el-input class="jw-field-col-1" v-model="entity.f_methods"></el-input>
      </el-form-item>
      <el-form-item label="Params" prop="f_params">
        <el-input class="jw-field-col-1" v-model="entity.f_params"></el-input>
      </el-form-item>
      <el-form-item label="Headers" prop="f_headers">
        <el-input class="jw-field-col-1" v-model="entity.f_headers"></el-input>
      </el-form-item>
      <el-form-item label="Consumes" prop="f_consumes">
        <el-input class="jw-field-col-1" v-model="entity.f_consumes"></el-input>
      </el-form-item>
      <el-form-item label="Produces" prop="f_produces">
        <el-input class="jw-field-col-1" v-model="entity.f_produces"></el-input>
      </el-form-item>
      <el-form-item label="Custom" prop="f_custom">
        <el-input class="jw-field-col-1" v-model="entity.f_custom"></el-input>
      </el-form-item>
      <el-form-item label="Handler Method" prop="f_handler_method">
        <el-input class="jw-field-col-2" v-model="entity.f_handler_method"></el-input>
      </el-form-item>
      <el-form-item label="记录日志" prop="f_is_log">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_log">
          <el-radio :label="1" disabled>是</el-radio>
          <el-radio :label="2" disabled>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="扫描生成" prop="f_is_auto">
        <el-radio-group class="jw-field-col-1" v-model="entity.f_is_auto">
          <el-radio :label="1" disabled>是</el-radio>
          <el-radio :label="2" disabled>否</el-radio>
        </el-radio-group>
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
            name: 'URL',
            url: 'api/platform/sys/urls'
          }
        },
        entity: {},
        rules: {}
      }
    }
  }
</script>
