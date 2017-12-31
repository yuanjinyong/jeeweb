<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-2" label="合作伙伴名称" prop="f_partner_name">
        <el-input v-model="entity.f_partner_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="类型" prop="f_type">
        <jw-dict v-model="entity.f_type" :dict-code="'ClothesSizeType'">
        </jw-dict>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="尺码" prop="f_type">
        <jw-dict v-model="entity.f_size" :dict-code="'ClothesSizeSize'">
        </jw-dict>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="编码" prop="f_code">
        <el-input v-model="entity.f_code"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="名称" prop="f_name">
        <el-input v-model="entity.f_name"></el-input>
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
    name: 'clothesSizeDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '衣服尺码',
            url: 'api/diy/partner/clothes/sizes'
          },
          createEntity (options, cb) {
            cb({
              f_partner_id: null,
              f_partner_name: null,
              f_type: 'A',
              f_size: 'L',
              f_code: 'L-A',
              f_name: '通用大码',
              f_desc: null,
              f_remark: null
            })
          }
        },
        entity: {},
        rules: {
          f_code: [
            {required: true, message: '请输入颜色编码', trigger: 'blur'},
            {max: 20, message: '长度在20个字符以内', trigger: 'blur'}
          ],
          f_name: [
            {required: true, message: '请输入颜色名称', trigger: 'blur'},
            {max: 50, message: '长度在50个字符以内', trigger: 'blur'}
          ]
        }
      }
    }
  }
</script>
