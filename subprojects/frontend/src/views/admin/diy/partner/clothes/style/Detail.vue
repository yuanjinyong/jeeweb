<template>
  <jw-form ref="form" :form-options="options" :entity="entity" :rules="rules">
    <template slot="fieldset">
      <el-form-item class="jw-field jw-field-2" label="合作伙伴名称" prop="f_partner_name">
        <el-input v-model="entity.f_partner_name"></el-input>
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
      <el-form-item class="jw-field jw-field-1" label="成本价" prop="f_cost_price">
        <el-input-number v-model="entity.f_cost_price" :step="1"></el-input-number>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="重量" prop="f_weight">
        <el-input-number v-model="entity.f_weight" :step="10"></el-input-number>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="类型" prop="f_type">
        <jw-dict v-model="entity.f_crowd_type" :dict-code="'ClothesSizeType'">
        </jw-dict>
      </el-form-item>
      <el-form-item class="jw-field jw-field-1" label="状态" prop="f_is_preset">
        <el-radio-group v-model="entity.f_status">
          <el-radio :label="101">出售</el-radio>
          <el-radio :label="102">停售</el-radio>
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
    name: 'clothesStyleDetail',
    mixins: [DetailMixin],
    data () {
      return {
        options: {
          context: {
            name: '衣服款式',
            url: 'api/diy/partner/clothes/styles'
          },
          createEntity (options, cb) {
            cb({
              f_partner_id: null,
              f_partner_name: null,
              f_code: null,
              f_name: null,
              f_desc: null,
              f_cost_price: 0.00,
              f_crowd_type: 101,
              f_weight: 0,
              f_creator_id: null,
              f_created_time: null,
              f_status: 101,
              f_remark: null
            })
          }
        },
        entity: {},
        rules: {
          f_name: [
            {required: true, message: '请输入衣服款式名称', trigger: 'blur'},
            {max: 80, message: '长度在80个字符以内', trigger: 'blur'}
          ]
        }
      }
    }
  }
</script>
