<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-1" label="名称" prop="f_name">
        <el-input v-model="entity.f_name"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="负责人姓名" prop="f_leader">
        <el-input v-model="entity.f_leader"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="描述" prop="f_desc">
        <el-input v-model="entity.f_desc" type="textarea" autosize></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="固定电话" prop="f_phone">
        <el-input v-model="entity.f_phone"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="手机号码" prop="f_mobile">
        <el-input v-model="entity.f_mobile"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-2" label="地址" prop="f_address">
        <el-input v-model="entity.f_address"></el-input>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="状态" prop="f_is_preset">
        <el-radio-group v-model="entity.f_status">
          <el-radio :label="101">正常合作</el-radio>
          <el-radio :label="102">暂停合作</el-radio>
        </el-radio-group>
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
    name: 'partnerDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '合作伙伴',
            url: 'api/diy/partner/partners'
          },
          createEntity (options, cb) {
            cb({f_status: 101})
          }
        },
        entity: {},
        rules: {
          f_name: [
            {required: true, message: '请输入合作伙伴名称', trigger: 'blur'},
            {max: 200, message: '长度在200个字符以内', trigger: 'blur'}
          ]
        }
      }
    }
  }
</script>
