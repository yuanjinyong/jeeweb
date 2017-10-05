<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item label="编码" :prop="'model.key'">
        <el-input class="jw-field-col-1" v-model="entity.model.key"></el-input>
      </el-form-item>
      <el-form-item label="名称" :prop="'model.name'">
        <el-input class="jw-field-col-1" v-model="entity.model.name"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input class="jw-field-col-2" v-model="entity.description" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item label="版本" :prop="'model.revision'">
        <el-input-number class="jw-field-col-1" v-model="entity.model.revision" :step="1"></el-input-number>
      </el-form-item>
    </template>
  </jw-form>
</template>


<script>
  import {DetailMixin} from 'mixins'

  export default {
    name: 'processModelDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '流程模型',
            url: 'api/activiti/process/models',
            detailComponent: this
          },
          createEntity (options, cb) {
            let entity = {
              description: null,
              model: {key: 'processKey', name: '流程名称', revision: 1}
            }

            cb(entity)
          }
        },
        entity: {model: {}},
        rules: {
          'model.key': [
            {required: true, message: '请输入编码', trigger: 'blur'}
          ],
          'model.name': [
            {required: true, message: '请输入名称', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {}
  }
</script>
