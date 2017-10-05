<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="编码" prop="f_key">
        <el-input class="jw-field-col-1" v-model="entity.f_key" disabled></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="f_name">
        <el-input class="jw-field-col-1" v-model="entity.f_name" disabled></el-input>
      </el-form-item>
      <el-form-item label="版本" prop="f_version">
        <el-input class="jw-field-col-1" v-model="entity.f_version" disabled></el-input>
      </el-form-item>
      <el-form-item label="租户ID" prop="f_tenant_id">
        <el-input class="jw-field-col-1" v-model="entity.f_tenant_id" disabled></el-input>
      </el-form-item>
      <el-form-item label="部署批次" prop="f_deployment_id">
        <el-input class="jw-field-col-1" v-model="entity.f_deployment_id" disabled></el-input>
      </el-form-item>
      <el-form-item label="模型名" prop="f_resource_name">
        <el-input class="jw-field-col-1" v-model="entity.f_resource_name" disabled></el-input>
      </el-form-item>

      <el-form-item label="描述" prop="f_description">
        <el-input class="jw-field-col-3" v-model="entity.f_description" type="textarea" autosize disabled></el-input>
      </el-form-item>

      <div class="jw-form-item" style="overflow: auto;width:100%;max-height:287px;text-align: center;">
        <img :src="diagramUrl" />
      </div>
    </template>
  </jw-form>
</template>


<script>
  import Vue from 'vue'
  import {DetailMixin} from 'mixins'

  export default {
    name: 'processDefinitionDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '流程模型',
            url: 'api/activiti/process/definitions',
            detailComponent: this
          },
          createEntity (options, cb) {
            cb({})
          }
        },
        entity: {},
        rules: {}
      }
    },
    computed: {
      diagramUrl () {
        return this.entity.f_id ? (Vue.cfg.apiUrl + this.options.context.url + '/' + this.entity.f_id + '/diagram') : ''
      }
    },
    methods: {}
  }
</script>
